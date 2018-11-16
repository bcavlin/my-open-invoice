package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
public class CurrencyDTO implements java.io.Serializable {

    private Integer ccyId;

    private String name;

    private String description;

}
