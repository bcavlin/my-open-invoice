package com.bgh.myopeninvoice.jsfbeans;

import com.bgh.myopeninvoice.db.dao.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.CompaniesEntity;
import com.bgh.myopeninvoice.db.model.CompanyContactEntity;
import com.bgh.myopeninvoice.db.model.ContactsEntity;
import com.bgh.myopeninvoice.jsfbeans.model.CompaniesEntityLazyModel;
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

    private LazyDataModel<CompaniesEntity> companiesEntityList;

    private DualListModel<ContactsEntity> contactsDualListModel;

    private CompaniesEntity selectedCompaniesEntity;

    private int pageSize = 20;

    @PostConstruct
    private void init() {
        logger.info("Initializing companies entries");
        companiesEntityList = new CompaniesEntityLazyModel(invoiceDAO);
    }

    private void refresh() {
        logger.info("Loading companies entries");
        if (selectedCompaniesEntity != null) {
            selectedCompaniesEntity = invoiceDAO.getCompaniesRepository().findOne(selectedCompaniesEntity.getCompanyId());
        }
    }

    public void newEntryListener(ActionEvent event) {
        logger.info("Creating new entity");
        selectedCompaniesEntity = new CompaniesEntity();
        selectedCompaniesEntity.setOwnedByMe(true);
        fillDualList();
    }

    public void ajaxChangeRowListener() {
        logger.info("Filling dual list");
        fillDualList();
    }

    private void fillDualList() {
        final Iterable<ContactsEntity> allContactsEntity = invoiceDAO.getContactsRepository().findAll();
        final Collection<CompanyContactEntity> assignedContactsEntity = selectedCompaniesEntity.getCompanyContactsByCompanyId();

        List<ContactsEntity> sourceList = new ArrayList<>();
        List<ContactsEntity> targetList = new ArrayList<>();

        contactsDualListModel = new DualListModel<>();

        allContactsEntity.forEach(sourceList::add);
        assignedContactsEntity.forEach(r -> targetList.add(r.getContactsByContactId()));

        sourceList.removeAll(targetList);

        contactsDualListModel.setSource(sourceList);
        contactsDualListModel.setTarget(targetList);
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
        if (selectedCompaniesEntity != null && contactsDualListModel != null) {
            RequestContext.getCurrentInstance().execute("PF('companies-contacts-form-dialog').hide()");

            logger.info("Adding/editing entity {}", contactsDualListModel.getTarget().toString());
            invoiceDAO.saveCompanyContactEntity(selectedCompaniesEntity, contactsDualListModel.getTarget());
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

    public LazyDataModel<CompaniesEntity> getCompaniesEntityList() {
        return companiesEntityList;
    }

    public void setCompaniesEntityList(LazyDataModel<CompaniesEntity> companiesEntityList) {
        this.companiesEntityList = companiesEntityList;
    }

    public CompaniesEntity getSelectedCompaniesEntity() {
        return selectedCompaniesEntity;
    }

    public void setSelectedCompaniesEntity(CompaniesEntity selectedCompaniesEntity) {
        this.selectedCompaniesEntity = selectedCompaniesEntity;
    }

    public DualListModel<ContactsEntity> getContactsDualListModel() {
        return contactsDualListModel;
    }

    public void setContactsDualListModel(DualListModel<ContactsEntity> contactsDualListModel) {
        this.contactsDualListModel = contactsDualListModel;
    }
}
