package com.bgh.myopeninvoice.jsfbeans;

import com.bgh.myopeninvoice.db.dao.TaxRepository;
import com.bgh.myopeninvoice.db.model.TaxEntity;
import com.bgh.myopeninvoice.utils.FacesUtils;
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
    private TaxRepository taxRepository;

    private LazyDataModel<TaxEntity> taxEntityList;

    private TaxEntity selectedTaxEntity;

    private int pageSize = 20;

    @PostConstruct
    public void refresh() {
        if (taxEntityList == null) {
            logger.info("Loading tax entries");
            taxEntityList = new TaxExtityLazyModel(taxRepository);
        }
    }

    public void newEntryListener(ActionEvent event) {
        logger.info("Creating new entity");
        selectedTaxEntity = new TaxEntity();
    }

    public void addEditTax(ActionEvent event) {
        if (selectedTaxEntity != null) {
            logger.info("Adding/editing entity {}", selectedTaxEntity.toString());
            selectedTaxEntity = taxRepository.save(selectedTaxEntity);
            refresh();
            FacesUtils.addSuccessMessage("New entity created");
        } else {
            FacesUtils.addErrorMessage("Selected tax entity is null");
        }
    }

    public void deleteEntryListener(ActionEvent event) {
        if (selectedTaxEntity != null) {
            logger.info("Deleting entity {}", selectedTaxEntity.toString());
            taxRepository.delete(selectedTaxEntity.getTaxId());
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
