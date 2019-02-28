package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ContentDTO implements java.io.Serializable {

  private Integer contentId;

  @NotNull private String contentTable;

  @NotNull private String filename;

  /* We need to get content as a separate call - using proxy pattern */
  @Setter(AccessLevel.NONE)
  private byte[] content;

  @NotNull private LocalDateTime dateCreated;
}
