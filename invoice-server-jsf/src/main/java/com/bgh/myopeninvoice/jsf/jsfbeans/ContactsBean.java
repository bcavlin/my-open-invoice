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

import com.bgh.myopeninvoice.db.dao.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.ContactsEntity;
import com.bgh.myopeninvoice.jsf.jsfbeans.model.ContactsEntityLazyModel;
import com.bgh.myopeninvoice.jsf.utils.FacesUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
    public void init() {
        if (contactsEntityList == null) {
            final String parameter1 = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("parameter1");
            logger.info("Initializing contacts entries");
            contactsEntityList = new ContactsEntityLazyModel(invoiceDAO, StringUtils.commaDelimitedListToSet(parameter1));
        }
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
