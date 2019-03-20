package com.bgh.myopeninvoice.api.security;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AccountCredentials {

  @NotBlank private String username;

  @NotBlank private String password;
}
