package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

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

  @Column(name = "FROM_DATE", nullable = false)
  private LocalDate fromDate;

  @Column(name = "TO_DATE", nullable = false)
  private LocalDate toDate;

    @Column(name = "CREATED_AT", nullable = false)
    private ZonedDateTime createdAt;

  @Basic
  @Column(name = "TITLE", nullable = false)
  private String title;

  @Column(name = "DUE_DATE", nullable = false)
  private LocalDate dueDate;

  @Basic
  @Column(name = "TAX_PERCENT", nullable = false, precision = 32767)
  private BigDecimal taxPercent;

  @Basic
  @Column(name = "NOTE", length = 2000)
  private String note;

  @Column(name = "PAID_DATE")
  private LocalDate paidDate;

  @Basic
  @Column(name = "RATE", precision = 32767)
  private BigDecimal rate;

  @Basic
  @Column(name = "RATE_UNIT", length = 10)
  private String rateUnit;

  @Basic
  @Column(name = "CCY_ID", nullable = false)
  private Integer ccyId;

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

  @LazyCollection(LazyCollectionOption.TRUE)
  @OneToMany(mappedBy = "invoiceByInvoiceId")
  private Collection<ReportEntity> reportsByInvoiceId;

  public BigDecimal getTotalValue() {
    BigDecimal totalQuantity = null;

    if (CollectionUtils.isNotEmpty(invoiceItemsByInvoiceId)) {
      Function<InvoiceItemsEntity, BigDecimal> totalMapper =
          (ii) ->
              ii.getQuantity().compareTo(BigDecimal.valueOf(0)) != 0
                  ? ii.getQuantity()
                  : (ii.getTimesheetsByInvoiceItemId().stream()
                      .map(TimesheetEntity::getHoursWorked)
                      .reduce(BigDecimal.ZERO, BigDecimal::add));

      totalQuantity =
          invoiceItemsByInvoiceId.stream()
              .map(totalMapper)
              .reduce(BigDecimal.ZERO, BigDecimal::add);

      if (!getRateUnit().equalsIgnoreCase("TOTAL")) {
        totalQuantity = totalQuantity.multiply(getRate());
      }
    }

    return totalQuantity == null ? BigDecimal.valueOf(0) : totalQuantity;
  }

  public BigDecimal getTotalValueWithTax() {
    return getTotalValue().multiply(taxPercent.add(BigDecimal.valueOf(1)));
  }

  @Transient
  public Integer getReportsByInvoiceIdSize() {
    return Optional.ofNullable(reportsByInvoiceId).orElse(Collections.emptyList()).size();
  }

  @Transient
  public Integer getInvoiceItemsByInvoiceIdSize() {
    return Optional.ofNullable(invoiceItemsByInvoiceId).orElse(Collections.emptyList()).size();
  }

  @Transient
  public Integer getAttachmentsByInvoiceIdSize() {
    return Optional.ofNullable(attachmentsByInvoiceId).orElse(Collections.emptyList()).size();
  }

  @Transient
  public LocalDate getFromDateAdjusted() {
    if (this.getContractByCompanyContractTo() != null) {
      int weekStart = this.getContractByCompanyContractTo().getCompanyByCompanyId().getWeekStart();
      LocalDate d = fromDate;

      if (d.getDayOfWeek().getValue() - weekStart < 0) {
        return d.minusWeeks(1).with(ChronoField.DAY_OF_WEEK, weekStart);
      } else {
        return d.with(ChronoField.DAY_OF_WEEK, weekStart);
      }
    }
    return null;
  }

  @Transient
  public LocalDate getToDateAdjusted() {
    if (this.getContractByCompanyContractTo() != null) {
      int weekStart = this.getContractByCompanyContractTo().getCompanyByCompanyId().getWeekStart();
      int weekEnd = weekStart == 1 ? 7 : weekStart;

      LocalDate d = toDate;

      if (d.getDayOfWeek().getValue() - weekEnd <= 0) {
        return d.with(ChronoField.DAY_OF_WEEK, weekEnd);
      } else {
        return d.plusWeeks(1).with(ChronoField.DAY_OF_WEEK, weekEnd);
      }
    }
    return null;
  }

  @Transient
  public Long getFromToDays() {
    if (getFromDateAdjusted() != null && getToDateAdjusted() != null) {
      long between = ChronoUnit.DAYS.between(getFromDateAdjusted(), getToDateAdjusted());
      return between % 7 != 0 ? between + 1 : between;
    } else {
      return null;
    }
  }
}
