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

package com.bgh.myopeninvoice.db.dao;

import com.bgh.myopeninvoice.db.model.CompaniesEntity;
import com.bgh.myopeninvoice.db.model.ContactsEntity;
import com.bgh.myopeninvoice.db.model.RolesEntity;
import com.bgh.myopeninvoice.db.model.UsersEntity;

import java.util.List;

/**
 * Created by bcavlin on 21/03/17.
 */
public interface InvoiceDAO {

    TaxRepository getTaxRepository();

    UsersRepository getUsersRepository();

    RolesRepository getRolesRepository();

    ContactsRepository getContactsRepository();

    CompaniesRepository getCompaniesRepository();

    CurrencyRepository getCurrencyRepository();

    InvoiceRepository getInvoiceRepository();

    void saveUserRolesEntity(UsersEntity user, List<RolesEntity> targetRoles);

    void saveCompanyContactEntity(CompaniesEntity selectedCompaniesEntity, List<ContactsEntity> target);

    ContractsRepository getContractsRepository();

    CompanyContactRepository getCompanyContactRepository();

    Integer getInvoiceCounterSeq();

    InvoiceItemsRepository getInvoiceItemsRepository();

    AttachmentRepository getAttachmentRepository();
}
