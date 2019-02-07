package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CurrencyDTO implements java.io.Serializable {

  private Integer ccyId;

  @NotNull
  private String name;

  @NotNull
  private String description;
}
