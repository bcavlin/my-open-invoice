package com.bgh.myopeninvoice.api.domain.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DefaultResponse<T> extends OperationResponse {

  @Setter(AccessLevel.NONE)
  private final String objectType;

  private Long count;

  private List<T> details;

  @SuppressWarnings("unchecked")
  public DefaultResponse(Class<T> objectType) {
    this.objectType = objectType.getSimpleName();
  }
}
