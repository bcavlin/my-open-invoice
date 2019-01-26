package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "INVOICE", schema = "INVOICE")
public class InvoiceEntity implements java.io.Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "INVOICE_ID", nullable = false)
  private Integer invoiceId;

  @Basic
  @Column(name = "COMPANY_CONTACT_FROM", nullable = false)
  private Integer companyContactFrom;

  @Basic
  @Column(name = "COMPANY_CONTRACT_TO", nullable = false)
  private Integer companyContractTo;

  @Temporal(TemporalType.DATE)
  @Column(name = "FROM_DATE", nullable = false)
  private Date fromDate;

  @Temporal(TemporalType.DATE)
  @Column(name = "TO_DATE", nullable = false)
  private Date toDate;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED_DATE", nullable = false)
  private Date createdDate;

  @Basic
  @Column(name = "TITLE", nullable = false)
  private String title;

  @Temporal(TemporalType.DATE)
  @Column(name = "DUE_DATE", nullable = false)
  private Date dueDate;

  @Basic
  @Column(name = "TAX_PERCENT", nullable = false, precision = 32767)
  private BigDecimal taxPercent;

  @Basic
  @Column(name = "NOTE", length = 2000)
  private String note;

  @Temporal(TemporalType.DATE)
  @Column(name = "PAID_DATE")
  private Date paidDate;

  @Basic
  @Column(name = "RATE", precision = 32767)
  private BigDecimal rate;

  @Basic
  @Column(name = "RATE_UNIT", length = 10)
  private String rateUnit;

  @Basic
  @Column(name = "CCY_ID", nullable = false)
  private Integer ccyId;

  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "invoiceByInvoiceId")
  private Collection<AttachmentEntity> attachmentsByInvoiceId;

  @ManyToOne
  @JoinColumn(
      name = "COMPANY_CONTACT_FROM",
      referencedColumnName = "COMPANY_CONTACT_ID",
      nullable = false,
      insertable = false,
      updatable = false)
  private CompanyContactEntity companyContactByCompanyContactFrom;

  @ManyToOne
  @JoinColumn(
      name = "CCY_ID",
      referencedColumnName = "CCY_ID",
      nullable = false,
      insertable = false,
      updatable = false)
  private CurrencyEntity currencyByCcyId;

  @ManyToOne
  @JoinColumn(
      name = "COMPANY_CONTRACT_TO",
      referencedColumnName = "CONTRACT_ID",
      nullable = false,
      insertable = false,
      updatable = false)
  private ContractEntity contractByCompanyContractTo;

  @OneToMany(mappedBy = "invoiceByInvoiceId")
  private Collection<InvoiceItemsEntity> invoiceItemsByInvoiceId;

  @OneToMany(mappedBy = "invoiceByInvoiceId")
  private Collection<ReportsEntity> reportsByInvoiceId;

  @Formula(
      "(select sum(e.quantity * case when e.unit = 'HOUR' then rate else 1 end) from invoice.invoice_items e where e.invoice_id = invoice_id)")
  private BigDecimal totalValue;

  @Formula(
      "(select sum(e.quantity * case when e.unit = 'HOUR' then rate else 1 end) * (tax_percent + 1) from invoice.invoice_items e where e.invoice_id = invoice_id)")
  private BigDecimal totalValueWithTax;

  public BigDecimal getTotalValue() {
    return totalValue == null ? BigDecimal.valueOf(0) : totalValue;
  }

  public BigDecimal getTotalValueWithTax() {
    return totalValueWithTax == null ? BigDecimal.valueOf(0) : totalValueWithTax;
  }
}
