package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TaxDTO implements Serializable {

    private Integer taxId;

    private BigDecimal percent;

    private String identifier;

}
