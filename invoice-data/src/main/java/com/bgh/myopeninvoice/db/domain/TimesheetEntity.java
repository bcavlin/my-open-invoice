package com.bgh.myopeninvoice.db.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;

@Data
@Entity
@Table(name = "TIME_SHEET", schema = "INVOICE")
@ToString(exclude = {"invoiceItemsByInvoiceItemId"})
public class TimesheetEntity implements java.io.Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TIMESHEET_ID", nullable = false)
  private Integer timesheetId;

  @Basic
  @Column(name = "INVOICE_ITEM_ID", nullable = false)
  private Integer invoiceItemId;

  @Column(name = "ITEM_DATE", nullable = false)
  private LocalDate itemDate;

  @Basic
  @Column(name = "HOURS_WORKED", nullable = false, precision = 32767)
  private BigDecimal hoursWorked;

  @ManyToOne
  @JoinColumn(
      name = "INVOICE_ITEM_ID",
      referencedColumnName = "INVOICE_ITEM_ID",
      nullable = false,
      insertable = false,
      updatable = false)
  private InvoiceItemsEntity invoiceItemsByInvoiceItemId;

  @Transient
  public Boolean getWeekend() {
    if (itemDate != null) {
      return itemDate.getDayOfWeek() == DayOfWeek.SATURDAY || itemDate.getDayOfWeek() == DayOfWeek.SUNDAY
              ? Boolean.TRUE
              : Boolean.FALSE;
    }
    return Boolean.FALSE;
  }
}
