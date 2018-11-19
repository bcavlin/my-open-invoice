package com.bgh.myopeninvoice.reporting.exception;

public class BirtReportException extends RuntimeException {

    public BirtReportException() {
        super();
    }

    public BirtReportException(String message) {
        super(message);
    }

    public BirtReportException(String message, Throwable cause) {
        super(message, cause);
    }

    public BirtReportException(Throwable cause) {
        super(cause);
    }

    protected BirtReportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
