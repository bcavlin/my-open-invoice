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

import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bcavlin on 14/03/17.
 */
@Data
@Entity
@Table(name = "ATTACHMENT", schema = "INVOICE", catalog = "INVOICEDB")
public class AttachmentEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ATTACHMENT_ID", nullable = false)
    private Integer attachmentId;

    @Basic
    @Column(name = "INVOICE_ID", nullable = false)
    private Integer invoiceId;

    @Lob
    @Column(name = "CONTENT", nullable = false)
    private byte[] content;

    @Basic
    @Column(name = "FILENAME", nullable = false, length = 255)
    private String filename;

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID", referencedColumnName = "INVOICE_ID", nullable = false, insertable = false, updatable = false)
    private InvoiceEntity invoiceByInvoiceId;

    @Transient
    private byte[] imageData;

    @Transient
    private Boolean loadProxy;

    @Transient
    private String fileExtension;

    public Boolean getLoadProxy() {
        if (loadProxy == null) {
            loadProxy = Boolean.TRUE;
        }
        return loadProxy;
    }

    public String getFileExtension() {
        if (!StringUtils.isBlank(filename)) {
            this.fileExtension = FilenameUtils.getExtension(filename).toLowerCase();
        }
        return fileExtension;
    }

}
