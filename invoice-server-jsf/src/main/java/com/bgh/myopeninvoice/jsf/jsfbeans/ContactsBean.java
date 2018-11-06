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

import com.bgh.myopeninvoice.db.repository.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.ContactEntity;
import com.bgh.myopeninvoice.jsf.jsfbeans.model.ContactsEntityLazyModel;
import com.bgh.myopeninvoice.jsf.utils.FacesUtils;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
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
@Slf4j
@ManagedBean
@ViewScoped
@Component
public class ContactsBean implements Serializable {

    @Autowired
    private InvoiceDAO invoiceDAO;

    private LazyDataModel<ContactEntity> contactsEntityList;

    private ContactEntity selectedContactEntity;

    private int pageSize = 20;

    @PostConstruct
    public void init() {
        if (contactsEntityList == null) {
            final String parameter1 = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("parameter1");
            log.info("Initializing contacts entries");
            contactsEntityList = new ContactsEntityLazyModel(invoiceDAO, StringUtils.commaDelimitedListToSet(parameter1));
        }
    }

    private void refresh() {
        log.info("Loading contacts entries");
        if (selectedContactEntity != null) {
            selectedContactEntity = invoiceDAO.getContactsRepository().findOne(selectedContactEntity.getContactId());
        }
    }

    public void newEntryListener(ActionEvent event) {
        log.info("Creating new entity");
        selectedContactEntity = new ContactEntity();
    }

    public void addOrEditEntryListener(ActionEvent event) {
        if (selectedContactEntity != null) {
            RequestContext.getCurrentInstance().execute("PF('contacts-form-dialog').hide()");

            log.info("Adding/editing entity {}", selectedContactEntity.toString());
            selectedContactEntity = invoiceDAO.getContactsRepository().save(selectedContactEntity);
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
        } else {
            FacesUtils.addErrorMessage("Selected contacts entity is null");
        }
    }

    public void deleteEntryListener(ActionEvent event) {
        if (selectedContactEntity != null) {
            log.info("Deleting entity {}", selectedContactEntity.toString());
            invoiceDAO.getContactsRepository().delete(selectedContactEntity.getContactId());
            refresh();
            FacesUtils.addSuccessMessage("Entity deleted");
            selectedContactEntity = null;
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

    public LazyDataModel<ContactEntity> getContactsEntityList() {
        return contactsEntityList;
    }

    public void setContactsEntityList(LazyDataModel<ContactEntity> contactsEntityList) {
        this.contactsEntityList = contactsEntityList;
    }

    public ContactEntity getSelectedContactEntity() {
        return selectedContactEntity;
    }

    public void setSelectedContactEntity(ContactEntity selectedContactEntity) {
        this.selectedContactEntity = selectedContactEntity;
    }


}
