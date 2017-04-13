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

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

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
    private BigDecimal total;
    private InvoiceEntity invoiceByInvoiceId;
    private Collection<TimeSheetEntity> timeSheetsByInvoiceItemId;

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
    @Column(name = "TOTAL", nullable = false, precision = 32767)
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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
        if (total != null ? !total.equals(that.total) : that.total != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = invoiceItemId != null ? invoiceItemId.hashCode() : 0;
        result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID", referencedColumnName = "INVOICE_ID", nullable = false, insertable = false, updatable = false)
    public InvoiceEntity getInvoiceByInvoiceId() {
        return invoiceByInvoiceId;
    }

    public void setInvoiceByInvoiceId(InvoiceEntity invoiceByInvoiceId) {
        this.invoiceByInvoiceId = invoiceByInvoiceId;
    }

    @OneToMany(mappedBy = "invoiceItemsByInvoiceItemId")
    public Collection<TimeSheetEntity> getTimeSheetsByInvoiceItemId() {
        return timeSheetsByInvoiceItemId;
    }

    public void setTimeSheetsByInvoiceItemId(Collection<TimeSheetEntity> timeSheetsByInvoiceItemId) {
        this.timeSheetsByInvoiceItemId = timeSheetsByInvoiceItemId;
    }

    @Override
    public String toString() {
        return "InvoiceItemsEntity{" +
                "invoiceItemId=" + invoiceItemId +
                ", invoiceId=" + invoiceId +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", total=" + total +
                '}';
    }
}
