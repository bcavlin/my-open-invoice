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

package com.bgh.myopeninvoice.db.repository;

import com.bgh.myopeninvoice.db.domain.CompanyEntity;
import com.bgh.myopeninvoice.db.domain.ContactEntity;
import com.bgh.myopeninvoice.db.domain.RoleEntity;
import com.bgh.myopeninvoice.db.domain.UserEntity;

import java.util.List;

/**
 * Created by bcavlin on 21/03/17.
 */
public interface InvoiceDAO {

    TaxRepository getTaxRepository();

    UsersRepository getUsersRepository();

    RolesRepository getRolesRepository();

    ContactRepository getContactRepository();

    CompanyRepository getCompanyRepository();

    CurrencyRepository getCurrencyRepository();

    InvoiceRepository getInvoiceRepository();

    void saveUserRolesEntity(UserEntity user, List<RoleEntity> targetRoles);

    void saveCompanyContactEntity(CompanyEntity selectedCompanyEntity, List<ContactEntity> target);

    ContractsRepository getContractsRepository();

    CompanyContactRepository getCompanyContactRepository();

    Integer getInvoiceCounterSeq();

    InvoiceItemsRepository getInvoiceItemsRepository();

    AttachmentRepository getAttachmentRepository();

    TimeSheetRepository getTimeSheetRepository();

    ReportsRepository getReportsRepository();
}
