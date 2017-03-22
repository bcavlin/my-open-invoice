package com.bgh.myopeninvoice.db.dao;

import com.bgh.myopeninvoice.db.model.RolesEntity;
import com.bgh.myopeninvoice.db.model.TaxEntity;
import com.bgh.myopeninvoice.db.model.UserRoleEntity;
import com.bgh.myopeninvoice.db.model.UsersEntity;

import java.util.List;

/**
 * Created by bcavlin on 21/03/17.
 */
public interface InvoiceDAO {

    TaxRepository getTaxRepository();

    UsersRepository getUsersRepository();

    RolesRepository getRolesRepository();

    UserRoleEntity storeUserRoleEntity(List<UserRoleEntity> existingRoles, List<String> newRoles);

    TaxEntity saveTaxEntity(TaxEntity taxEntity);

    void deleteTaxEntity(Integer id);

    UsersEntity saveUsersEntity(UsersEntity usersEntity);

    void deleteUsersEntity(Integer id);

    void saveUserRolesEntity(UsersEntity user, List<RolesEntity> targetRoles);

}
