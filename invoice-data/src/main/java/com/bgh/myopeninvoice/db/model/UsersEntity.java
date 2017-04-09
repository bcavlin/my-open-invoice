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

package com.bgh.myopeninvoice.db.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Created by bcavlin on 14/03/17.
 */
@Entity
@Table(name = "USERS", schema = "INVOICE", catalog = "INVOICEDB")
public class UsersEntity implements Serializable {
    private Integer userId;
    private String username;
    private String password;
    private Date lastLogged;
    private Boolean enabled;
    private Collection<ContactsEntity> contactsByUserId;
    private Collection<UserRoleEntity> userRolesByUserId;

    @Id
    @GeneratedValue
    @Column(name = "USER_ID", nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "USERNAME", nullable = false, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "PASSWORD", nullable = true, length = 200)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_LOGGED", nullable = false)
    public Date getLastLogged() {
        return lastLogged;
    }

    public void setLastLogged(Date lastLogged) {
        this.lastLogged = lastLogged;
    }

    @Basic
    @Column(name = "ENABLED", nullable = false)
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (lastLogged != null ? !lastLogged.equals(that.lastLogged) : that.lastLogged != null) return false;
        if (enabled != null ? !enabled.equals(that.enabled) : that.enabled != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (lastLogged != null ? lastLogged.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        return result;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "usersByUserId")
    public Collection<ContactsEntity> getContactsByUserId() {
        return contactsByUserId;
    }

    public void setContactsByUserId(Collection<ContactsEntity> contactsByUserId) {
        this.contactsByUserId = contactsByUserId;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "usersByUserId")
    public Collection<UserRoleEntity> getUserRolesByUserId() {
        return userRolesByUserId;
    }

    public void setUserRolesByUserId(Collection<UserRoleEntity> userRolesByUserId) {
        this.userRolesByUserId = userRolesByUserId;
    }

    @Override
    public String toString() {
        return "UsersEntity{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", lastLogged=" + lastLogged +
                ", enabled=" + enabled +
                '}';
    }
}
