package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
public class ContentDTO implements java.io.Serializable {

    private Integer contentId;

    private String contentTable;

    private String filename;

    private byte[] content;

    private Date dateCreated;

}
