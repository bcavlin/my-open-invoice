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

import com.bgh.myopeninvoice.db.dao.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.CompaniesEntity;
import com.bgh.myopeninvoice.db.model.ContractsEntity;
import com.bgh.myopeninvoice.db.model.QContractsEntity;
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
public class ContractsEntityLazyModel extends LazyDataModel<ContractsEntity> {

    private static Logger logger = LoggerFactory.getLogger(ContractsEntityLazyModel.class);

    private List<ContractsEntity> contractsEntityList;

    private InvoiceDAO invoiceDAO;

    private CompaniesEntity selectedCompaniesEntity;

    public ContractsEntityLazyModel(InvoiceDAO invoiceDAO, CompaniesEntity selectedCompaniesEntity) {
        this.invoiceDAO = invoiceDAO;
        this.selectedCompaniesEntity = selectedCompaniesEntity;
    }

    @Override
    public List<ContractsEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        contractsEntityList = new ArrayList<>();

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

        final BooleanExpression predicate = QContractsEntity.contractsEntity.contractSignedWith.eq(selectedCompaniesEntity.getCompanyId());

        Page<ContractsEntity> contractsEntityPage = null;

        if (predicate == null) {
            contractsEntityPage = invoiceDAO.getContractsRepository().findAll(pageRequest);
        } else {
            contractsEntityPage = invoiceDAO.getContractsRepository().findAll(predicate, pageRequest);
        }

        setRowCount((int) contractsEntityPage.getTotalElements());
        setPageSize(getPageSize());

        for (ContractsEntity contractsEntity : contractsEntityPage) {
            contractsEntityList.add(contractsEntity);
        }
        return contractsEntityList;
    }

    @Override
    public Object getRowKey(ContractsEntity object) {
        return object.getContractId();
    }

    @Override
    public ContractsEntity getRowData(String rowKey) {
        Integer id = Integer.valueOf(rowKey);
        if (contractsEntityList != null) {
            for (ContractsEntity contractsEntity : contractsEntityList) {
                if (id.equals(contractsEntity.getContractId())) {
                    return contractsEntity;
                }
            }
        }
        return null;
    }
}
