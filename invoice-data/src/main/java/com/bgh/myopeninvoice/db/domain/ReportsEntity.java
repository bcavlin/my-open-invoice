package com.bgh.myopeninvoice.db.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "REPORTS", schema = "INVOICE")
public class ReportsEntity implements java.io.Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "REPORT_ID", nullable = false)
  private Integer reportId;

  @Basic
  @Column(name = "INVOICE_ID", nullable = false)
  private Integer invoiceId;

  @Basic
  @Column(name = "CONTENT_ID", nullable = false)
  private Integer contentId;

  @Column(name = "DATE_CREATED", nullable = false)
  private LocalDateTime dateCreated;

  @Basic
  @Column(name = "REPORT_NAME", nullable = false, length = 255)
  private String reportName;

  @ManyToOne
  @JoinColumn(
      name = "INVOICE_ID",
      referencedColumnName = "INVOICE_ID",
      nullable = false,
      insertable = false,
      updatable = false)
  private InvoiceEntity invoiceByInvoiceId;

  @ManyToOne
  @JoinColumn(
      name = "CONTENT_ID",
      referencedColumnName = "CONTENT_ID",
      nullable = false,
      insertable = false,
      updatable = false)
  private ContentEntity contentByContentId;
}
