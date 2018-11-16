package com.bgh.myopeninvoice.api.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Locale;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomUPAToken extends UsernamePasswordAuthenticationToken {

    private Locale locale;

    public CustomUPAToken(Object principal, Object credentials, Locale locale) {
        super(principal, credentials);
        this.locale = locale;
    }

    public CustomUPAToken(Object principal,
                          Object credentials,
                          Collection<? extends GrantedAuthority> authorities,
                          Locale locale) {
        super(principal, credentials, authorities);
        this.locale = locale;
    }

}
