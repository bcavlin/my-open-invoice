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

package com.bgh.myopeninvoice.jsfbeans;

import com.bgh.myopeninvoice.db.dao.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.*;
import com.bgh.myopeninvoice.jsfbeans.model.CompaniesEntityLazyModel;
import com.bgh.myopeninvoice.jsfbeans.model.ContractsEntityLazyModel;
import com.bgh.myopeninvoice.utils.FacesUtils;
import com.google.common.collect.Lists;
import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@ManagedBean
@ViewScoped
@Component
public class CompaniesBean implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(CompaniesBean.class);

    private InvoiceDAO invoiceDAO;

    private LazyDataModel<CompaniesEntity> companiesEntityLazyDataModel;

    private LazyDataModel<ContractsEntity> contractsEntityLazyDataModel;

    private DualListModel<ContactsEntity> contactsEntityDualListModel;

    private CompaniesEntity selectedCompaniesEntity;

    private ContractsEntity selectedContractsEntity;

    private Collection<CompanyContactEntity> companyContactEntityCollectionForSelection;

    private Collection<CurrencyEntity> currencyEntityCollectionForSelection;

    private Collection<CompaniesEntity> companiesEntityCollectionForSelection;

    private int pageSize = 20;

    @Autowired
    public CompaniesBean(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @PostConstruct
    private void init() {
        logger.info("Initializing companies entries");
        companiesEntityLazyDataModel = new CompaniesEntityLazyModel(invoiceDAO);
        currencyEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCurrencyRepository().findAll());
        companyContactEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompanyContactRepository().findAll(QCompanyContactEntity.companyContactEntity.companiesByCompanyId.ownedByMe.eq(true)));
        companiesEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompaniesRepository().findAll(QCompaniesEntity.companiesEntity.ownedByMe.eq(false)));
    }

    private void refresh() {
        logger.info("Loading companies entries");
        selectedCompaniesEntity = invoiceDAO.getCompaniesRepository().findOne(selectedCompaniesEntity.getCompanyId());
        companyContactEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompanyContactRepository().findAll(QCompanyContactEntity.companyContactEntity.companiesByCompanyId.ownedByMe.eq(true)));
        companiesEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompaniesRepository().findAll(QCompaniesEntity.companiesEntity.ownedByMe.eq(false)));
    }

    public void newEntryListenerForCompany(ActionEvent event) {
        logger.info("Creating new entity");
        selectedCompaniesEntity = new CompaniesEntity();
        selectedCompaniesEntity.setOwnedByMe(false);
        fillDualList();
    }

    public void newEntryListenerForContract(ActionEvent event) {
        logger.info("newEntryListenerForContract");
        selectedContractsEntity = new ContractsEntity();
        selectedContractsEntity.setCompaniesByContractSignedWith(selectedCompaniesEntity);
        selectedContractsEntity.setContractSignedWith(selectedCompaniesEntity.getCompanyId());
        selectedContractsEntity.setValidFrom(new Date());
        selectedContractsEntity.setValidTo(new DateTime().plusYears(2).toDate());
        if (companyContactEntityCollectionForSelection == null) {
            companyContactEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompanyContactRepository().findAll(QCompanyContactEntity.companyContactEntity.companiesByCompanyId.ownedByMe.eq(true)));
        }
    }

    public void ajaxChangeRowListenerForCompany() {
        logger.info("Filling dual list");
        contractsEntityLazyDataModel = new ContractsEntityLazyModel(invoiceDAO, selectedCompaniesEntity);
        fillDualList();
        if (companyContactEntityCollectionForSelection == null) {
            companyContactEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompanyContactRepository().findAll(QCompanyContactEntity.companyContactEntity.companiesByCompanyId.ownedByMe.eq(true)));
        }
    }

    public void ajaxChangeRowListenerForContract() {
    }

    private void fillDualList() {
        final Iterable<ContactsEntity> allContactsEntity = invoiceDAO.getContactsRepository().findAll();
        Collection<CompanyContactEntity> assignedContactsEntity = selectedCompaniesEntity.getCompanyContactsByCompanyId();

        if (assignedContactsEntity == null) {
            assignedContactsEntity = new ArrayList<>();
        }

        List<ContactsEntity> sourceList = new ArrayList<>();
        List<ContactsEntity> targetList = new ArrayList<>();

        contactsEntityDualListModel = new DualListModel<>();

        allContactsEntity.forEach(sourceList::add);
        assignedContactsEntity.forEach(r -> targetList.add(r.getContactsByContactId()));

        sourceList.removeAll(targetList);

        contactsEntityDualListModel.setSource(sourceList);
        contactsEntityDualListModel.setTarget(targetList);
    }

    public void addOrEditCompanyListener(ActionEvent event) {
        if (selectedCompaniesEntity != null) {
            RequestContext.getCurrentInstance().execute("PF('companies-form-dialog').hide()");

            logger.info("Adding/editing entity {}", selectedCompaniesEntity.toString());
            //TODO this needs to be one transaction - delete and save - or prevent this change
            if (selectedCompaniesEntity.getOwnedByMe()) { //need to do this because of edit
                logger.info("Removing contracts for {} as it is owned by me", selectedCompaniesEntity.getCompanyName());
                invoiceDAO.getContractsRepository().delete(selectedCompaniesEntity.getContractsByCompanyId());
            }
            selectedCompaniesEntity = invoiceDAO.getCompaniesRepository().save(selectedCompaniesEntity);
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
        } else {
            FacesUtils.addErrorMessage("Selected companies entity is null");
        }
    }

    public void addOrEditEntryListener2(ActionEvent event) {
        if (selectedCompaniesEntity != null && contactsEntityDualListModel != null) {
            RequestContext.getCurrentInstance().execute("PF('companies-contacts-form-dialog').hide()");

            logger.info("Adding/editing entity {}", contactsEntityDualListModel.getTarget().toString());
            invoiceDAO.saveCompanyContactEntity(selectedCompaniesEntity, contactsEntityDualListModel.getTarget());
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
        } else {
            FacesUtils.addErrorMessage("Selected users entity is null");
        }
    }

    public void addOrEditCompanyContractListener(ActionEvent event) {
        if (selectedCompaniesEntity != null && selectedContractsEntity != null) {
            logger.info("Adding/editing entity {}", selectedContractsEntity.toString());

            //TODO check active contracts vs current record

            if (selectedContractsEntity.getContractSignedWith().equals(selectedContractsEntity.getContractSignedWithSubcontract())) {
                FacesUtils.addErrorMessage("Subcontract and contract cannot be signed for same company");
            } else {
                RequestContext.getCurrentInstance().execute("PF('contracts-form-dialog').hide()");

                invoiceDAO.getContractsRepository().save(selectedContractsEntity);
                refresh();
                FacesUtils.addSuccessMessage("Entity record updated");
            }

        } else {
            FacesUtils.addErrorMessage("Selected users entity is null");
        }
    }

    public void deleteEntryListener(ActionEvent event) {
        if (selectedCompaniesEntity != null) {
            logger.info("Deleting entity {}", selectedCompaniesEntity.toString());
            invoiceDAO.getCompaniesRepository().delete(selectedCompaniesEntity.getCompanyId());
            refresh();
            FacesUtils.addSuccessMessage("Entity deleted");
            selectedCompaniesEntity = null;
        } else {
            FacesUtils.addErrorMessage("Selected companies entity is null");
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (selectedCompaniesEntity != null) {
            selectedCompaniesEntity.setContent(event.getFile().getContents());

        }
    }

    public String getImageLogo(CompaniesEntity companiesEntity) throws IOException, ImageReadException {
        if (companiesEntity != null && companiesEntity.getContent() != null) {
            ImageFormat mimeType = Sanselan.guessFormat(companiesEntity.getContent());

            return "data:image/" + mimeType.extension.toLowerCase() + ";base64," + Base64.getEncoder().encodeToString(companiesEntity.getContent()); //Base64.encodeBase64String(companiesEntity.getContent());
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

    public LazyDataModel<CompaniesEntity> getCompaniesEntityLazyDataModel() {
        return companiesEntityLazyDataModel;
    }

    public void setCompaniesEntityLazyDataModel(LazyDataModel<CompaniesEntity> companiesEntityLazyDataModel) {
        this.companiesEntityLazyDataModel = companiesEntityLazyDataModel;
    }

    public CompaniesEntity getSelectedCompaniesEntity() {
        return selectedCompaniesEntity;
    }

    public void setSelectedCompaniesEntity(CompaniesEntity selectedCompaniesEntity) {
        this.selectedCompaniesEntity = selectedCompaniesEntity;
    }

    public DualListModel<ContactsEntity> getContactsEntityDualListModel() {
        return contactsEntityDualListModel;
    }

    public void setContactsEntityDualListModel(DualListModel<ContactsEntity> contactsEntityDualListModel) {
        this.contactsEntityDualListModel = contactsEntityDualListModel;
    }

    public ContractsEntity getSelectedContractsEntity() {
        return selectedContractsEntity;
    }

    public void setSelectedContractsEntity(ContractsEntity selectedContractsEntity) {
        this.selectedContractsEntity = selectedContractsEntity;
    }

    public Collection<CompanyContactEntity> getCompanyContactEntityCollectionForSelection() {
        return companyContactEntityCollectionForSelection;
    }

    public void setCompanyContactEntityCollectionForSelection(Collection<CompanyContactEntity> companyContactEntityCollectionForSelection) {
        this.companyContactEntityCollectionForSelection = companyContactEntityCollectionForSelection;
    }

    public LazyDataModel<ContractsEntity> getContractsEntityLazyDataModel() {
        return contractsEntityLazyDataModel;
    }

    public void setContractsEntityLazyDataModel(LazyDataModel<ContractsEntity> contractsEntityLazyDataModel) {
        this.contractsEntityLazyDataModel = contractsEntityLazyDataModel;
    }

    public Collection<CurrencyEntity> getCurrencyEntityCollectionForSelection() {
        return currencyEntityCollectionForSelection;
    }

    public void setCurrencyEntityCollectionForSelection(Collection<CurrencyEntity> currencyEntityCollectionForSelection) {
        this.currencyEntityCollectionForSelection = currencyEntityCollectionForSelection;
    }

    public Collection<CompaniesEntity> getCompaniesEntityCollectionForSelection() {
        return companiesEntityCollectionForSelection;
    }

    public void setCompaniesEntityCollectionForSelection(Collection<CompaniesEntity> companiesEntityCollectionForSelection) {
        this.companiesEntityCollectionForSelection = companiesEntityCollectionForSelection;
    }
}
