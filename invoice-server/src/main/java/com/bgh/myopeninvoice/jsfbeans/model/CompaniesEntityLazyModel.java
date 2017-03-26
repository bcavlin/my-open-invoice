package com.bgh.myopeninvoice.jsfbeans.model;

import com.bgh.myopeninvoice.db.dao.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.CompaniesEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bcavlin on 17/03/17.
 */
public class CompaniesEntityLazyModel extends LazyDataModel<CompaniesEntity> {

    private static Logger logger = LoggerFactory.getLogger(CompaniesEntityLazyModel.class);

    private List<CompaniesEntity> companiesEntityList;

    private InvoiceDAO invoiceDAO;

    public CompaniesEntityLazyModel(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @Override
    public List<CompaniesEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        companiesEntityList = new ArrayList<>();

        PageRequest pageRequest = new PageRequest(first * pageSize, pageSize);

        Sort.Direction direction = null;
        if (sortOrder == SortOrder.ASCENDING) {
            direction = Sort.Direction.ASC;
        } else if (sortOrder == SortOrder.DESCENDING) {
            direction = Sort.Direction.DESC;
        }
        try {
            if (direction != null && sortField != null) {
                pageRequest = new PageRequest(
                        first * pageSize,
                        pageSize,
                        new Sort(new Sort.Order(direction, sortField)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        BooleanExpression predicate = null;

        Page<CompaniesEntity> companiesEntityPage = null;

        if (predicate == null) {
            companiesEntityPage = invoiceDAO.getCompaniesRepository().findAll(pageRequest);
        } else {
            companiesEntityPage = invoiceDAO.getCompaniesRepository().findAll(predicate, pageRequest);
        }

        setRowCount((int) companiesEntityPage.getTotalElements());
        setPageSize(getPageSize());

        for (CompaniesEntity companiesEntity : companiesEntityPage) {
            companiesEntityList.add(companiesEntity);
        }
        return companiesEntityList;
    }

    @Override
    public Object getRowKey(CompaniesEntity object) {
        return object.getCompanyId();
    }

    @Override
    public CompaniesEntity getRowData(String rowKey) {
        Integer id = Integer.valueOf(rowKey);
        if (companiesEntityList != null) {
            for (CompaniesEntity companiesEntity : companiesEntityList) {
                if (id.equals(companiesEntity.getCompanyId())) {
                    return companiesEntity;
                }
            }
        }
        return null;
    }
}
