package com.bgh.myopeninvoice.jsfbeans;

import com.bgh.myopeninvoice.db.dao.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.RolesEntity;
import com.bgh.myopeninvoice.db.model.UserRoleEntity;
import com.bgh.myopeninvoice.db.model.UsersEntity;
import com.bgh.myopeninvoice.jsfbeans.model.UsersEntityLazyModel;
import com.bgh.myopeninvoice.utils.FacesUtils;
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

    private int pageSize = 20;

    @PostConstruct
    private void init() {
        logger.info("Initializing users entries");
        usersEntityList = new UsersEntityLazyModel(invoiceDAO);
        selectedUsersEntity = null;
    }

    private void refresh() {
        logger.info("Loading users entries");
        if (selectedUsersEntity != null) {
            selectedUsersEntity = invoiceDAO.getUsersRepository().findOne(selectedUsersEntity.getUserId());
        }
    }

    private void fillDualList() {
        final Iterable<RolesEntity> allRolesEntity = invoiceDAO.getRolesRepository().findAll();
        final List<UserRoleEntity> assignedRolesEntity = selectedUsersEntity.getUserRoleEntities();

        List<RolesEntity> sourceList = new ArrayList<>();
        List<RolesEntity> targetList = new ArrayList<>();

        rolesDualListModel = new DualListModel<>();

        allRolesEntity.forEach(sourceList::add);
        assignedRolesEntity.forEach(r -> targetList.add(r.getRolesEntity()));

        sourceList.removeAll(targetList);

        rolesDualListModel.setSource(sourceList);
        rolesDualListModel.setTarget(targetList);
    }

    public void ajaxChangeRowListener() {
        logger.info("Filling dual list");
        fillDualList();
    }

    public void newEntryListener(ActionEvent event) {
        logger.info("Creating new entity");
        selectedUsersEntity = new UsersEntity();
        selectedUsersEntity.setUserRoleEntities(new ArrayList<>()); //cannot be null
        fillDualList();
    }

    public void addOrEditEntryListener(ActionEvent event) throws Exception {
        if (selectedUsersEntity != null) {
            logger.info("Adding/editing entity {}", selectedUsersEntity.toString());
            selectedUsersEntity = invoiceDAO.saveUsersEntity(selectedUsersEntity);
            refresh();
            FacesUtils.addSuccessMessage("Entity record updated");
        } else {
            FacesUtils.addErrorMessage("Selected users entity is null");
        }
    }

    public void addOrEditEntryListener2(ActionEvent event) {
        if (selectedUsersEntity != null && rolesDualListModel != null) {
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
            invoiceDAO.deleteUsersEntity(selectedUsersEntity.getUserId());
            refresh();
            FacesUtils.addSuccessMessage("Entity deleted");
            selectedUsersEntity = null;
        } else {
            FacesUtils.addErrorMessage("Selected users entity is null");
        }
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
}
