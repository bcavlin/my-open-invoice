/*
 * Copyright 2017 Branislav Cavlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bgh.myopeninvoice.jsf.jsfbeans;

import com.bgh.myopeninvoice.db.model.*;
import com.bgh.myopeninvoice.db.repository.InvoiceDAO;
import com.bgh.myopeninvoice.jsf.jsfbeans.model.CompaniesEntityLazyModel;
import com.bgh.myopeninvoice.jsf.jsfbeans.model.ContractsEntityLazyModel;
import com.bgh.myopeninvoice.jsf.utils.FacesUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by bcavlin on 17/03/17.
 */
@Slf4j
@ManagedBean
@ViewScoped
@Component
public class CompaniesBean implements Serializable {

    //private static Logger logger = LoggerFactory.getLogger(CompaniesBean.class);

    private InvoiceDAO invoiceDAO;

    private LazyDataModel<CompanyEntity> companiesEntityLazyDataModel;

    private LazyDataModel<ContractEntity> contractsEntityLazyDataModel;

    private DualListModel<ContactEntity> contactsEntityDualListModel;

    private CompanyEntity selectedCompanyEntity;

    private ContractEntity selectedContractEntity;

    private Collection<CompanyContactEntity> companyContactEntityCollectionForSelection;

    private Collection<CurrencyEntity> currencyEntityCollectionForSelection;

    private Collection<CompanyEntity> companyEntityCollectionForSelection;

    private int pageSize = 20;

    @Autowired
    public CompaniesBean(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @PostConstruct
    private void init() {
        log.info("Initializing companies entries");
        companiesEntityLazyDataModel = new CompaniesEntityLazyModel(invoiceDAO);
        currencyEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCurrencyRepository().findAll());
        companyContactEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompanyContactRepository().findAll(QCompanyContactEntity.companyContactEntity.companiesByCompanyId.ownedByMe.eq(true)));
        companyEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompaniesRepository().findAll(QCompaniesEntity.companiesEntity.ownedByMe.eq(false)));
    }

    private void refresh() {
        log.info("Loading companies entries");
        selectedCompanyEntity = invoiceDAO.getCompaniesRepository().findOne(selectedCompanyEntity.getCompanyId());
        companyContactEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompanyContactRepository().findAll(QCompanyContactEntity.companyContactEntity.companiesByCompanyId.ownedByMe.eq(true)));
        companyEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompaniesRepository().findAll(QCompaniesEntity.companiesEntity.ownedByMe.eq(false)));
    }

    public void newEntryListenerForCompany(ActionEvent event) {
        log.info("Creating new entity");
        selectedCompanyEntity = new CompanyEntity();
        selectedCompanyEntity.setOwnedByMe(false);
        fillDualList();
    }

    public void newEntryListenerForContract(ActionEvent event) {
        log.info("newEntryListenerForContract");
        selectedContractEntity = new ContractEntity();
        selectedContractEntity.setCompaniesByContractSignedWith(selectedCompanyEntity);
        selectedContractEntity.setContractSignedWith(selectedCompanyEntity.getCompanyId());
        selectedContractEntity.setValidFrom(new Date());
        selectedContractEntity.setValidTo(new DateTime().plusYears(2).toDate());
        if (companyContactEntityCollectionForSelection == null) {
            companyContactEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompanyContactRepository().findAll(QCompanyContactEntity.companyContactEntity.companiesByCompanyId.ownedByMe.eq(true)));
        }

        selectedContractEntity.setContractNumber(selectedCompanyEntity.getShortName()
                + "-"
                + new DateTime(selectedContractEntity.getValidFrom().getTime()).getYear()
                + "-"
                + invoiceDAO.getContractsRepository().getNewContractSequenceNumber());
    }

    public void ajaxChangeRowListenerForCompany() {
        log.info("Filling dual list");
        contractsEntityLazyDataModel = new ContractsEntityLazyModel(invoiceDAO, selectedCompanyEntity);
        fillDualList();
        if (companyContactEntityCollectionForSelection == null) {
            companyContactEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompanyContactRepository().findAll(QCompanyContactEntity.companyContactEntity.companiesByCompanyId.ownedByMe.eq(true)));
        }
    }

    public void ajaxChangeRowListenerForContract() {
    }

    private void fillDualList() {
        final Iterable<ContactEntity> allContactsEntity = invoiceDAO.getContactsRepository().findAll();
        Collection<CompanyContactEntity> assignedContactsEntity = selectedCompanyEntity.getCompanyContactsByCompanyId();

        if (assignedContactsEntity == null) {
            assignedContactsEntity = new ArrayList<>();
        }

        List<ContactEntity> sourceList = new ArrayList<>();
        List<ContactEntity> targetList = new ArrayList<>();

        contactsEntityDualListModel = new DualListModel<>();

        allContactsEntity.forEach(sourceList::add);
        assignedContactsEntity.forEach(r -> targetList.add(r.getContactsByContactId()));

        sourceList.removeAll(targetList);

        contactsEntityDualListModel.setSource(sourceList);
        contactsEntityDualListModel.setTarget(targetList);
    }

    public void addOrEditCompanyListener(ActionEvent event) {
        if (selectedCompanyEntity != null) {
            RequestContext.getCurrentInstance().execute("PF('companies-form-dialog').hide()");

            log.info("Adding/editing entity {}", selectedCompanyEntity.toString());
            //TODO this needs to be one transaction - delete and save - or prevent this change
            if (selectedCompanyEntity.getOwnedByMe()) { //need to do this because of edit
                log.info("Removing contracts for {} as it is owned by me", selectedCompanyEntity.getCompanyName());
                invoiceDAO.getContractsRepository().delete(selectedCompanyEntity.getContractsByCompanyId());
            }
            selectedCompanyEntity = invoiceDAO.getCompaniesRepository().save(selectedCompanyEntity);
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
        } else {
            FacesUtils.addErrorMessage("Selected companies entity is null");
        }
    }

    public void addOrEditEntryListener2(ActionEvent event) {
        if (selectedCompanyEntity != null && contactsEntityDualListModel != null) {
            RequestContext.getCurrentInstance().execute("PF('companies-contacts-form-dialog').hide()");

            log.info("Adding/editing entity {}", contactsEntityDualListModel.getTarget().toString());
            invoiceDAO.saveCompanyContactEntity(selectedCompanyEntity, contactsEntityDualListModel.getTarget());
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
        } else {
            FacesUtils.addErrorMessage("Selected users entity is null");
        }
    }

    public void addOrEditCompanyContractListener(ActionEvent event) {
        if (selectedCompanyEntity != null && selectedContractEntity != null) {
            log.info("Adding/editing entity {}", selectedContractEntity.toString());

            //TODO check active contracts vs current record

            if (selectedContractEntity.getContractSignedWith().equals(selectedContractEntity.getContractSignedWithSubcontract())) {
                FacesUtils.addErrorMessage("Subcontract and contract cannot be signed for same company");
            } else {
                RequestContext.getCurrentInstance().execute("PF('contracts-form-dialog').hide()");

                invoiceDAO.getContractsRepository().save(selectedContractEntity);
                refresh();
                FacesUtils.addSuccessMessage("Entity record updated");
            }

        } else {
            FacesUtils.addErrorMessage("Selected users entity is null");
        }
    }

    public void deleteEntryListener(ActionEvent event) {
        if (selectedCompanyEntity != null) {
            log.info("Deleting entity {}", selectedCompanyEntity.toString());
            invoiceDAO.getCompaniesRepository().delete(selectedCompanyEntity.getCompanyId());
            refresh();
            FacesUtils.addSuccessMessage("Entity deleted");
            selectedCompanyEntity = null;
        } else {
            FacesUtils.addErrorMessage("Selected companies entity is null");
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (selectedCompanyEntity != null) {
            selectedCompanyEntity.setContent(event.getFile().getContents());

        }
    }

    public String getImageLogo(CompanyEntity companyEntity) throws IOException, ImageReadException {
        if (companyEntity != null && companyEntity.getContent() != null) {
            ImageFormat mimeType = Sanselan.guessFormat(companyEntity.getContent());

            return "data:image/" + mimeType.extension.toLowerCase() + ";base64," + Base64.getEncoder().encodeToString(companyEntity.getContent()); //Base64.encodeBase64String(companyEntity.getContent());
        } else {
            return null;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public LazyDataModel<CompanyEntity> getCompaniesEntityLazyDataModel() {
        return companiesEntityLazyDataModel;
    }

    public void setCompaniesEntityLazyDataModel(LazyDataModel<CompanyEntity> companiesEntityLazyDataModel) {
        this.companiesEntityLazyDataModel = companiesEntityLazyDataModel;
    }

    public CompanyEntity getSelectedCompanyEntity() {
        return selectedCompanyEntity;
    }

    public void setSelectedCompanyEntity(CompanyEntity selectedCompanyEntity) {
        this.selectedCompanyEntity = selectedCompanyEntity;
    }

    public DualListModel<ContactEntity> getContactsEntityDualListModel() {
        return contactsEntityDualListModel;
    }

    public void setContactsEntityDualListModel(DualListModel<ContactEntity> contactsEntityDualListModel) {
        this.contactsEntityDualListModel = contactsEntityDualListModel;
    }

    public ContractEntity getSelectedContractEntity() {
        return selectedContractEntity;
    }

    public void setSelectedContractEntity(ContractEntity selectedContractEntity) {
        this.selectedContractEntity = selectedContractEntity;
    }

    public Collection<CompanyContactEntity> getCompanyContactEntityCollectionForSelection() {
        return companyContactEntityCollectionForSelection;
    }

    public void setCompanyContactEntityCollectionForSelection(Collection<CompanyContactEntity> companyContactEntityCollectionForSelection) {
        this.companyContactEntityCollectionForSelection = companyContactEntityCollectionForSelection;
    }

    public LazyDataModel<ContractEntity> getContractsEntityLazyDataModel() {
        return contractsEntityLazyDataModel;
    }

    public void setContractsEntityLazyDataModel(LazyDataModel<ContractEntity> contractsEntityLazyDataModel) {
        this.contractsEntityLazyDataModel = contractsEntityLazyDataModel;
    }

    public Collection<CurrencyEntity> getCurrencyEntityCollectionForSelection() {
        return currencyEntityCollectionForSelection;
    }

    public void setCurrencyEntityCollectionForSelection(Collection<CurrencyEntity> currencyEntityCollectionForSelection) {
        this.currencyEntityCollectionForSelection = currencyEntityCollectionForSelection;
    }

    public Collection<CompanyEntity> getCompanyEntityCollectionForSelection() {
        return companyEntityCollectionForSelection;
    }

    public void setCompanyEntityCollectionForSelection(Collection<CompanyEntity> companyEntityCollectionForSelection) {
        this.companyEntityCollectionForSelection = companyEntityCollectionForSelection;
    }
}
