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
import com.bgh.myopeninvoice.db.model.InvoiceEntity;
import com.bgh.myopeninvoice.db.model.QInvoiceEntity;
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
public class InvoiceEntityLazyModel extends LazyDataModel<InvoiceEntity> {

    private static Logger logger = LoggerFactory.getLogger(InvoiceEntityLazyModel.class);

    private List<InvoiceEntity> invoiceEntityList;

    private InvoiceDAO invoiceDAO;

    public InvoiceEntityLazyModel(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @Override
    public List<InvoiceEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        invoiceEntityList = new ArrayList<>();

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

                if (QInvoiceEntity.invoiceEntity.title.toString().equalsIgnoreCase("invoiceEntity." + stringObjectEntry.getKey())) {
                    temp = QInvoiceEntity.invoiceEntity.title.containsIgnoreCase(stringObjectEntry.getValue().toString());
                } else if (QInvoiceEntity.invoiceEntity.createdDate.toString().equalsIgnoreCase("invoiceEntity." + stringObjectEntry.getKey())) {
                    temp = QInvoiceEntity.invoiceEntity.createdDate.stringValue().containsIgnoreCase(stringObjectEntry.getValue().toString());
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

        Page<InvoiceEntity> invoiceEntityPage = null;

        if (predicate == null) {
            invoiceEntityPage = invoiceDAO.getInvoiceRepository().findAll(pageRequest);
        } else {
            invoiceEntityPage = invoiceDAO.getInvoiceRepository().findAll(predicate, pageRequest);
        }

        setRowCount((int) invoiceEntityPage.getTotalElements());
        setPageSize(getPageSize());

        for (InvoiceEntity invoiceEntity : invoiceEntityPage) {
            invoiceEntityList.add(invoiceEntity);
        }
        return invoiceEntityList;
    }

    @Override
    public Object getRowKey(InvoiceEntity object) {
        return object.getInvoiceId();
    }

    @Override
    public InvoiceEntity getRowData(String rowKey) {
        Integer id = Integer.valueOf(rowKey);
        if (invoiceEntityList != null) {
            for (InvoiceEntity invoiceEntity : invoiceEntityList) {
                if (id.equals(invoiceEntity.getInvoiceId())) {
                    return invoiceEntity;
                }
            }
        }
        return null;
    }
}
