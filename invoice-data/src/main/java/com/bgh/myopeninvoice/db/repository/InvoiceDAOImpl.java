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

import com.bgh.myopeninvoice.db.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    private ContactRepository contactRepository;

    private CompanyRepository companyRepository;

    private CompanyContactRepository companyContactRepository;

    private ContractsRepository contractsRepository;

    private CurrencyRepository currencyRepository;

    private InvoiceRepository invoiceRepository;

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setInvoiceRepository(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public InvoiceRepository getInvoiceRepository() {
        return invoiceRepository;
    }

    @Autowired
    public void setCurrencyRepository(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
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
    public void setCompanyRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
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
    public ContactRepository getContactRepository() {
        return contactRepository;
    }

    @Override
    public CompanyRepository getCompanyRepository() {
        return companyRepository;
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
    public void saveUserRolesEntity(UserEntity userEntity, List<RoleEntity> targetRoles) {
        List<UserRoleEntity> add = new ArrayList<>();
        List<UserRoleEntity> remove = new ArrayList<>();
        //first add new roles if not already there
        iterateTargetRoles(userEntity, targetRoles, add);
        //then delete those that are extra
        iterateUserEntity(userEntity, targetRoles, remove);

        for (UserRoleEntity entity : remove) {
            userRoleRepository.delete(entity);
        }

        for (UserRoleEntity entity : add) {
            userRoleRepository.save(entity);
        }

    }

    private void iterateUserEntity(UserEntity userEntity, List<RoleEntity> targetRoles, List<UserRoleEntity> remove) {
        for (UserRoleEntity current : userEntity.getUserRolesByUserId()) {
            boolean found = false;
            for (RoleEntity targetRole : targetRoles) {
                if (current.getRoleByRoleId().getRoleId().equals(targetRole.getRoleId())) {
                    found = true;
                }
            }
            if (!found) {
                remove.add(current);
            }
        }
    }

    private void iterateTargetRoles(UserEntity userEntity, List<RoleEntity> targetRoles, List<UserRoleEntity> add) {
        for (RoleEntity target : targetRoles) {
            boolean found = false;
            for (UserRoleEntity currentRole : userEntity.getUserRolesByUserId()) {
                if (currentRole.getRoleByRoleId().getRoleId().equals(target.getRoleId())) {
                    found = true;
                }
            }
            if (!found) {
                UserRoleEntity newUserRoleEntity = new UserRoleEntity();
                newUserRoleEntity.setAssignedDate(new Date());
                newUserRoleEntity.setUserByUserId(userEntity);
                newUserRoleEntity.setRoleByRoleId(target);
                add.add(newUserRoleEntity);
            }
        }
    }

    @Override
    public void saveCompanyContactEntity(CompanyEntity selectedCompanyEntity, List<ContactEntity> targetContacts) {
        List<CompanyContactEntity> add = new ArrayList<>();
        List<CompanyContactEntity> remove = new ArrayList<>();

        prepareContactEntity(selectedCompanyEntity, targetContacts, add);
        //then delete those that are extra
        prepareCompanyContactEntity(selectedCompanyEntity, targetContacts, remove);

        for (CompanyContactEntity entity : remove) {
            companyContactRepository.delete(entity);
        }

        for (CompanyContactEntity entity : add) {
            companyContactRepository.save(entity);
        }

    }

    private void prepareCompanyContactEntity(CompanyEntity selectedCompanyEntity, List<ContactEntity> targetContacts, List<CompanyContactEntity> remove) {
        for (CompanyContactEntity current : selectedCompanyEntity.getCompanyContactsByCompanyId()) {
            boolean found = false;
            for (ContactEntity target : targetContacts) {
                if (current.getContactByContactId().getContactId().equals(target.getContactId())) {
                    found = true;
                }
            }
            if (!found) {
                remove.add(current);
            }
        }
    }

    private void prepareContactEntity(CompanyEntity selectedCompanyEntity, List<ContactEntity> targetContacts, List<CompanyContactEntity> add) {
        for (ContactEntity target : targetContacts) {
            boolean found = false;
            for (CompanyContactEntity current : selectedCompanyEntity.getCompanyContactsByCompanyId()) {
                if (current.getContactByContactId().getContactId().equals(target.getContactId())) {
                    found = true;
                }
            }
            if (!found) {
                CompanyContactEntity newCompanyContEnt = new CompanyContactEntity();
                newCompanyContEnt.setCompanyByCompanyId(selectedCompanyEntity);
                newCompanyContEnt.setContactByContactId(target);
                add.add(newCompanyContEnt);
            }
        }
    }

    @Override
    public ContractsRepository getContractsRepository() {
        return contractsRepository;
    }

    @Override
    public CompanyContactRepository getCompanyContactRepository() {
        return companyContactRepository;
    }

    @Override
    public Integer getInvoiceCounterSeq() {
        return jdbcTemplate.queryForObject("SELECT INVOICE.INVOICE_COUNTER_SEQ.NEXTVAL FROM DUAL", Integer.class);
    }

    private InvoiceItemsRepository invoiceItemsRepository;

    @Autowired
    public void setInvoiceItemsRepository(InvoiceItemsRepository invoiceItemsRepository) {
        this.invoiceItemsRepository = invoiceItemsRepository;
    }

    @Override
    public InvoiceItemsRepository getInvoiceItemsRepository() {
        return invoiceItemsRepository;
    }

    private AttachmentRepository attachmentRepository;

    @Override
    public AttachmentRepository getAttachmentRepository() {
        return attachmentRepository;
    }

    @Autowired
    public void setAttachmentRepository(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    private TimeSheetRepository timeSheetRepository;

    @Override
    public TimeSheetRepository getTimeSheetRepository() {
        return timeSheetRepository;
    }

    @Autowired
    public void setTimeSheetRepository(TimeSheetRepository timeSheetRepository) {
        this.timeSheetRepository = timeSheetRepository;
    }

    private ReportsRepository reportsRepository;

    @Override
    public ReportsRepository getReportsRepository() {
        return reportsRepository;
    }

    @Autowired
    public void setReportsRepository(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }
}
