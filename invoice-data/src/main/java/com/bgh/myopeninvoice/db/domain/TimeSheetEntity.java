package com.bgh.myopeninvoice.db.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Data
@Entity
@Table(name = "TIME_SHEET", schema = "INVOICE")
public class TimeSheetEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIMESHEET_ID", nullable = false)
    private Integer timesheetId;

    @Basic
    @Column(name = "INVOICE_ITEM_ID", nullable = false)
    private Integer invoiceItemId;

    @Temporal(TemporalType.DATE)
    @Column(name = "ITEM_DATE", nullable = false)
    private Date itemDate;

    @Basic
    @Column(name = "HOURS_WORKED", nullable = false, precision = 32767)
    private BigDecimal hoursWorked;

    @ManyToOne
    @JoinColumn(name = "INVOICE_ITEM_ID", referencedColumnName = "INVOICE_ITEM_ID", nullable = false,
            insertable = false, updatable = false)
    private InvoiceItemsEntity invoiceItemsByInvoiceItemId;

    @Transient
    private Boolean isWeekend;

    public Boolean getWeekend() {
        if (isWeekend == null && itemDate != null) {
            LocalDate date = itemDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            isWeekend = date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    date.getDayOfWeek() == DayOfWeek.SUNDAY ? Boolean.TRUE : Boolean.FALSE;
        }
        return isWeekend;
    }

}
