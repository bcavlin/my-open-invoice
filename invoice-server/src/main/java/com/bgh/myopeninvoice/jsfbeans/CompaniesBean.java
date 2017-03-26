package com.bgh.myopeninvoice.jsfbeans;

import com.bgh.myopeninvoice.db.dao.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.CompaniesEntity;
import com.bgh.myopeninvoice.jsfbeans.model.CompaniesEntityLazyModel;
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
public class CompaniesBean implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(CompaniesBean.class);

    @Autowired
    private InvoiceDAO invoiceDAO;

    private LazyDataModel<CompaniesEntity> companiesEntityList;

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

}
