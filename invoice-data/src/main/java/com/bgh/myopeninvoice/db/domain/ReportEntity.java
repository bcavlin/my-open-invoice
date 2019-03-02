package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "REPORTS", schema = "INVOICE")
@ToString(exclude = {"invoiceByInvoiceId"})
public class ReportEntity implements java.io.Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "REPORT_ID", nullable = false)
  private Integer reportId;

  @Basic
  @Column(name = "INVOICE_ID", nullable = false)
  private Integer invoiceId;

  @Column(name = "DATE_CREATED", nullable = false)
  private ZonedDateTime dateCreated;

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

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "CONTENT_ID", referencedColumnName = "CONTENT_ID")
  private ContentEntity contentByContentId;
}
