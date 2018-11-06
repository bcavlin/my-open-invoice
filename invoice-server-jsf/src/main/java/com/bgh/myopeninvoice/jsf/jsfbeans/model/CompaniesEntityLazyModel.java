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

package com.bgh.myopeninvoice.jsf.jsfbeans.model;

import com.bgh.myopeninvoice.db.model.CompanyEntity;
import com.bgh.myopeninvoice.db.repository.InvoiceDAO;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bcavlin on 17/03/17.
 */
public class CompaniesEntityLazyModel extends LazyDataModel<CompanyEntity> {

    //private static Logger logger = LoggerFactory.getLogger(CompaniesEntityLazyModel.class);

    private List<CompanyEntity> companyEntityList;

    private InvoiceDAO invoiceDAO;

    public CompaniesEntityLazyModel(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @Override
    public List<CompanyEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        companyEntityList = new ArrayList<>();

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

        Page<CompanyEntity> companiesEntityPage = null;

        if (predicate == null) {
            companiesEntityPage = invoiceDAO.getCompaniesRepository().findAll(pageRequest);
        } else {
            companiesEntityPage = invoiceDAO.getCompaniesRepository().findAll(predicate, pageRequest);
        }

        setRowCount((int) companiesEntityPage.getTotalElements());
        setPageSize(getPageSize());

        for (CompanyEntity companyEntity : companiesEntityPage) {
            companyEntityList.add(companyEntity);
        }
        return companyEntityList;
    }

    @Override
    public Object getRowKey(CompanyEntity object) {
        return object.getCompanyId();
    }

    @Override
    public CompanyEntity getRowData(String rowKey) {
        Integer id = Integer.valueOf(rowKey);
        if (companyEntityList != null) {
            for (CompanyEntity companyEntity : companyEntityList) {
                if (id.equals(companyEntity.getCompanyId())) {
                    return companyEntity;
                }
            }
        }
        return null;
    }
}
