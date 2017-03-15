package com.bgh.myopeninvoice.db.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by bcavlin on 14/03/17.
 */
@Entity
@Table(name = "INVOICE", schema = "INVOICE", catalog = "INVOICEDB")
public class InvoiceEntity {
    private Integer invoiceId;
    private Integer companyFrom;
    private Integer companyTo;
    private Integer rate;
    private Date fromDate;
    private Date toDate;
    private Date createdDate;
    private String title;
    private Date dueDate;
    private Integer taxId;
    private String note;
    private Date paidDate;

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
    @Column(name = "COMPANY_FROM", nullable = false)
    public Integer getCompanyFrom() {
        return companyFrom;
    }

    public void setCompanyFrom(Integer companyFrom) {
        this.companyFrom = companyFrom;
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
    @Column(name = "RATE", nullable = false)
    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
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
    @Column(name = "TAX_ID", nullable = false)
    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvoiceEntity that = (InvoiceEntity) o;

        if (invoiceId != null ? !invoiceId.equals(that.invoiceId) : that.invoiceId != null) return false;
        if (companyFrom != null ? !companyFrom.equals(that.companyFrom) : that.companyFrom != null) return false;
        if (companyTo != null ? !companyTo.equals(that.companyTo) : that.companyTo != null) return false;
        if (rate != null ? !rate.equals(that.rate) : that.rate != null) return false;
        if (fromDate != null ? !fromDate.equals(that.fromDate) : that.fromDate != null) return false;
        if (toDate != null ? !toDate.equals(that.toDate) : that.toDate != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (dueDate != null ? !dueDate.equals(that.dueDate) : that.dueDate != null) return false;
        if (taxId != null ? !taxId.equals(that.taxId) : that.taxId != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (paidDate != null ? !paidDate.equals(that.paidDate) : that.paidDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = invoiceId != null ? invoiceId.hashCode() : 0;
        result = 31 * result + (companyFrom != null ? companyFrom.hashCode() : 0);
        result = 31 * result + (companyTo != null ? companyTo.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
        result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
        result = 31 * result + (taxId != null ? taxId.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (paidDate != null ? paidDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InvoiceEntity{" +
                "invoiceId=" + invoiceId +
                ", companyFrom=" + companyFrom +
                ", companyTo=" + companyTo +
                ", rate=" + rate +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", createdDate=" + createdDate +
                ", title='" + title + '\'' +
                ", dueDate=" + dueDate +
                ", taxId=" + taxId +
                ", note='" + note + '\'' +
                ", paidDate=" + paidDate +
                '}';
    }
}
