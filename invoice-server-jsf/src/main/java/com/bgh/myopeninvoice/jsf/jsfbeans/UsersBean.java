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

package com.bgh.myopeninvoice.jsf.jsfbeans;

import com.bgh.myopeninvoice.db.model.RoleEntity;
import com.bgh.myopeninvoice.db.repository.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.UserRoleEntity;
import com.bgh.myopeninvoice.db.model.UserEntity;
import com.bgh.myopeninvoice.jsf.jsfbeans.model.UsersEntityLazyModel;
import com.bgh.myopeninvoice.jsf.utils.CustomUtils;
import com.bgh.myopeninvoice.jsf.utils.FacesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bcavlin on 17/03/17.
 */
@Slf4j
@ManagedBean
@ViewScoped
@Component
public class UsersBean implements Serializable {

    @Autowired
    private InvoiceDAO invoiceDAO;

    private LazyDataModel<UserEntity> usersEntityList;

    private DualListModel<RoleEntity> rolesDualListModel;

    private UserEntity selectedUserEntity;

    private String password;
    private String passwordRepeat;

    private int pageSize = 20;

    @PostConstruct
    private void init() {
        log.info("Initializing users entries");
        usersEntityList = new UsersEntityLazyModel(invoiceDAO);
        selectedUserEntity = null;
        setPassword(null);
        setPasswordRepeat(null);

    }

    private void refresh() {
        log.info("Loading users entries");
        if (selectedUserEntity != null) {
            selectedUserEntity = invoiceDAO.getUsersRepository().findOne(selectedUserEntity.getUserId());
        }
        setPassword(null);
        setPasswordRepeat(null);

    }

    public boolean hasRole(UserEntity user, String roleName) {
        if (user != null) {
            for (UserRoleEntity userRoleEntity : user.getUserRolesByUserId()) {
                if (roleName.equalsIgnoreCase(userRoleEntity.getRoleByRoleId().getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void fillDualList() {
        final Iterable<RoleEntity> allRolesEntity = invoiceDAO.getRolesRepository().findAll();
        final Collection<UserRoleEntity> assignedRolesEntity = selectedUserEntity.getUserRolesByUserId();

        List<RoleEntity> sourceList = new ArrayList<>();
        List<RoleEntity> targetList = new ArrayList<>();

        rolesDualListModel = new DualListModel<>();

        allRolesEntity.forEach(sourceList::add);
        assignedRolesEntity.forEach(r -> targetList.add(r.getRoleByRoleId()));

        sourceList.removeAll(targetList);

        rolesDualListModel.setSource(sourceList);
        rolesDualListModel.setTarget(targetList);
    }

    public void ajaxChangeRowListener() {
        log.info("Filling dual list");
        fillDualList();
        setPassword(null);
        setPasswordRepeat(null);

    }

    public void newEntryListener(ActionEvent event) {
        log.info("Creating new entity");
        selectedUserEntity = new UserEntity();
        selectedUserEntity.setUserRolesByUserId(new ArrayList<>()); //cannot be null
        fillDualList();
        setPassword(null);
        setPasswordRepeat(null);

    }

    public void addOrEditEntryListener(ActionEvent event) throws Exception {
        if (selectedUserEntity != null) {
            boolean success = true;

            if (StringUtils.isNotBlank(password) && password.length() > 5) {
                selectedUserEntity.setPassword(CustomUtils.encodePassword(password));
            }

            if (StringUtils.isNotBlank(password) && password.length() <= 5) {
                FacesUtils.addErrorMessage("Password has to be greater than 5 in length");
                success = false;
            }

            if (selectedUserEntity.getEnabled() && selectedUserEntity.getPassword() == null) {
                FacesUtils.addErrorMessage("You cannot enable user without a password");
                success = false;
            }

            if (success) {
                RequestContext.getCurrentInstance().execute("PF('users-form-dialog').hide()");

                log.info("Adding/editing entity {}", selectedUserEntity.toString());
                selectedUserEntity = invoiceDAO.getUsersRepository().save(selectedUserEntity);
                refresh();
                FacesUtils.addSuccessMessage("Entity record updated");
                setPassword(null);
                setPasswordRepeat(null);
            }
        } else {
            FacesUtils.addErrorMessage("Selected users entity is null");
        }

    }

    public void addOrEditEntryListener2(ActionEvent event) {
        if (selectedUserEntity != null && rolesDualListModel != null) {
            RequestContext.getCurrentInstance().execute("PF('roles-form-dialog').hide()");

            log.info("Adding/editing entity {}", rolesDualListModel.getTarget().toString());
            invoiceDAO.saveUserRolesEntity(selectedUserEntity, rolesDualListModel.getTarget());
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
        } else {
            FacesUtils.addErrorMessage("Selected users entity is null");
        }
    }

    public void deleteEntryListener(ActionEvent event) {
        if (selectedUserEntity != null) {
            log.info("Deleting entity {}", selectedUserEntity.toString());
            invoiceDAO.getUsersRepository().delete(selectedUserEntity.getUserId());
            refresh();
            FacesUtils.addSuccessMessage("Entity deleted");
            selectedUserEntity = null;
        } else {
            FacesUtils.addErrorMessage("Selected users entity is null");
        }
        setPassword(null);
        setPasswordRepeat(null);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public LazyDataModel<UserEntity> getUsersEntityList() {
        return usersEntityList;
    }

    public void setUsersEntityList(LazyDataModel<UserEntity> usersEntityList) {
        this.usersEntityList = usersEntityList;
    }

    public UserEntity getSelectedUserEntity() {
        return selectedUserEntity;
    }

    public void setSelectedUserEntity(UserEntity selectedUserEntity) {
        this.selectedUserEntity = selectedUserEntity;
    }

    public DualListModel<RoleEntity> getRolesDualListModel() {
        return rolesDualListModel;
    }

    public void setRolesDualListModel(DualListModel<RoleEntity> rolesDualListModel) {
        this.rolesDualListModel = rolesDualListModel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}
