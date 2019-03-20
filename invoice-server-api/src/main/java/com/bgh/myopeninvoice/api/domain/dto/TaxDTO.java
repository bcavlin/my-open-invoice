package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TaxDTO implements Serializable {

  private Integer taxId;

  @DecimalMin("0.0")
  @DecimalMax("1.0")
  @NotNull
  private BigDecimal percent;

  @NotNull private String identifier;
}
