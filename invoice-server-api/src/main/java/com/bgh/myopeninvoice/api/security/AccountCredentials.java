package com.bgh.myopeninvoice.api.security;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AccountCredentials {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
