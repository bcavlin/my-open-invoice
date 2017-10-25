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

package com.bgh.myopeninvoice.jsfbeans.model;

import com.bgh.myopeninvoice.db.dao.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.QTaxEntity;
import com.bgh.myopeninvoice.db.model.TaxEntity;
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
public class TaxEntityLazyModel extends LazyDataModel<TaxEntity> {

    private static Logger logger = LoggerFactory.getLogger(TaxEntityLazyModel.class);

    private List<TaxEntity> taxEntityList;

    private InvoiceDAO invoiceDAO;

    public TaxEntityLazyModel(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @Override
    public List<TaxEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        taxEntityList = new ArrayList<>();

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

        if (filters != null && !filters.isEmpty()) {
            for (Map.Entry<String, Object> stringObjectEntry : filters.entrySet()) {
                BooleanExpression temp = null;

                if (QTaxEntity.taxEntity.identifier.toString().equalsIgnoreCase("taxEntity." + stringObjectEntry.getKey())) {
                    temp = QTaxEntity.taxEntity.identifier.containsIgnoreCase(stringObjectEntry.getValue().toString());
                }

                if (temp != null) {
                    if (predicate == null) {
                        predicate = temp;
                    } else {
                        predicate.and(temp);
                    }
                }
            }
        }

        Page<TaxEntity> taxEntityPage = null;

        if (predicate == null) {
            taxEntityPage = invoiceDAO.getTaxRepository().findAll(pageRequest);
        } else {
            taxEntityPage = invoiceDAO.getTaxRepository().findAll(predicate, pageRequest);
        }

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
