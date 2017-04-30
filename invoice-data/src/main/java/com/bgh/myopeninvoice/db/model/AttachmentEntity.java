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

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by bcavlin on 14/03/17.
 */
@Entity
@Table(name = "ATTACHMENT", schema = "INVOICE", catalog = "INVOICEDB")
public class AttachmentEntity implements Serializable {
    private Integer attachmentId;
    private Integer invoiceId;
    private byte[] content;
    private String filename;
    private InvoiceEntity invoiceByInvoiceId;

    @Id
    @GeneratedValue
    @Column(name = "ATTACHMENT_ID", nullable = false)
    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    @Basic
    @Column(name = "INVOICE_ID", nullable = false)
    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Lob
    @Column(name = "CONTENT", nullable = false)
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Basic
    @Column(name = "FILENAME", nullable = false, length = 255)
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttachmentEntity that = (AttachmentEntity) o;

        if (attachmentId != null ? !attachmentId.equals(that.attachmentId) : that.attachmentId != null) return false;
        if (invoiceId != null ? !invoiceId.equals(that.invoiceId) : that.invoiceId != null) return false;
        if (!Arrays.equals(content, that.content)) return false;
        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = attachmentId != null ? attachmentId.hashCode() : 0;
        result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(content);
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
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

    private Boolean loadProxy;

    @Transient
    public Boolean getLoadProxy() {
        if (loadProxy == null) {
            loadProxy = Boolean.TRUE;
        }
        return loadProxy;
    }

    public void setLoadProxy(Boolean loadProxy) {
        this.loadProxy = loadProxy;
    }

    private String fileExtension;

    @Transient
    public String getFileExtension() {
        if (!StringUtils.isBlank(filename)) {
            this.fileExtension = FilenameUtils.getExtension(filename).toLowerCase();
        }
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public String toString() {
        return "AttachmentEntity{" +
                "attachmentId=" + attachmentId +
                ", invoiceId=" + invoiceId +
                ", filename='" + filename + '\'' +
                ", loadProxy=" + loadProxy +
                ", fileExtension='" + fileExtension + '\'' +
                '}';
    }
}
