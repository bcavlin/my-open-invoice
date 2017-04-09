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

import com.bgh.myopeninvoice.db.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bcavlin on 21/03/17.
 */
@Repository("invoiceDAO")
public class InvoiceDAOImpl implements InvoiceDAO {

    private RolesRepository rolesRepository;

    private TaxRepository taxRepository;

    private UsersRepository usersRepository;

    private UserRoleRepository userRoleRepository;

    private ContactsRepository contactsRepository;

    private CompaniesRepository companiesRepository;

    private CompanyContactRepository companyContactRepository;

    private ContractsRepository contractsRepository;

    private CurrencyRepository currencyRepository;

    @Autowired
    public void setCurrencyRepository(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Autowired
    public void setContactsRepository(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    @Override
    public CurrencyRepository getCurrencyRepository() {
        return currencyRepository;
    }

    @Autowired
    public void setRolesRepository(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Autowired
    public void setCompaniesRepository(CompaniesRepository companiesRepository) {
        this.companiesRepository = companiesRepository;
    }

    @Autowired
    public void setCompanyContactRepository(CompanyContactRepository companyContactRepository) {
        this.companyContactRepository = companyContactRepository;
    }

    @Autowired
    public void setContractsRepository(ContractsRepository contractsRepository) {
        this.contractsRepository = contractsRepository;
    }

    @Autowired
    public void setTaxRepository(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public ContactsRepository getContactsRepository() {
        return contactsRepository;
    }

    @Override
    public CompaniesRepository getCompaniesRepository() {
        return companiesRepository;
    }

    @Override
    public TaxRepository getTaxRepository() {
        return taxRepository;
    }

    @Override
    public UsersRepository getUsersRepository() {
        return usersRepository;
    }

    @Override
    public RolesRepository getRolesRepository() {
        return rolesRepository;
    }

    @Transactional
    @Override
    public void saveUserRolesEntity(UsersEntity usersEntity, List<RolesEntity> targetRoles) {
        List<UserRoleEntity> add = new ArrayList<>();
        List<UserRoleEntity> remove = new ArrayList<>();
        //first add new roles if not already there
        for (RolesEntity target : targetRoles) {
            boolean found = false;
            for (UserRoleEntity currentRole : usersEntity.getUserRolesByUserId()) {
                if (currentRole.getRolesByRoleId().getRoleId().equals(target.getRoleId())) {
                    found = true;
                }
            }
            if (!found) {
                UserRoleEntity _new = new UserRoleEntity();
                _new.setDateAssigned(new Date());
                _new.setUsersByUserId(usersEntity);
                _new.setRolesByRoleId(target);
                add.add(_new);
            }
        }
        //then delete those that are extra
        for (UserRoleEntity current : usersEntity.getUserRolesByUserId()) {
            boolean found = false;
            for (RolesEntity targetRole : targetRoles) {
                if (current.getRolesByRoleId().getRoleId().equals(targetRole.getRoleId())) {
                    found = true;
                }
            }
            if (!found) {
                remove.add(current);
            }
        }

        for (UserRoleEntity entity : remove) {
            userRoleRepository.delete(entity.getUserRoleId());
        }
        userRoleRepository.save(add);
    }

    @Override
    public void saveCompanyContactEntity(CompaniesEntity selectedCompaniesEntity, List<ContactsEntity> targetContacts) {
        List<CompanyContactEntity> add = new ArrayList<>();
        List<CompanyContactEntity> remove = new ArrayList<>();

        for (ContactsEntity target : targetContacts) {
            boolean found = false;
            for (CompanyContactEntity current : selectedCompaniesEntity.getCompanyContactsByCompanyId()) {
                if (current.getContactsByContactId().getContactId().equals(target.getContactId())) {
                    found = true;
                }
            }
            if (!found) {
                CompanyContactEntity _new = new CompanyContactEntity();
                _new.setCompaniesByCompanyId(selectedCompaniesEntity);
                _new.setContactsByContactId(target);
                add.add(_new);
            }
        }
        //then delete those that are extra
        for (CompanyContactEntity current : selectedCompaniesEntity.getCompanyContactsByCompanyId()) {
            boolean found = false;
            for (ContactsEntity target : targetContacts) {
                if (current.getContactsByContactId().getContactId().equals(target.getContactId())) {
                    found = true;
                }
            }
            if (!found) {
                remove.add(current);
            }
        }

        for (CompanyContactEntity entity : remove) {
            companyContactRepository.delete(entity.getCompanyContactId());
        }
        companyContactRepository.save(add);
    }

    @Override
    public ContractsRepository getContractsRepository() {
        return contractsRepository;
    }

    @Override
    public CompanyContactRepository getCompanyContactRepository() {
        return companyContactRepository;
    }
}
