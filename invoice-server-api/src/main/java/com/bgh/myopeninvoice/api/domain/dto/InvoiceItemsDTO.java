package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class InvoiceItemsDTO implements java.io.Serializable {

  private Integer invoiceItemId;

  @NotNull private Integer invoiceId;

  @NotNull private String description;

  private String code;

  @NotNull private BigDecimal quantity;

  @NotNull private String unit;

  private BigDecimal timesheetTotal;

  private Long timesheetTotalDays;

  @JsonIgnoreProperties({"invoiceItems"})
  private Collection<TimesheetDTO> timesheets;
}
