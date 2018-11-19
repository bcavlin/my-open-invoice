package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

@Data
public class CurrencyDTO implements java.io.Serializable {

    private Integer ccyId;

    private String name;

    private String description;

}
