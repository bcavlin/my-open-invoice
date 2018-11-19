package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ContentDTO implements java.io.Serializable {

    private Integer contentId;

    private String contentTable;

    private String filename;

    private byte[] content;

    private Date dateCreated;

}
