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

/**
 * Created by bcavlin on 14/03/17.
 */
@Data
@Entity
@Table(name = "COMPANY", schema = "INVOICE")
public class CompanyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_ID")
    private Integer companyId;

    @Basic
    @Column(name = "COMPANY_NAME", nullable = false, length = 255, unique = true)
    private String companyName;

    @Basic
    @Column(name = "SHORT_NAME", nullable = false, length = 10, unique = true)
    private String shortName;

    @Basic
    @Column(name = "ADDRESS_LINE_1", nullable = false, length = 500)
    private String addressLine1;

    @Basic
    @Column(name = "ADDRESS_LINE_2", nullable = true, length = 500)
    private String addressLine2;

    @Basic
    @Column(name = "PHONE_1", nullable = true, length = 20)
    private String phone1;

    @Basic
    @Column(name = "OWNED_BY_ME", nullable = false)
    private Boolean ownedByMe;

    @Basic
    @Column(name = "BUSINESS_NUMBER", nullable = true, length = 30)
    private String businessNumber;

    @Lob
    @Column(name = "CONTENT", nullable = true)
    private byte[] content;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "companiesByCompanyId")
    private Collection<CompanyContactEntity> companyContactsByCompanyId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "companiesByContractSignedWith")
    private Collection<ContractEntity> contractsByCompanyId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "companiesByContractSignedWithSubcontract")
    private Collection<ContractEntity> contractsByCompanyId0;

    @OneToMany(mappedBy = "companiesByCompanyTo")
    private Collection<InvoiceEntity> invoicesByCompanyId;

    @Basic
    @Column(name = "WEEK_START", nullable = false)
    private Integer weekStart;


    @Transient
    public String getContactsToList() {
        StringBuilder sb = new StringBuilder();
        for (CompanyContactEntity companyContactEntity : companyContactsByCompanyId) {
            if (sb.length() > 0) sb.append(",");
            sb.append(companyContactEntity.getContactsByContactId().getContactId());
        }
        return sb.toString();
    }

}
