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
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * Created by bcavlin on 01/04/17.
 */
@Data
@Entity
@Table(name = "CONTRACT", schema = "INVOICE")
public class ContractEntity implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CONTRACT_ID")
    private Integer contractId;

    @Basic
    @Column(name = "CONTRACT_IS_FOR", nullable = false)
    private Integer contractIsFor;

    @Basic
    @Column(name = "CONTRACT_SIGNED_WITH", nullable = false)
    private Integer contractSignedWith;

    @Basic
    @Column(name = "CONTRACT_SIGNED_WITH_SUBCONTRACT", nullable = true)
    private Integer contractSignedWithSubcontract;

    @Basic
    @Column(name = "RATE", nullable = false, precision = 32767)
    private BigDecimal rate;

    @Basic
    @Column(name = "RATE_UNIT", nullable = false, length = 10)
    private String rateUnit;

    @Basic
    @Column(name = "CCY_ID", nullable = false)
    private Integer ccyId;

    @Temporal(TemporalType.DATE)
    @Basic
    @Column(name = "VALID_FROM", nullable = false)
    private Date validFrom;

    @Temporal(TemporalType.DATE)
    @Basic
    @Column(name = "VALID_TO", nullable = true)
    private Date validTo;

    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = 100)
    private String description;

    @ManyToOne
    @JoinColumn(name = "CONTRACT_IS_FOR", referencedColumnName = "COMPANY_CONTACT_ID", nullable = false,
            insertable = false, updatable = false)
    private CompanyContactEntity companyContactByContractIsFor;

    @ManyToOne
    @JoinColumn(name = "CONTRACT_SIGNED_WITH", referencedColumnName = "COMPANY_ID", nullable = false,
            insertable = false, updatable = false)
    private CompanyEntity companiesByContractSignedWith;

    @ManyToOne
    @JoinColumn(name = "CONTRACT_SIGNED_WITH_SUBCONTRACT", referencedColumnName = "COMPANY_ID",
            insertable = false, updatable = false)
    private CompanyEntity companiesByContractSignedWithSubcontract;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JoinColumn(name = "CCY_ID", referencedColumnName = "CCY_ID", nullable = false,
            insertable = false, updatable = false)
    private CurrencyEntity currencyByCcyId;

    @Basic
    @Column(name = "CONTRACT_NUMBER", nullable = true, length = 50)
    private String contractNumber;

    @Basic
    @Column(name = "PURCHASE_ORDER", nullable = true, length = 50)
    private String purchaseOrder;

    @Lob
    @Column(name = "CONTENT", nullable = true)
    private byte[] content;

    @OneToMany(mappedBy = "contractsByCompanyContractTo")
    private Collection<InvoiceEntity> invoicesByContractId;


}
