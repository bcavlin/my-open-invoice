package com.bgh.myopeninvoice.common.util;

import java.time.format.DateTimeFormatter;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";

    public static final String DATE_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("[yyyy-MM-dd'T'HH:mm:ss.SSSZ][yyyy-MM-dd'T'HH:mm:ss.SSSX]");

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("[yyyy-MM-dd]");

    private Constants() {
    }
}
