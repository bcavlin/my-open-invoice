package com.bgh.myopeninvoice.db.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Data
@Entity
@Table(name = "INVOICE_ITEMS", schema = "INVOICE")
@ToString(exclude = {"invoiceByInvoiceId"})
public class InvoiceItemsEntity implements java.io.Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "INVOICE_ITEM_ID", nullable = false)
  private Integer invoiceItemId;

  @Basic
  @Column(name = "INVOICE_ID", nullable = false)
  private Integer invoiceId;

  @Basic
  @Column(name = "DESCRIPTION", nullable = false)
  private String description;

  @Basic
  @Column(name = "CODE")
  private String code;

  @Basic
  @Column(name = "QUANTITY", nullable = false)
  private BigDecimal quantity;

  @Basic
  @Column(name = "UNIT", nullable = false, length = 20)
  private String unit;

  @ManyToOne
  @JoinColumn(
      name = "INVOICE_ID",
      referencedColumnName = "INVOICE_ID",
      nullable = false,
      insertable = false,
      updatable = false)
  private InvoiceEntity invoiceByInvoiceId;

  @LazyCollection(LazyCollectionOption.FALSE)
  @OrderBy("itemDate ASC")
  @OneToMany(
      mappedBy = "invoiceItemsByInvoiceItemId",
      cascade = CascadeType.ALL)
  private Collection<TimesheetEntity> timesheetsByInvoiceItemId;

  @Transient
  @Setter(AccessLevel.NONE)
  private BigDecimal timesheetTotal;

  @Transient
  @Setter(AccessLevel.NONE)
  private Long timesheetTotalDays;

  // this did not wor as we need to update total hours upfront - new entries cannot be calcualted
  // until they are in the database
  //    @Formula("(select sum(e.hours_worked) from invoice.time_sheet e where e.invoice_item_id =
  // invoice_item_id)")

  public BigDecimal getTimesheetTotal() {
    if (getTimesheetsByInvoiceItemId() != null) {
      timesheetTotal =
          getTimesheetsByInvoiceItemId().stream()
              .filter(o -> o.getHoursWorked() != null)
              .map(TimesheetEntity::getHoursWorked)
              .reduce(BigDecimal.ZERO, BigDecimal::add);
    } else {
      timesheetTotal = new BigDecimal(0);
    }
    return timesheetTotal == null ? new BigDecimal(0) : timesheetTotal;
  }

  public Long getTimesheetTotalDays() {
    if (getTimesheetsByInvoiceItemId() != null) {
      timesheetTotalDays =
          getTimesheetsByInvoiceItemId().stream().filter(o -> o.getHoursWorked() != null).count();
    } else {
      timesheetTotalDays = 0L;
    }
    return timesheetTotalDays;
  }

  @Transient
  public Integer getTimesheetsByInvoiceItemIdSize() {
    return Optional.ofNullable(timesheetsByInvoiceItemId).orElse(Collections.emptyList()).size();
  }

}
