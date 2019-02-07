package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ReportsDTO implements java.io.Serializable {

  private Integer reportId;

  private Integer invoiceId;

  private Integer contentId;

  private LocalDate dateCreated;

  private String reportName;
}
