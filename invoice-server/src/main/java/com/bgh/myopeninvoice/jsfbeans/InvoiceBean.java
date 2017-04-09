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
import com.bgh.myopeninvoice.db.model.InvoiceEntity;
import com.bgh.myopeninvoice.jsfbeans.model.InvoiceEntityLazyModel;
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
public class InvoiceBean implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(InvoiceBean.class);

    @Autowired
    private InvoiceDAO invoiceDAO;

    private LazyDataModel<InvoiceEntity> invoiceEntityLazyDataModel;

    private InvoiceEntity selectedInvoiceEntity;

    private int pageSize = 20;

    @PostConstruct
    private void init() {
        logger.info("Initializing entries");
        invoiceEntityLazyDataModel = new InvoiceEntityLazyModel(invoiceDAO);
    }

    private void refresh() {
        logger.info("Loading entries");
        if (selectedInvoiceEntity != null) {
            selectedInvoiceEntity = invoiceDAO.getInvoiceRepository().findOne(selectedInvoiceEntity.getInvoiceId());
        }
    }

    public void newEntryListener(ActionEvent event) {
        logger.info("Creating new entity");
        selectedInvoiceEntity = new InvoiceEntity();
    }

    public void ajaxChangeRowListener() {

    }

    public void addOrEditEntryListener(ActionEvent event) {

    }

    public void deleteEntryListener(ActionEvent event) {

    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public LazyDataModel<InvoiceEntity> getInvoiceEntityLazyDataModel() {
        return invoiceEntityLazyDataModel;
    }

    public void setInvoiceEntityLazyDataModel(LazyDataModel<InvoiceEntity> invoiceEntityLazyDataModel) {
        this.invoiceEntityLazyDataModel = invoiceEntityLazyDataModel;
    }

    public InvoiceEntity getSelectedInvoiceEntity() {
        return selectedInvoiceEntity;
    }

    public void setSelectedInvoiceEntity(InvoiceEntity selectedInvoiceEntity) {
        this.selectedInvoiceEntity = selectedInvoiceEntity;
    }
}
