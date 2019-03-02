package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ReportDTO implements java.io.Serializable {

  private Integer reportId;

  @NotNull
  private Integer invoiceId;

  @JsonIgnoreProperties("content")
  private ContentDTO content;

  private ZonedDateTime dateCreated;

  @NotNull
  private String reportName;
}
