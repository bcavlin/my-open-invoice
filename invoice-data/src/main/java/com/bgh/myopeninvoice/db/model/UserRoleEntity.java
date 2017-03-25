package com.bgh.myopeninvoice.db.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    private Date dateAssigned;
    private RolesEntity rolesByRoleId;
    private UsersEntity usersByUserId;
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

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_ASSIGNED", nullable = false)
    public Date getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(Date dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleEntity)) return false;

        UserRoleEntity that = (UserRoleEntity) o;

        if (userRoleId != null ? !userRoleId.equals(that.userRoleId) : that.userRoleId != null) return false;
        if (dateAssigned != null ? !dateAssigned.equals(that.dateAssigned) : that.dateAssigned != null) return false;
        if (rolesByRoleId != null ? !rolesByRoleId.equals(that.rolesByRoleId) : that.rolesByRoleId != null)
            return false;
        return usersByUserId != null ? usersByUserId.equals(that.usersByUserId) : that.usersByUserId == null;

    }

    @Override
    public int hashCode() {
        int result = userRoleId != null ? userRoleId.hashCode() : 0;
        result = 31 * result + (dateAssigned != null ? dateAssigned.hashCode() : 0);
        result = 31 * result + (rolesByRoleId != null ? rolesByRoleId.hashCode() : 0);
        result = 31 * result + (usersByUserId != null ? usersByUserId.hashCode() : 0);
        return result;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID", nullable = false)
    public RolesEntity getRolesByRoleId() {
        return rolesByRoleId;
    }

    public void setRolesByRoleId(RolesEntity rolesByRoleId) {
        this.rolesByRoleId = rolesByRoleId;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false)
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @Override
    public String toString() {
        return "UserRoleEntity{" +
                "userRoleId=" + userRoleId +
                ", dateAssigned=" + dateAssigned +
                ", rolesByRoleId=" + rolesByRoleId +
                ", usersByUserId=" + usersByUserId +
                ", addOrKeep=" + addOrKeep +
                '}';
    }
}
