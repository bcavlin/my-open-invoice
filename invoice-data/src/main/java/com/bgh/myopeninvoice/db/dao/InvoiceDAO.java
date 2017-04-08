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

    void saveUserRolesEntity(UsersEntity user, List<RolesEntity> targetRoles);

    void saveCompanyContactEntity(CompaniesEntity selectedCompaniesEntity, List<ContactsEntity> target);

    ContractsRepository getContractsRepository();

    CompanyContactRepository getCompanyContactRepository();
}
