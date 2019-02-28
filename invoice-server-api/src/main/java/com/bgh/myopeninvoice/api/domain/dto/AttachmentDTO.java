package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AttachmentDTO implements java.io.Serializable {

  private Integer attachmentId;

  @NotNull
  private Integer invoiceId;

  @JsonIgnoreProperties("content")
  private ContentDTO content;
}
