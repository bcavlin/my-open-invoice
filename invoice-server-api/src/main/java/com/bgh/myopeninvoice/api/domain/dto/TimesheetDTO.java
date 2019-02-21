package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class TimesheetDTO implements java.io.Serializable {

  private Integer timesheetId;

  @NotNull
  private Integer invoiceItemId;

  @NotNull
  private LocalDate itemDate;

  @NotNull private BigDecimal hoursWorked;

  private Boolean weekend;
}
