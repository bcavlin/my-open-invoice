package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.security.CustomUPAToken;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class AbstractController {

    @Getter
    @Autowired
    private MessageSource messageSource;

    static final String ENTITY_ID_CANNOT_BE_NULL = "entity.id-cannot-be-null";

    static final String FILTER = "filter";

    Pattern patternFields = Pattern.compile("(?:(?:, )?#(\\w+:\\w+))+", Pattern.CASE_INSENSITIVE);

    Locale getContextLocale() {
        CustomUPAToken token = (CustomUPAToken) SecurityContextHolder.getContext().getAuthentication();
        return token.getLocale();
    }

    protected void validateSpecialFilter(@RequestParam Map<String, String> queryParameters, SearchParameters searchParameters) {
    }

}