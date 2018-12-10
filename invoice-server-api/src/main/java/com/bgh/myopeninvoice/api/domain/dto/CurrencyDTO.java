package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CurrencyDTO implements java.io.Serializable {

    private Integer ccyId;

    private String name;

    private String description;

}
