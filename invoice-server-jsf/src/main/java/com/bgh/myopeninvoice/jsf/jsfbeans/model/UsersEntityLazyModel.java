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

import com.bgh.myopeninvoice.db.model.UserEntity;
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
public class UsersEntityLazyModel extends LazyDataModel<UserEntity> {

    //private static Logger logger = LoggerFactory.getLogger(UsersEntityLazyModel.class);

    private List<UserEntity> userEntityList;

    private InvoiceDAO invoiceDAO;

    public UsersEntityLazyModel(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @Override
    public List<UserEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        userEntityList = new ArrayList<>();

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

//                if (QUsersEntity.usersEntity.identifier.toString().equalsIgnoreCase("UserEntity." + stringObjectEntry.getKey())) {
//                    temp = QUsersEntity.usersEntity.identifier.containsIgnoreCase(stringObjectEntry.getValue().toString());
//                }
//
//                if (temp != null) {
//                    if (predicate == null) {
//                        predicate = temp;
//                    } else {
//                        predicate.and(temp);
//                    }
//                }
            }
        }

        Page<UserEntity> usersEntityPage = null;

        if (predicate == null) {
            usersEntityPage = invoiceDAO.getUsersRepository().findAll(pageRequest);
        } else {
            usersEntityPage = invoiceDAO.getUsersRepository().findAll(predicate, pageRequest);
        }

        setRowCount((int) usersEntityPage.getTotalElements());
        setPageSize(getPageSize());

        for (UserEntity UserEntity : usersEntityPage) {
            userEntityList.add(UserEntity);
        }
        return userEntityList;
    }

    @Override
    public Object getRowKey(UserEntity object) {
        return object.getUserId();
    }

    @Override
    public UserEntity getRowData(String rowKey) {
        Integer id = Integer.valueOf(rowKey);
        if (userEntityList != null) {
            for (UserEntity UserEntity : userEntityList) {
                if (id.equals(UserEntity.getUserId())) {
                    return UserEntity;
                }
            }
        }
        return null;
    }
}
