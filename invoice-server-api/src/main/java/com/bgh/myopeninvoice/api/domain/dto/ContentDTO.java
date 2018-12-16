package com.bgh.myopeninvoice.api.domain.dto;

import com.bgh.myopeninvoice.api.domain.CustomJsonDateDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ContentDTO implements java.io.Serializable {

    private Integer contentId;

    @NotNull
    private String contentTable;

    @NotNull
    private String filename;

    @Setter(AccessLevel.NONE)
    private byte[] content;

    @NotNull
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date dateCreated;

}
