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

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private TaxRepository taxRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ContactsRepository contactsEntity;

    @Autowired
    private CompaniesRepository companiesRepository;

    @Autowired
    private CompanyContactRepository companyContactRepository;

    @Autowired
    private ContractsRepository contractsRepository;

    @Override
    public ContactsRepository getContactsRepository() {
        return contactsEntity;
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
                if(currentRole.getRolesByRoleId().getRoleId().equals(target.getRoleId())){
                    found = true;
                }
            }
            if(!found){
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
                if(current.getRolesByRoleId().getRoleId().equals(targetRole.getRoleId())){
                    found = true;
                }
            }
            if(!found){
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
                if(current.getContactsByContactId().getContactId().equals(target.getContactId())){
                    found = true;
                }
            }
            if(!found){
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
                if(current.getContactsByContactId().getContactId().equals(target.getContactId())){
                    found = true;
                }
            }
            if(!found){
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
