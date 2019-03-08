package com.bgh.myopeninvoice.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DefaultResponse<T> extends OperationResponse {

  private String type;

  private Long count;

  private List<T> details;

  @SuppressWarnings("unchecked")
  public DefaultResponse(Class<T> type) {
    this.type = type.getSimpleName();
    this.httpStatus = HttpStatus.OK;
  }
}
