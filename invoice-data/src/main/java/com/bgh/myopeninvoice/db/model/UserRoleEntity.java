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

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by bcavlin on 14/03/17.
 */
@Data
@Entity
@Table(name = "USER_ROLE", schema = "INVOICE", catalog = "INVOICEDB")
public class UserRoleEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "USER_ROLE_ID", nullable = false)
    private Integer userRoleId;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_ASSIGNED", nullable = false)
    private Date dateAssigned;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID", nullable = false)
    private RoleEntity roleByRoleId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false)
    private UserEntity userByUserId;

    @Transient
    private boolean addOrKeep;

}
