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
import com.bgh.myopeninvoice.db.model.ContractEntity;
import com.bgh.myopeninvoice.db.model.QContractsEntity;
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
public class ContractsEntityLazyModel extends LazyDataModel<ContractEntity> {

    //private static Logger logger = LoggerFactory.getLogger(ContractsEntityLazyModel.class);

    private List<ContractEntity> contractEntityList;

    private InvoiceDAO invoiceDAO;

    private CompanyEntity selectedCompanyEntity;

    public ContractsEntityLazyModel(InvoiceDAO invoiceDAO, CompanyEntity selectedCompanyEntity) {
        this.invoiceDAO = invoiceDAO;
        this.selectedCompanyEntity = selectedCompanyEntity;
    }

    @Override
    public List<ContractEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        contractEntityList = new ArrayList<>();

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

        final BooleanExpression predicate = QContractsEntity.contractsEntity.contractSignedWith.eq(selectedCompanyEntity.getCompanyId());

        Page<ContractEntity> contractsEntityPage = null;

        if (predicate == null) {
            contractsEntityPage = invoiceDAO.getContractsRepository().findAll(pageRequest);
        } else {
            contractsEntityPage = invoiceDAO.getContractsRepository().findAll(predicate, pageRequest);
        }

        setRowCount((int) contractsEntityPage.getTotalElements());
        setPageSize(getPageSize());

        for (ContractEntity contractEntity : contractsEntityPage) {
            contractEntityList.add(contractEntity);
        }
        return contractEntityList;
    }

    @Override
    public Object getRowKey(ContractEntity object) {
        return object.getContractId();
    }

    @Override
    public ContractEntity getRowData(String rowKey) {
        Integer id = Integer.valueOf(rowKey);
        if (contractEntityList != null) {
            for (ContractEntity contractEntity : contractEntityList) {
                if (id.equals(contractEntity.getContractId())) {
                    return contractEntity;
                }
            }
        }
        return null;
    }
}
