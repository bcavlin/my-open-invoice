package com.bgh.myopeninvoice.api.domain.dto;

import com.bgh.myopeninvoice.api.domain.CustomJsonDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ContentDTO implements java.io.Serializable {

    private Integer contentId;

    @NotNull
    private String contentTable;

    @NotNull
    private String filename;

    private byte[] content;

    @NotNull
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date dateCreated;

}
