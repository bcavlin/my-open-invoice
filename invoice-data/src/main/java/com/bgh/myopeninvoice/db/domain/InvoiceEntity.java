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
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * Created by bcavlin on 14/03/17.
 */
@Data
@Entity
@Table(name = "INVOICE", schema = "INVOICE")
public class InvoiceEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "INVOICE_ID", nullable = false)
    private Integer invoiceId;

    @Basic
    @Column(name = "COMPANY_TO", nullable = false)
    private Integer companyTo;

    @Basic
    @Column(name = "COMPANY_CONTACT_FROM", nullable = false)
    private Integer companyContactFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "FROM_DATE", nullable = false)
    private Date fromDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "TO_DATE", nullable = false)
    private Date toDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Basic
    @Column(name = "TITLE", nullable = false, length = 255)
    private String title;

    @Temporal(TemporalType.DATE)
    @Column(name = "DUE_DATE", nullable = false)
    private Date dueDate;

    @Basic
    @Column(name = "TAX_PERCENT", nullable = false, precision = 32767)
    private BigDecimal taxPercent;

    @Basic
    @Column(name = "NOTE", nullable = true, length = 2000)
    private String note;

    @Temporal(TemporalType.DATE)
    @Column(name = "PAID_DATE", nullable = true)
    private Date paidDate;

    @Basic
    @Column(name = "RATE", nullable = true, precision = 32767)
    private BigDecimal rate;

    @Basic
    @Column(name = "RATE_UNIT", nullable = true, length = 10)
    private String rateUnit;

    @Basic
    @Column(name = "CCY_ID", nullable = false)
    private Integer ccyId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "invoiceByInvoiceId")
    private Collection<AttachmentEntity> attachmentsByInvoiceId;

    @ManyToOne
    @JoinColumn(name = "COMPANY_TO", referencedColumnName = "COMPANY_ID", nullable = false,
            insertable = false, updatable = false)
    private CompanyEntity companiesByCompanyTo;

    @ManyToOne
    @JoinColumn(name = "COMPANY_CONTACT_FROM", referencedColumnName = "COMPANY_CONTACT_ID",
            nullable = false, insertable = false, updatable = false)
    private CompanyContactEntity companyContactByCompanyContactFrom;

    @ManyToOne
    @JoinColumn(name = "CCY_ID", referencedColumnName = "CCY_ID", nullable = false, insertable = false, updatable = false)
    private CurrencyEntity currencyByCcyId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "invoiceByInvoiceId")
    private Collection<InvoiceItemsEntity> invoiceItemsByInvoiceId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JoinColumn(name = "COMPANY_CONTRACT_TO", referencedColumnName = "CONTRACT_ID", insertable = false, updatable = false)
    private ContractEntity contractsByCompanyContractTo;

    @Basic
    @Column(name = "COMPANY_CONTRACT_TO", nullable = true)
    private Integer companyContractTo;


    @Formula("(select sum(e.quantity * case when e.unit = 'HOUR' then rate else 1 end) from invoice.invoice_items e where e.invoice_id = invoice_id)")
    private BigDecimal totalValue;

    public BigDecimal getTotalValue() {
        return totalValue == null ? new BigDecimal(0) : totalValue;
    }

    @Formula("(select sum(e.quantity * case when e.unit = 'HOUR' then rate else 1 end) * (tax_percent + 1) from invoice.invoice_items e where e.invoice_id = invoice_id)")
    private BigDecimal totalValueWithTax;

    public BigDecimal getTotalValueWithTax() {
        return totalValueWithTax == null ? new BigDecimal(0) : totalValueWithTax;

    }

}
