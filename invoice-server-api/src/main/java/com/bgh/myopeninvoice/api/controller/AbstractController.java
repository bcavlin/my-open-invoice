package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.security.CustomUPAToken;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Locale;

@Data
public abstract class AbstractController {

    @Autowired
    private MessageSource messageSource;

    Locale getContextLocale() {
        CustomUPAToken token = (CustomUPAToken) SecurityContextHolder.getContext().getAuthentication();
        return token.getLocale();
    }
}
