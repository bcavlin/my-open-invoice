package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceItemsDTO implements java.io.Serializable {

    private Integer invoiceItemId;

    private Integer invoiceId;

    private String description;

    private String code;

    private BigDecimal quantity;

    private String unit;

    private BigDecimal timeSheetTotal;

    private Long timeSheetTotalDays;

}
