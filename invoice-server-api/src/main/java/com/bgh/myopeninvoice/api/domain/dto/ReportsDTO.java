package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReportsDTO implements java.io.Serializable {

    private Integer reportId;

    private Integer invoiceId;

    private Integer contentId;

    private Date dateCreated;

    private String reportName;

}
