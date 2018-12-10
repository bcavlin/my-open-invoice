package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TimeSheetDTO implements java.io.Serializable {

    private Integer timesheetId;

    private Integer invoiceItemId;

    private Date itemDate;

    private BigDecimal hoursWorked;

    private Boolean isWeekend;

}
