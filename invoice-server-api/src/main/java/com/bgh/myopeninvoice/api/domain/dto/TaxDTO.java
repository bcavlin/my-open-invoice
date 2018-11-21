package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TaxDTO implements Serializable {

    private Integer taxId;

    @DecimalMin("0.0")
    @DecimalMax("1.0")
    @NotNull
    private BigDecimal percent;

    @NotNull
    private String identifier;

}
