package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ATTACHMENT", schema = "INVOICE")
@ToString(exclude = {"invoiceByInvoiceId"})
public class AttachmentEntity implements java.io.Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ATTACHMENT_ID", nullable = false)
  private Integer attachmentId;

  @Basic
  @Column(name = "INVOICE_ID", nullable = false)
  private Integer invoiceId;

  @ManyToOne
  @JoinColumn(
      name = "INVOICE_ID",
      referencedColumnName = "INVOICE_ID",
      nullable = false,
      insertable = false,
      updatable = false)
  private InvoiceEntity invoiceByInvoiceId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "CONTENT_ID", referencedColumnName = "CONTENT_ID")
  private ContentEntity contentByContentId;
}
