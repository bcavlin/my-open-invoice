package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CurrencyDTO implements java.io.Serializable {

  private Integer ccyId;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private BigDecimal rateToCAD;

    private ZonedDateTime updatedAt;
}
