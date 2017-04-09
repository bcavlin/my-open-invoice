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

/**
 * Created by bcavlin on 14/03/17.
 */
@Entity
@Table(name = "ROLES", schema = "INVOICE", catalog = "INVOICEDB")
public class RolesEntity implements Serializable {
    private Integer roleId;
    private String roleName;
    private Collection<UserRoleEntity> userRolesByRoleId;

    @Id
    @Column(name = "ROLE_ID", nullable = false)
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "ROLE_NAME", nullable = false, length = 50)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolesEntity that = (RolesEntity) o;

        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (roleName != null ? !roleName.equals(that.roleName) : that.roleName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId != null ? roleId.hashCode() : 0;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        return result;
    }

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "rolesByRoleId")
    public Collection<UserRoleEntity> getUserRolesByRoleId() {
        return userRolesByRoleId;
    }

    public void setUserRolesByRoleId(Collection<UserRoleEntity> userRolesByRoleId) {
        this.userRolesByRoleId = userRolesByRoleId;
    }

    @Override
    public String toString() {
        return "RolesEntity{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
