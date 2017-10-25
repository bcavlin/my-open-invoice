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
import com.bgh.myopeninvoice.db.model.TaxEntity;
import com.bgh.myopeninvoice.jsfbeans.model.TaxEntityLazyModel;
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
public class TaxBean implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(TaxBean.class);

    @Autowired
    private InvoiceDAO invoiceDAO;

    private LazyDataModel<TaxEntity> taxEntityList;

    private TaxEntity selectedTaxEntity;

    private int pageSize = 20;

    @PostConstruct
    private void init() {
        logger.info("Initializing tax entries");
        taxEntityList = new TaxEntityLazyModel(invoiceDAO);
    }

    private void refresh() {
        logger.info("Loading tax entries");
        if (selectedTaxEntity != null) {
            selectedTaxEntity = invoiceDAO.getTaxRepository().findOne(selectedTaxEntity.getTaxId());
        }
    }

    public void newEntryListener(ActionEvent event) {
        logger.info("Creating new entity");
        selectedTaxEntity = new TaxEntity();
    }

    public void addOrEditEntryListener(ActionEvent event) {
        if (selectedTaxEntity != null) {
            RequestContext.getCurrentInstance().execute("PF('tax-form-dialog').hide()");

            logger.info("Adding/editing entity {}", selectedTaxEntity.toString());
            selectedTaxEntity = invoiceDAO.getTaxRepository().save(selectedTaxEntity);
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
        } else {
            FacesUtils.addErrorMessage("Selected tax entity is null");
        }
    }

    public void deleteEntryListener(ActionEvent event) {
        if (selectedTaxEntity != null) {
            logger.info("Deleting entity {}", selectedTaxEntity.toString());
            invoiceDAO.getTaxRepository().delete(selectedTaxEntity.getTaxId());
            refresh();
            FacesUtils.addSuccessMessage("Entity deleted");
            selectedTaxEntity = null;
        } else {
            FacesUtils.addErrorMessage("Selected tax entity is null");
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public LazyDataModel<TaxEntity> getTaxEntityList() {
        return taxEntityList;
    }

    public void setTaxEntityList(LazyDataModel<TaxEntity> taxEntityList) {
        this.taxEntityList = taxEntityList;
    }

    public TaxEntity getSelectedTaxEntity() {
        return selectedTaxEntity;
    }

    public void setSelectedTaxEntity(TaxEntity selectedTaxEntity) {
        this.selectedTaxEntity = selectedTaxEntity;
    }

}
