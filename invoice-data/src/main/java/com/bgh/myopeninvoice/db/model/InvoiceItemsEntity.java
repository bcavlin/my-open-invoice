package com.bgh.myopeninvoice.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by bcavlin on 14/03/17.
 */
@Entity
@Table(name = "INVOICE_ITEMS", schema = "INVOICE", catalog = "INVOICEDB")
public class InvoiceItemsEntity implements Serializable {
    private Integer invoiceItemId;
    private Integer invoiceId;
    private String description;
    private String code;
    private BigDecimal hoursTotal;
    private BigDecimal value;

    @Id
    @GeneratedValue
    @Column(name = "INVOICE_ITEM_ID", nullable = false)
    public Integer getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(Integer invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    @Basic
    @Column(name = "INVOICE_ID", nullable = false)
    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Basic
    @Column(name = "DESCRIPTION", nullable = false, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "CODE", nullable = true, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "HOURS_TOTAL", nullable = true, precision = 32767)
    public BigDecimal getHoursTotal() {
        return hoursTotal;
    }

    public void setHoursTotal(BigDecimal hoursTotal) {
        this.hoursTotal = hoursTotal;
    }

    @Basic
    @Column(name = "VALUE", nullable = false, precision = 32767)
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvoiceItemsEntity that = (InvoiceItemsEntity) o;

        if (invoiceItemId != null ? !invoiceItemId.equals(that.invoiceItemId) : that.invoiceItemId != null)
            return false;
        if (invoiceId != null ? !invoiceId.equals(that.invoiceId) : that.invoiceId != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (hoursTotal != null ? !hoursTotal.equals(that.hoursTotal) : that.hoursTotal != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = invoiceItemId != null ? invoiceItemId.hashCode() : 0;
        result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (hoursTotal != null ? hoursTotal.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InvoiceItemsEntity{" +
                "invoiceItemId=" + invoiceItemId +
                ", invoiceId=" + invoiceId +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", hoursTotal=" + hoursTotal +
                ", value=" + value +
                '}';
    }
}
