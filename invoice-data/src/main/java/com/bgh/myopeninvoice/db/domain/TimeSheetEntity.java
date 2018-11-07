/*
 * Copyright 2017 Branislav Cavlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bgh.myopeninvoice.db.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by bcavlin on 14/03/17.
 */
@Data
@Entity
@Table(name = "TIME_SHEET", schema = "INVOICE")
public class TimeSheetEntity implements Serializable {

    @Id
    @GeneratedValue
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
