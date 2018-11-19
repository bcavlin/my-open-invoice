package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TimeSheetDTO implements java.io.Serializable {

    private Integer timesheetId;

    private Integer invoiceItemId;

    private Date itemDate;

    private BigDecimal hoursWorked;

    private Boolean isWeekend;

}
