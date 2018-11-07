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

package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by bcavlin on 23/03/17.
 */
@Data
@Entity
@Table(name = "CONTACT", schema = "INVOICE")
public class ContactEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "CONTACT_ID", nullable = false)
    private Integer contactId;

    @Basic
    @Column(name = "FIRST_NAME", nullable = false, length = 100)
    private String firstName;

    @Basic
    @Column(name = "LAST_NAME", nullable = true, length = 100)
    private String lastName;

    @Basic
    @Column(name = "MIDDLE_NAME", nullable = true, length = 100)
    private String middleName;

    @Basic
    @Column(name = "EMAIL", nullable = false, length = 255)
    private String email;

    @Basic
    @Column(name = "ADDRESS_LINE1", nullable = true, length = 500)
    private String addressLine1;

    @Basic
    @Column(name = "ADDRESS_LINE2", nullable = true, length = 500)
    private String addressLine2;

    @Basic
    @Column(name = "PHONE_1", nullable = true, length = 20)
    private String phone1;

    @Basic
    @Column(name = "USER_ID", nullable = true)
    private Integer userId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "contactsByContactId")
    private Collection<CompanyContactEntity> companyContactsByContactId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    private UserEntity userByUserId;


    @Transient
    public String getCompaniesList() {
        if (companyContactsByContactId != null) {
            return companyContactsByContactId.stream().map(
                    CompanyContactEntity::getCompaniesByCompanyId).
                    map(CompanyEntity::getCompanyName).collect(Collectors.joining(", "));
        }
        return null;
    }

}
