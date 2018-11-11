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
 * Created by bcavlin on 23/03/17.
 */
@Data
@Entity
@Table(name = "COMPANY_CONTACT", schema = "INVOICE")
public class CompanyContactEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_CONTACT_ID")
    private Integer companyContactId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID", nullable = false)
    private CompanyEntity companiesByCompanyId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CONTACT_ID", referencedColumnName = "CONTACT_ID", nullable = false)
    private ContactEntity contactsByContactId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "companyContactByContractIsFor")
    private Collection<ContractEntity> contractsByCompanyContactId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "companyContactByCompanyContactFrom")
    private Collection<InvoiceEntity> invoicesByCompanyContactId;

}
