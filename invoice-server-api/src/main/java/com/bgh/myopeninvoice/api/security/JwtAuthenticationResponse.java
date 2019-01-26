package com.bgh.myopeninvoice.api.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthenticationResponse {

  private static final String tokenType = "Bearer";

  private final String accessToken;
}
