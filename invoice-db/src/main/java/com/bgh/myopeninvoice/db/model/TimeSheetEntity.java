package com.bgh.myopeninvoice.db.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by bcavlin on 14/03/17.
 */
@Entity
@Table(name = "TIME_SHEET", schema = "INVOICE", catalog = "INVOICEDB")
public class TimeSheetEntity {
    private Integer timesheetId;
    private Integer invoiceItemId;
    private Date itemDate;
    private BigDecimal hoursWorked;

    @Id
    @GeneratedValue
    @Column(name = "TIMESHEET_ID", nullable = false)
    public Integer getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(Integer timesheetId) {
        this.timesheetId = timesheetId;
    }

    @Basic
    @Column(name = "INVOICE_ITEM_ID", nullable = false)
    public Integer getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(Integer invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "ITEM_DATE", nullable = false)
    public Date getItemDate() {
        return itemDate;
    }

    public void setItemDate(Date itemDate) {
        this.itemDate = itemDate;
    }

    @Basic
    @Column(name = "HOURS_WORKED", nullable = false, precision = 32767)
    public BigDecimal getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(BigDecimal hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeSheetEntity that = (TimeSheetEntity) o;

        if (timesheetId != null ? !timesheetId.equals(that.timesheetId) : that.timesheetId != null) return false;
        if (invoiceItemId != null ? !invoiceItemId.equals(that.invoiceItemId) : that.invoiceItemId != null)
            return false;
        if (itemDate != null ? !itemDate.equals(that.itemDate) : that.itemDate != null) return false;
        if (hoursWorked != null ? !hoursWorked.equals(that.hoursWorked) : that.hoursWorked != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = timesheetId != null ? timesheetId.hashCode() : 0;
        result = 31 * result + (invoiceItemId != null ? invoiceItemId.hashCode() : 0);
        result = 31 * result + (itemDate != null ? itemDate.hashCode() : 0);
        result = 31 * result + (hoursWorked != null ? hoursWorked.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TimeSheetEntity{" +
                "timesheetId=" + timesheetId +
                ", invoiceItemId=" + invoiceItemId +
                ", itemDate=" + itemDate +
                ", hoursWorked=" + hoursWorked +
                '}';
    }
}
