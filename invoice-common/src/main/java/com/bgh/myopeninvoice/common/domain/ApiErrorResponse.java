package com.bgh.myopeninvoice.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
  private Integer status;
  private Integer code;
  private String message;
}
