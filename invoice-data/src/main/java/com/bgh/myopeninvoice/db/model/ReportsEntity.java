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
import java.util.Arrays;
import java.util.Date;

/**
 * Created by bcavlin on 30/04/17.
 */
@Entity
@Table(name = "REPORTS", schema = "INVOICE", catalog = "INVOICEDB")
public class ReportsEntity implements Serializable{
    private Integer reportId;
    private byte[] content;
    private Date dateCreated;
    private InvoiceEntity invoiceByInvoiceId;

    @Id
    @GeneratedValue
    @Column(name = "REPORT_ID", nullable = false)
    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    @Lob
    @Column(name = "CONTENT", nullable = false)
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_CREATED", nullable = false)
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportsEntity that = (ReportsEntity) o;

        if (reportId != null ? !reportId.equals(that.reportId) : that.reportId != null) return false;
        if (!Arrays.equals(content, that.content)) return false;
        if (dateCreated != null ? !dateCreated.equals(that.dateCreated) : that.dateCreated != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reportId != null ? reportId.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(content);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID", referencedColumnName = "INVOICE_ID", nullable = false)
    public InvoiceEntity getInvoiceByInvoiceId() {
        return invoiceByInvoiceId;
    }

    public void setInvoiceByInvoiceId(InvoiceEntity invoiceByInvoiceId) {
        this.invoiceByInvoiceId = invoiceByInvoiceId;
    }

    @Override
    public String toString() {
        return "ReportsEntity{" +
                "reportId=" + reportId +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
