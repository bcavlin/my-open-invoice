package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Data
@Entity
@Table(name = "INVOICE_ITEMS", schema = "INVOICE")
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
    @JoinColumn(name = "INVOICE_ID", referencedColumnName = "INVOICE_ID", nullable = false,
            insertable = false, updatable = false)
    private InvoiceEntity invoiceByInvoiceId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OrderBy("itemDate ASC")
    @OneToMany(mappedBy = "invoiceItemsByInvoiceItemId", orphanRemoval = true, cascade = CascadeType.ALL)
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
