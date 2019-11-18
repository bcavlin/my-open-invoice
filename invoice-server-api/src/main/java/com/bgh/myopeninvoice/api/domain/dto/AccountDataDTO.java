package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AccountDataDTO implements Serializable {

  private Integer accountItemId;

  private Integer accountId;

  private LocalDate itemDate;

  private String description;

  private BigDecimal debit;

  private BigDecimal credit;

}
