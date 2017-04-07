package com.bgh.myopeninvoice.jsfbeans;

import com.bgh.myopeninvoice.db.dao.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.*;
import com.bgh.myopeninvoice.jsfbeans.model.CompaniesEntityLazyModel;
import com.bgh.myopeninvoice.jsfbeans.model.ContractsEntityLazyModel;
import com.bgh.myopeninvoice.utils.FacesUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bcavlin on 17/03/17.
 */
@ManagedBean
@ViewScoped
@Component
public class CompaniesBean implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(CompaniesBean.class);

    @Autowired
    private InvoiceDAO invoiceDAO;

    private LazyDataModel<CompaniesEntity> companiesEntityLazyDataModel;

    private LazyDataModel<ContractsEntity> contractsEntityLazyDataModel;

    private DualListModel<ContactsEntity> contactsEntityDualListModel;

    private CompaniesEntity selectedCompaniesEntity;

    private ContractsEntity selectedContractsEntity;

    private Collection<CompanyContactEntity> companyContactEntityCollectionForSelection;

    private int pageSize = 20;

    @PostConstruct
    private void init() {
        logger.info("Initializing companies entries");
        companiesEntityLazyDataModel = new CompaniesEntityLazyModel(invoiceDAO);
    }

    private void refresh() {
        logger.info("Loading companies entries");
        if (selectedCompaniesEntity != null) {
            selectedCompaniesEntity = invoiceDAO.getCompaniesRepository().findOne(selectedCompaniesEntity.getCompanyId());
        }
    }

    public void newEntryListenerForCompany(ActionEvent event) {
        logger.info("Creating new entity");
        selectedCompaniesEntity = new CompaniesEntity();
        selectedCompaniesEntity.setOwnedByMe(true);
        fillDualList();
    }

    public void newEntryListenerForContract(ActionEvent event) {
        logger.info("newEntryListenerForContract");
        selectedContractsEntity = new ContractsEntity();
        selectedContractsEntity.setCompaniesByContractSignedWith(selectedCompaniesEntity);
        selectedContractsEntity.setContractSignedWith(selectedCompaniesEntity.getCompanyId());
        if(companyContactEntityCollectionForSelection==null){
            companyContactEntityCollectionForSelection = new ArrayList<>();
            final Iterable<CompanyContactEntity> all = invoiceDAO.getCompanyContactRepository().findAll(QCompanyContactEntity.companyContactEntity.companiesByCompanyId.ownedByMe.eq(true));
            if(all!=null){
                all.forEach(companyContactEntityCollectionForSelection::add);
            }
        }
    }

    public void ajaxChangeRowListenerForCompany() {
        logger.info("Filling dual list");
        contractsEntityLazyDataModel = new ContractsEntityLazyModel(invoiceDAO, selectedCompaniesEntity);
        fillDualList();
        if(companyContactEntityCollectionForSelection==null){
            companyContactEntityCollectionForSelection = new ArrayList<>();
            final Iterable<CompanyContactEntity> all = invoiceDAO.getCompanyContactRepository().findAll(QCompanyContactEntity.companyContactEntity.companiesByCompanyId.ownedByMe.eq(true));
            if(all!=null){
                all.forEach(companyContactEntityCollectionForSelection::add);
            }
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

    public void addOrEditEntryListener(ActionEvent event) {
        if (selectedCompaniesEntity != null) {
            RequestContext.getCurrentInstance().execute("PF('companies-form-dialog').hide()");

            logger.info("Adding/editing entity {}", selectedCompaniesEntity.toString());
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

    public void addOrEditEntryListener3(ActionEvent event) {
        if (selectedCompaniesEntity != null && selectedContractsEntity != null) {
            RequestContext.getCurrentInstance().execute("PF('contracts-form-dialog').hide()");

            logger.info("Adding/editing entity {}", selectedContractsEntity.toString());
            invoiceDAO.getContractsRepository().save(selectedContractsEntity);
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
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
            selectedCompaniesEntity.setCompanyLogo(event.getFile().getContents());
        }
    }

    public String getImageLogo(CompaniesEntity companiesEntity) throws IOException, ImageReadException {
        if (companiesEntity != null && companiesEntity.getCompanyLogo() != null) {
            ImageFormat mimeType = Sanselan.guessFormat(companiesEntity.getCompanyLogo());

            return "data:image/" + mimeType.extension.toLowerCase() + ";base64," +
                    Base64.encodeBase64String(companiesEntity.getCompanyLogo());
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
}
