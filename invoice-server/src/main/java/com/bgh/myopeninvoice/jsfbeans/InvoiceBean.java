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
import com.bgh.myopeninvoice.jsfbeans.model.InvoiceEntityLazyModel;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
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
import java.util.Collection;
import java.util.Date;

/**
 * Created by bcavlin on 17/03/17.
 */
@ManagedBean
@ViewScoped
@Component
public class InvoiceBean implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(InvoiceBean.class);

    private InvoiceDAO invoiceDAO;

    private LazyDataModel<InvoiceEntity> invoiceEntityLazyDataModel;

    private Collection<CompanyContactEntity> companyContactEntityCollectionForSelection;

    private Collection<CompaniesEntity> companiesEntityCollectionForSelection;

    private Collection<TaxEntity> taxEntityCollectionForSelection;

    private InvoiceEntity selectedInvoiceEntity;

    private int pageSize = 20;

    @Autowired
    public InvoiceBean(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @PostConstruct
    private void init() {
        logger.info("Initializing entries");
        invoiceEntityLazyDataModel = new InvoiceEntityLazyModel(invoiceDAO);
        Predicate p = QCompanyContactEntity.companyContactEntity.companiesByCompanyId.ownedByMe.isTrue();
        companyContactEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompanyContactRepository().findAll(p));
        Predicate p1 = QCompaniesEntity.companiesEntity.ownedByMe.isFalse();
        companiesEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getCompaniesRepository().findAll(p1));

        taxEntityCollectionForSelection = Lists.newArrayList(invoiceDAO.getTaxRepository().findAll());
    }

    private void refresh() {
        logger.info("Loading entries");
    }

    public void newEntryListener(ActionEvent event) {
        logger.info("Creating new entity");
        selectedInvoiceEntity = new InvoiceEntity();
        selectedInvoiceEntity.setCreatedDate(new Date());
    }

    public void ajaxChangeRowListener() {

    }

    public void addOrEditEntryListener(ActionEvent event) {

    }

    public void deleteEntryListener(ActionEvent event) {

    }

    public void updateSelectionFromTo() {
        if (selectedInvoiceEntity != null
                && selectedInvoiceEntity.getCompanyContactFrom() != null
                && selectedInvoiceEntity.getCompanyTo() != null
                ) {
            if(selectedInvoiceEntity.getCompanyContactByCompanyContactFrom()==null || selectedInvoiceEntity.getCompanyContactByCompanyContactFrom().getCompanyContactId()!=selectedInvoiceEntity.getCompanyContactFrom()) {
                selectedInvoiceEntity.setCompanyContactByCompanyContactFrom(invoiceDAO.getCompanyContactRepository().findOne(selectedInvoiceEntity.getCompanyContactFrom()));
                selectedInvoiceEntity.setCompaniesByCompanyTo(invoiceDAO.getCompaniesRepository().findOne(selectedInvoiceEntity.getCompanyTo()));
                final ContractsEntity validContract = selectedInvoiceEntity.getCompanyContactByCompanyContactFrom().findValidContract(selectedInvoiceEntity.getCompaniesByCompanyTo());
                if (validContract != null) {
                    selectedInvoiceEntity.setRate(validContract.getRate());
                    selectedInvoiceEntity.setRateUnit(validContract.getRateUnit());
                    selectedInvoiceEntity.setCcy(validContract.getCcy());
                    selectedInvoiceEntity.setCurrencyByCcy(validContract.getCurrencyByCcy());
                }
            }
        }
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

    public Collection<CompanyContactEntity> getCompanyContactEntityCollectionForSelection() {
        return companyContactEntityCollectionForSelection;
    }

    public void setCompanyContactEntityCollectionForSelection(Collection<CompanyContactEntity> companyContactEntityCollectionForSelection) {
        this.companyContactEntityCollectionForSelection = companyContactEntityCollectionForSelection;
    }

    public Collection<CompaniesEntity> getCompaniesEntityCollectionForSelection() {
        return companiesEntityCollectionForSelection;
    }

    public void setCompaniesEntityCollectionForSelection(Collection<CompaniesEntity> companiesEntityCollectionForSelection) {
        this.companiesEntityCollectionForSelection = companiesEntityCollectionForSelection;
    }

    public Collection<TaxEntity> getTaxEntityCollectionForSelection() {
        return taxEntityCollectionForSelection;
    }

    public void setTaxEntityCollectionForSelection(Collection<TaxEntity> taxEntityCollectionForSelection) {
        this.taxEntityCollectionForSelection = taxEntityCollectionForSelection;
    }
}
