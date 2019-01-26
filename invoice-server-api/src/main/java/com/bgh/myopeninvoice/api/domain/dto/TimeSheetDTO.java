package com.bgh.myopeninvoice.api.domain.dto;

import com.bgh.myopeninvoice.api.domain.CustomJsonDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TimeSheetDTO implements java.io.Serializable {

  private Integer timesheetId;

  @NotNull private Integer invoiceItemId;

  @NotNull
  @JsonDeserialize(using = CustomJsonDateTimeDeserializer.class)
  private Date itemDate;

  @NotNull private BigDecimal hoursWorked;

  private Boolean isWeekend;

  @JsonIgnoreProperties({"timeSheets"})
  private InvoiceItemsDTO invoiceItem;
}
