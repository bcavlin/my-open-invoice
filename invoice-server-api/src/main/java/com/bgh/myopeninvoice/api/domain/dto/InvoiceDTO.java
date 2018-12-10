package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class InvoiceDTO implements java.io.Serializable {

    private Integer invoiceId;

    private Integer companyTo;

    private Integer companyContactFrom;

    private Date fromDate;

    private Date toDate;

    private Date createdDate;

    private String title;

    private Date dueDate;

    private BigDecimal taxPercent;

    private String note;

    private Date paidDate;

    private BigDecimal rate;

    private String rateUnit;

    private Integer ccyId;

    private Integer companyContractTo;

    private BigDecimal totalValue;

    private BigDecimal totalValueWithTax;

}
