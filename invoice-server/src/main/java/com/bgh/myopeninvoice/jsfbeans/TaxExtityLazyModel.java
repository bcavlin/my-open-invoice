package com.bgh.myopeninvoice.jsfbeans;

import com.bgh.myopeninvoice.db.dao.TaxRepository;
import com.bgh.myopeninvoice.db.model.TaxEntity;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bcavlin on 17/03/17.
 */
public class TaxExtityLazyModel extends LazyDataModel<TaxEntity> {

    private List<TaxEntity> taxEntityList;

    private TaxRepository taxRepository;

    public TaxExtityLazyModel(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    @Override
    public List<TaxEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        taxEntityList = new ArrayList<>();

        final Page<TaxEntity> taxEntityPage = taxRepository.findAll(new PageRequest(first * pageSize, pageSize));

        setRowCount((int) taxEntityPage.getTotalElements());
        setPageSize(getPageSize());

        for (TaxEntity taxEntity : taxEntityPage) {
            taxEntityList.add(taxEntity);
        }
        return taxEntityList;
    }

    @Override
    public Object getRowKey(TaxEntity object) {
        return object.getTaxId();
    }

    @Override
    public TaxEntity getRowData(String rowKey) {
        Integer id = Integer.valueOf(rowKey);
        if (taxEntityList != null) {
            for (TaxEntity taxEntity : taxEntityList) {
                if (id.equals(taxEntity.getTaxId())) {
                    return taxEntity;
                }
            }
        }
        return null;
    }
}
