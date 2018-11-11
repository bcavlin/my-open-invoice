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

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by bcavlin on 30/04/17.
 */
@Data
@Entity
@Table(name = "REPORTS", schema = "INVOICE")
public class ReportsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Integer reportId;

    @Lob
    @Column(name = "CONTENT", nullable = false)
    private byte[] content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATED", nullable = false)
    private Date dateCreated;

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID", referencedColumnName = "INVOICE_ID", nullable = false)
    private InvoiceEntity invoiceByInvoiceId;

    @Basic
    @Column(name = "REPORT_NAME")
    private String reportName;

    @Transient
    private Boolean loadProxy;

    public Boolean getLoadProxy() {
        if (loadProxy == null) {
            loadProxy = Boolean.TRUE;
        }
        return loadProxy;
    }

}
