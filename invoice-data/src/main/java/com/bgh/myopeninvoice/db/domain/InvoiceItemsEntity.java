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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by bcavlin on 14/03/17.
 */
@Data
@Entity
@Table(name = "INVOICE_ITEMS", schema = "INVOICE")
public class InvoiceItemsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_ITEM_ID")
    private Integer invoiceItemId;

    @Basic
    @Column(name = "INVOICE_ID", nullable = false)
    private Integer invoiceId;

    @Basic
    @Column(name = "DESCRIPTION", nullable = false, length = 255)
    private String description;

    @Basic
    @Column(name = "CODE", nullable = true, length = 255)
    private String code;

    @Basic
    @Column(name = "QUANTITY", nullable = false, precision = 32767)
    private BigDecimal quantity;

    @Basic
    @Column(name = "UNIT", nullable = false, length = 20)
    private String unit;

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID", referencedColumnName = "INVOICE_ID", nullable = false,
            insertable = false, updatable = false)
    private InvoiceEntity invoiceByInvoiceId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "invoiceItemsByInvoiceItemId", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("itemDate ASC")
    private Collection<TimeSheetEntity> timeSheetsByInvoiceItemId;


    @Transient
    private BigDecimal timeSheetTotal;

    @Transient
    private Long timeSheetTotalDays;

    //this did not wor as we need to update total hours upfront - new entries cannot be calcualted until they are in the database
//    @Formula("(select sum(e.hours_worked) from invoice.time_sheet e where e.invoice_item_id = invoice_item_id)")

    public BigDecimal getTimeSheetTotal() {
        if (getTimeSheetsByInvoiceItemId() != null) {
            timeSheetTotal = getTimeSheetsByInvoiceItemId().stream()
                    .filter(o -> o.getHoursWorked() != null).map(TimeSheetEntity::getHoursWorked)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            timeSheetTotal = new BigDecimal(0);
        }
        return timeSheetTotal == null ? new BigDecimal(0) : timeSheetTotal;
    }

    public Long getTimeSheetTotalDays() {
        if (getTimeSheetsByInvoiceItemId() != null) {
            timeSheetTotalDays = getTimeSheetsByInvoiceItemId().stream()
                    .filter(o -> o.getHoursWorked() != null).count();
        } else {
            timeSheetTotalDays = 0L;
        }
        return timeSheetTotalDays;
    }

}
