package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountDTO implements Serializable {

  private Integer accountId;

  private String name;

}
