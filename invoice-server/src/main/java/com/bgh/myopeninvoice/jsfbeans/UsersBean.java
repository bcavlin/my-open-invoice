package com.bgh.myopeninvoice.jsfbeans;

import com.bgh.myopeninvoice.db.dao.UsersRepository;
import com.bgh.myopeninvoice.db.model.UsersEntity;
import com.bgh.myopeninvoice.jsfbeans.model.UsersEntityLazyModel;
import com.bgh.myopeninvoice.utils.FacesUtils;
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

/**
 * Created by bcavlin on 17/03/17.
 */
@ManagedBean
@ViewScoped
@Component
public class UsersBean implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(UsersBean.class);

    @Autowired
    private UsersRepository usersRepository;

    private LazyDataModel<UsersEntity> usersEntityList;

    private UsersEntity selectedUsersEntity;

    private int pageSize = 20;

    @PostConstruct
    public void refresh() {
        if (usersEntityList == null) {
            logger.info("Loading users entries");
            usersEntityList = new UsersEntityLazyModel(usersRepository);
        }
    }

    public void newEntryListener(ActionEvent event) {
        logger.info("Creating new entity");
        selectedUsersEntity = new UsersEntity();
    }

    public void addOrEditEntryListener(ActionEvent event) {
        if (selectedUsersEntity != null) {
            logger.info("Adding/editing entity {}", selectedUsersEntity.toString());
            selectedUsersEntity = usersRepository.save(selectedUsersEntity);
            refresh();
            FacesUtils.addSuccessMessage("New entity created");
        } else {
            FacesUtils.addErrorMessage("Selected users entity is null");
        }
    }

    public void deleteEntryListener(ActionEvent event) {
        if (selectedUsersEntity != null) {
            logger.info("Deleting entity {}", selectedUsersEntity.toString());
            usersRepository.delete(selectedUsersEntity.getUserId());
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

    public void setusersEntityList(LazyDataModel<UsersEntity> usersEntityList) {
        this.usersEntityList = usersEntityList;
    }

    public UsersEntity getSelectedUsersEntity() {
        return selectedUsersEntity;
    }

    public void setSelectedUsersEntity(UsersEntity selectedUsersEntity) {
        this.selectedUsersEntity = selectedUsersEntity;
    }

}
