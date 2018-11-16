package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Data
public class TimeSheetDTO implements java.io.Serializable {

    private Integer timesheetId;

    private Integer invoiceItemId;

    private Date itemDate;

    private BigDecimal hoursWorked;

    private Boolean isWeekend;

}
