package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
