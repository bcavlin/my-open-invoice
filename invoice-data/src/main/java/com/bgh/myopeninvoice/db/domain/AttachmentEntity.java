package com.bgh.myopeninvoice.db.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ATTACHMENT", schema = "INVOICE")
public class AttachmentEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTACHMENT_ID", nullable = false)
    private Integer attachmentId;

    @Basic
    @Column(name = "INVOICE_ID", nullable = false)
    private Integer invoiceId;

    @Basic
    @Column(name = "CONTENT_ID", nullable = false)
    private Integer contentId;

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID", referencedColumnName = "INVOICE_ID", nullable = false, insertable = false, updatable = false)
    private InvoiceEntity invoiceByInvoiceId;

    @ManyToOne
    @JoinColumn(name = "CONTENT_ID", referencedColumnName = "CONTENT_ID", nullable = false, insertable = false, updatable = false)
    private ContentEntity contentByContentId;

}
