package com.bgh.myopeninvoice.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by bcavlin on 14/03/17.
 */
@Entity
@Table(name = "USER_ROLE", schema = "INVOICE", catalog = "INVOICEDB")
public class UserRoleEntity implements Serializable {
    private Integer userRoleId;
//    private Integer userId;
//    private Integer roleId;
    private Date dateAssigned;
    private RolesEntity rolesEntity;
    private UsersEntity usersEntity;
    private boolean addOrKeep;

    @Transient
    boolean getAddOrKeep() {
        return addOrKeep;
    }

    void setAddOrKeep(boolean addOrKeep) {
        this.addOrKeep = addOrKeep;
    }

    @Id
    @GeneratedValue
    @Column(name = "USER_ROLE_ID", nullable = false)
    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

//    @Basic
//    @Column(name = "USER_ID", nullable = false)
//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//
//    @Basic
//    @Column(name = "ROLE_ID", nullable = false)
//    public Integer getRoleId() {
//        return roleId;
//    }
//
//    public void setRoleId(Integer roleId) {
//        this.roleId = roleId;
//    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_ASSIGNED", nullable = false)
    public Date getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(Date dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
    public RolesEntity getRolesEntity() {
        return rolesEntity;
    }

    public void setRolesEntity(RolesEntity rolesEntity) {
        this.rolesEntity = rolesEntity;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    public UsersEntity getUsersEntity() {
        return usersEntity;
    }

    public void setUsersEntity(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleEntity)) return false;

        UserRoleEntity that = (UserRoleEntity) o;

        if (addOrKeep != that.addOrKeep) return false;
        if (userRoleId != null ? !userRoleId.equals(that.userRoleId) : that.userRoleId != null) return false;
        if (dateAssigned != null ? !dateAssigned.equals(that.dateAssigned) : that.dateAssigned != null) return false;
        if (rolesEntity != null ? !rolesEntity.equals(that.rolesEntity) : that.rolesEntity != null) return false;
        return usersEntity != null ? usersEntity.equals(that.usersEntity) : that.usersEntity == null;

    }

    @Override
    public int hashCode() {
        int result = userRoleId != null ? userRoleId.hashCode() : 0;
        result = 31 * result + (dateAssigned != null ? dateAssigned.hashCode() : 0);
        result = 31 * result + (rolesEntity != null ? rolesEntity.hashCode() : 0);
        result = 31 * result + (usersEntity != null ? usersEntity.hashCode() : 0);
        result = 31 * result + (addOrKeep ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserRoleEntity{" +
                "userRoleId=" + userRoleId +
                ", dateAssigned=" + dateAssigned +
                ", rolesEntity=" + rolesEntity.getRoleId() +
                ", usersEntity=" + usersEntity.getUserId() +
                ", addOrKeep=" + addOrKeep +
                '}';
    }
}
