package com.bgh.myopeninvoice.jsfbeans;

import com.bgh.myopeninvoice.db.dao.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.ContactsEntity;
import com.bgh.myopeninvoice.jsfbeans.model.ContactsEntityLazyModel;
import com.bgh.myopeninvoice.utils.FacesUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.io.Serializable;

/**
 * Created by bcavlin on 17/03/17.
 */
@ManagedBean
@ViewScoped
@Component
public class ContactsBean implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(ContactsBean.class);

    @Autowired
    private InvoiceDAO invoiceDAO;

    private LazyDataModel<ContactsEntity> contactsEntityList;

    private ContactsEntity selectedContactsEntity;

    private int pageSize = 20;

    @PostConstruct
    private void init() {
        logger.info("Initializing contacts entries");
        contactsEntityList = new ContactsEntityLazyModel(invoiceDAO);
    }

    private void refresh() {
        logger.info("Loading contacts entries");
        if (selectedContactsEntity != null) {
            selectedContactsEntity = invoiceDAO.getContactsRepository().findOne(selectedContactsEntity.getContactId());
        }
    }

    public void newEntryListener(ActionEvent event) {
        logger.info("Creating new entity");
        selectedContactsEntity = new ContactsEntity();
    }

    public void addOrEditEntryListener(ActionEvent event) {
        if (selectedContactsEntity != null) {
            RequestContext.getCurrentInstance().execute("PF('contacts-form-dialog').hide()");

            logger.info("Adding/editing entity {}", selectedContactsEntity.toString());
            selectedContactsEntity = invoiceDAO.getContactsRepository().save(selectedContactsEntity);
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
        } else {
            FacesUtils.addErrorMessage("Selected contacts entity is null");
        }
    }

    public void deleteEntryListener(ActionEvent event) {
        if (selectedContactsEntity != null) {
            logger.info("Deleting entity {}", selectedContactsEntity.toString());
            invoiceDAO.getContactsRepository().delete(selectedContactsEntity.getContactId());
            refresh();
            FacesUtils.addSuccessMessage("Entity deleted");
            selectedContactsEntity = null;
        } else {
            FacesUtils.addErrorMessage("Selected contacts entity is null");
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public LazyDataModel<ContactsEntity> getContactsEntityList() {
        return contactsEntityList;
    }

    public void setContactsEntityList(LazyDataModel<ContactsEntity> contactsEntityList) {
        this.contactsEntityList = contactsEntityList;
    }

    public ContactsEntity getSelectedContactsEntity() {
        return selectedContactsEntity;
    }

    public void setSelectedContactsEntity(ContactsEntity selectedContactsEntity) {
        this.selectedContactsEntity = selectedContactsEntity;
    }

}
