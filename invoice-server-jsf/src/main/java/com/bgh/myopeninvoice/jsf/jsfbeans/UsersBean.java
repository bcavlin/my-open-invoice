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

import com.bgh.myopeninvoice.db.repository.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.RolesEntity;
import com.bgh.myopeninvoice.db.model.UserRoleEntity;
import com.bgh.myopeninvoice.db.model.UsersEntity;
import com.bgh.myopeninvoice.jsf.jsfbeans.model.UsersEntityLazyModel;
import com.bgh.myopeninvoice.jsf.utils.CustomUtils;
import com.bgh.myopeninvoice.jsf.utils.FacesUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@ManagedBean
@ViewScoped
@Component
public class UsersBean implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(UsersBean.class);

    @Autowired
    private InvoiceDAO invoiceDAO;

    private LazyDataModel<UsersEntity> usersEntityList;

    private DualListModel<RolesEntity> rolesDualListModel;

    private UsersEntity selectedUsersEntity;

    private String password;
    private String passwordRepeat;

    private int pageSize = 20;

    @PostConstruct
    private void init() {
        logger.info("Initializing users entries");
        usersEntityList = new UsersEntityLazyModel(invoiceDAO);
        selectedUsersEntity = null;
        setPassword(null);
        setPasswordRepeat(null);

    }

    private void refresh() {
        logger.info("Loading users entries");
        if (selectedUsersEntity != null) {
            selectedUsersEntity = invoiceDAO.getUsersRepository().findOne(selectedUsersEntity.getUserId());
        }
        setPassword(null);
        setPasswordRepeat(null);

    }

    public boolean hasRole(UsersEntity user, String roleName) {
        if (user != null) {
            for (UserRoleEntity userRoleEntity : user.getUserRolesByUserId()) {
                if (roleName.equalsIgnoreCase(userRoleEntity.getRolesByRoleId().getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void fillDualList() {
        final Iterable<RolesEntity> allRolesEntity = invoiceDAO.getRolesRepository().findAll();
        final Collection<UserRoleEntity> assignedRolesEntity = selectedUsersEntity.getUserRolesByUserId();

        List<RolesEntity> sourceList = new ArrayList<>();
        List<RolesEntity> targetList = new ArrayList<>();

        rolesDualListModel = new DualListModel<>();

        allRolesEntity.forEach(sourceList::add);
        assignedRolesEntity.forEach(r -> targetList.add(r.getRolesByRoleId()));

        sourceList.removeAll(targetList);

        rolesDualListModel.setSource(sourceList);
        rolesDualListModel.setTarget(targetList);
    }

    public void ajaxChangeRowListener() {
        logger.info("Filling dual list");
        fillDualList();
        setPassword(null);
        setPasswordRepeat(null);

    }

    public void newEntryListener(ActionEvent event) {
        logger.info("Creating new entity");
        selectedUsersEntity = new UsersEntity();
        selectedUsersEntity.setUserRolesByUserId(new ArrayList<>()); //cannot be null
        fillDualList();
        setPassword(null);
        setPasswordRepeat(null);

    }

    public void addOrEditEntryListener(ActionEvent event) throws Exception {
        if (selectedUsersEntity != null) {
            boolean success = true;

            if (StringUtils.isNotBlank(password) && password.length() > 5) {
                selectedUsersEntity.setPassword(CustomUtils.encodePassword(password));
            }

            if (StringUtils.isNotBlank(password) && password.length() <= 5) {
                FacesUtils.addErrorMessage("Password has to be greater than 5 in length");
                success = false;
            }

            if (selectedUsersEntity.getEnabled() && selectedUsersEntity.getPassword() == null) {
                FacesUtils.addErrorMessage("You cannot enable user without a password");
                success = false;
            }

            if (success) {
                RequestContext.getCurrentInstance().execute("PF('users-form-dialog').hide()");

                logger.info("Adding/editing entity {}", selectedUsersEntity.toString());
                selectedUsersEntity = invoiceDAO.getUsersRepository().save(selectedUsersEntity);
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
        if (selectedUsersEntity != null && rolesDualListModel != null) {
            RequestContext.getCurrentInstance().execute("PF('roles-form-dialog').hide()");

            logger.info("Adding/editing entity {}", rolesDualListModel.getTarget().toString());
            invoiceDAO.saveUserRolesEntity(selectedUsersEntity, rolesDualListModel.getTarget());
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
        } else {
            FacesUtils.addErrorMessage("Selected users entity is null");
        }
    }

    public void deleteEntryListener(ActionEvent event) {
        if (selectedUsersEntity != null) {
            logger.info("Deleting entity {}", selectedUsersEntity.toString());
            invoiceDAO.getUsersRepository().delete(selectedUsersEntity.getUserId());
            refresh();
            FacesUtils.addSuccessMessage("Entity deleted");
            selectedUsersEntity = null;
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

    public LazyDataModel<UsersEntity> getUsersEntityList() {
        return usersEntityList;
    }

    public void setUsersEntityList(LazyDataModel<UsersEntity> usersEntityList) {
        this.usersEntityList = usersEntityList;
    }

    public UsersEntity getSelectedUsersEntity() {
        return selectedUsersEntity;
    }

    public void setSelectedUsersEntity(UsersEntity selectedUsersEntity) {
        this.selectedUsersEntity = selectedUsersEntity;
    }

    public DualListModel<RolesEntity> getRolesDualListModel() {
        return rolesDualListModel;
    }

    public void setRolesDualListModel(DualListModel<RolesEntity> rolesDualListModel) {
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
