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
@Entity
@Table(name = "INVOICE", schema = "INVOICE", catalog = "INVOICEDB")
public class InvoiceEntity implements Serializable {
    private Integer invoiceId;
    private Integer companyTo;
    private Integer companyContactFrom;
    private Date fromDate;
    private Date toDate;
    private Date createdDate;
    private String title;
    private Date dueDate;
    private BigDecimal taxPercent;
    private String note;
    private Date paidDate;
    private BigDecimal rate;
    private String rateUnit;
    private Integer ccyId;
    private Collection<AttachmentEntity> attachmentsByInvoiceId;
    private CompaniesEntity companiesByCompanyTo;
    private CompanyContactEntity companyContactByCompanyContactFrom;
    private CurrencyEntity currencyByCcyId;
    private Collection<InvoiceItemsEntity> invoiceItemsByInvoiceId;

    @Id
    @GeneratedValue
    @Column(name = "INVOICE_ID", nullable = false)
    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Basic
    @Column(name = "COMPANY_TO", nullable = false)
    public Integer getCompanyTo() {
        return companyTo;
    }

    public void setCompanyTo(Integer companyTo) {
        this.companyTo = companyTo;
    }

    @Basic
    @Column(name = "COMPANY_CONTACT_FROM", nullable = false)
    public Integer getCompanyContactFrom() {
        return companyContactFrom;
    }

    public void setCompanyContactFrom(Integer companyContactFrom) {
        this.companyContactFrom = companyContactFrom;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "FROM_DATE", nullable = false)
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "TO_DATE", nullable = false)
    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE", nullable = false)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "TITLE", nullable = false, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DUE_DATE", nullable = false)
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Basic
    @Column(name = "TAX_PERCENT", nullable = false, precision = 32767)
    public BigDecimal getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(BigDecimal taxPercent) {
        this.taxPercent = taxPercent;
    }

    @Basic
    @Column(name = "NOTE", nullable = true, length = 2000)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "PAID_DATE", nullable = true)
    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    @Basic
    @Column(name = "RATE", nullable = true, precision = 32767)
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "RATE_UNIT", nullable = true, length = 10)
    public String getRateUnit() {
        return rateUnit;
    }

    public void setRateUnit(String rateUnit) {
        this.rateUnit = rateUnit;
    }

    @Basic
    @Column(name = "CCY_ID", nullable = false)
    public Integer getCcyId() {
        return ccyId;
    }

    public void setCcyId(Integer ccyId) {
        this.ccyId = ccyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvoiceEntity that = (InvoiceEntity) o;

        if (invoiceId != null ? !invoiceId.equals(that.invoiceId) : that.invoiceId != null) return false;
        if (companyTo != null ? !companyTo.equals(that.companyTo) : that.companyTo != null) return false;
        if (companyContactFrom != null ? !companyContactFrom.equals(that.companyContactFrom) : that.companyContactFrom != null)
            return false;
        if (fromDate != null ? !fromDate.equals(that.fromDate) : that.fromDate != null) return false;
        if (toDate != null ? !toDate.equals(that.toDate) : that.toDate != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (dueDate != null ? !dueDate.equals(that.dueDate) : that.dueDate != null) return false;
        if (taxPercent != null ? !taxPercent.equals(that.taxPercent) : that.taxPercent != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (paidDate != null ? !paidDate.equals(that.paidDate) : that.paidDate != null) return false;
        if (rate != null ? !rate.equals(that.rate) : that.rate != null) return false;
        if (rateUnit != null ? !rateUnit.equals(that.rateUnit) : that.rateUnit != null) return false;
        if (ccyId != null ? !ccyId.equals(that.ccyId) : that.ccyId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = invoiceId != null ? invoiceId.hashCode() : 0;
        result = 31 * result + (companyTo != null ? companyTo.hashCode() : 0);
        result = 31 * result + (companyContactFrom != null ? companyContactFrom.hashCode() : 0);
        result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
        result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
        result = 31 * result + (taxPercent != null ? taxPercent.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (paidDate != null ? paidDate.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (rateUnit != null ? rateUnit.hashCode() : 0);
        result = 31 * result + (ccyId != null ? ccyId.hashCode() : 0);
        return result;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "invoiceByInvoiceId")
    public Collection<AttachmentEntity> getAttachmentsByInvoiceId() {
        return attachmentsByInvoiceId;
    }

    public void setAttachmentsByInvoiceId(Collection<AttachmentEntity> attachmentsByInvoiceId) {
        this.attachmentsByInvoiceId = attachmentsByInvoiceId;
    }

    @ManyToOne
    @JoinColumn(name = "COMPANY_TO", referencedColumnName = "COMPANY_ID", nullable = false, insertable = false, updatable = false)
    public CompaniesEntity getCompaniesByCompanyTo() {
        return companiesByCompanyTo;
    }

    public void setCompaniesByCompanyTo(CompaniesEntity companiesByCompanyTo) {
        this.companiesByCompanyTo = companiesByCompanyTo;
    }

    @ManyToOne
    @JoinColumn(name = "COMPANY_CONTACT_FROM", referencedColumnName = "COMPANY_CONTACT_ID", nullable = false, insertable = false, updatable = false)
    public CompanyContactEntity getCompanyContactByCompanyContactFrom() {
        return companyContactByCompanyContactFrom;
    }

    public void setCompanyContactByCompanyContactFrom(CompanyContactEntity companyContactByCompanyContactFrom) {
        this.companyContactByCompanyContactFrom = companyContactByCompanyContactFrom;
    }

    @ManyToOne
    @JoinColumn(name = "CCY_ID", referencedColumnName = "CCY_ID", nullable = false, insertable = false, updatable = false)
    public CurrencyEntity getCurrencyByCcyId() {
        return currencyByCcyId;
    }

    public void setCurrencyByCcyId(CurrencyEntity currencyByCcyId) {
        this.currencyByCcyId = currencyByCcyId;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "invoiceByInvoiceId")
    public Collection<InvoiceItemsEntity> getInvoiceItemsByInvoiceId() {
        return invoiceItemsByInvoiceId;
    }

    public void setInvoiceItemsByInvoiceId(Collection<InvoiceItemsEntity> invoiceItemsByInvoiceId) {
        this.invoiceItemsByInvoiceId = invoiceItemsByInvoiceId;
    }


    private BigDecimal totalValue;

    @Formula("(select sum(e.quantity * case when e.unit = 'HOUR' then rate else 1 end) from invoice.invoice_items e where e.invoice_id = invoice_id)")
    public BigDecimal getTotalValue() {
        return totalValue == null ? new BigDecimal(0) : totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    private BigDecimal totalValueWithTax;

    @Formula("(select sum(e.quantity * case when e.unit = 'HOUR' then rate else 1 end) * (tax_percent + 1) from invoice.invoice_items e where e.invoice_id = invoice_id)")
    public BigDecimal getTotalValueWithTax() {
        return totalValueWithTax == null ? new BigDecimal(0) : totalValueWithTax;

    }

    public void setTotalValueWithTax(BigDecimal totalValueWithTax) {
        this.totalValueWithTax = totalValueWithTax;
    }

    //    @Transient
//    public BigDecimal getTotalValue() {
//        return getInvoiceItemsByInvoiceId().stream().map(InvoiceItemsEntity::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    @Transient
//    public BigDecimal getTotalValueWithTax() {
//        return getInvoiceItemsByInvoiceId().stream().map(i -> i.getTotal()
//                .multiply(i.getInvoiceByInvoiceId().getTaxPercent()
//                        .add(new BigDecimal(1.0))))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }

    @Override
    public String toString() {
        return "InvoiceEntity{" +
                "invoiceId=" + invoiceId +
                ", companyTo=" + companyTo +
                ", companyContactFrom=" + companyContactFrom +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", createdDate=" + createdDate +
                ", title='" + title + '\'' +
                ", dueDate=" + dueDate +
                ", taxPercent=" + taxPercent +
                ", note='" + note + '\'' +
                ", paidDate=" + paidDate +
                ", rate=" + rate +
                ", rateUnit='" + rateUnit + '\'' +
                ", ccyId=" + ccyId +
                '}';
    }
}
