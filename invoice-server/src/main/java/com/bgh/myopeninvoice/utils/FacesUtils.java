package com.bgh.myopeninvoice.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.text.NumberFormat;

/**
 * Created with IntelliJ IDEA.
 * User: bcavlin
 * Date: 2/11/14
 * To change this template use File | Settings | File Templates.
 */
public class FacesUtils {

    public static void addErrorMessage(String msg) {
        addMessage(FacesMessage.SEVERITY_ERROR, msg);
    }

    private static void addMessage(FacesMessage.Severity severity, String msg) {
        final FacesMessage facesMsg = new FacesMessage(severity, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        addMessage(FacesMessage.SEVERITY_INFO, msg);
    }

    public static String getBundleKey(String bundleName, String key) {
        return FacesContext
                .getCurrentInstance()
                .getApplication()
                .getResourceBundle(FacesContext.getCurrentInstance(),
                        bundleName).getString(key);
    }

    public static NumberFormat getNumberConverter(String type) {
        if (type.equalsIgnoreCase("currency")) {
            return NumberFormat.getCurrencyInstance(getContext().getViewRoot().getLocale());
        } else if (type.equalsIgnoreCase("percent")) {
            return NumberFormat.getPercentInstance(getContext().getViewRoot().getLocale());
        } else if (type.equalsIgnoreCase("number")) {
            return NumberFormat.getNumberInstance(getContext().getViewRoot().getLocale());
        } else {
            throw new IllegalArgumentException("No converter for " + type);
        }
    }

    private static FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    @SuppressWarnings("unchecked")
    public static <T> T findBean(String beanName) {
        return (T) getContext().getApplication().evaluateExpressionGet(getContext(), "#{" + beanName + "}",
                Object.class);
    }
}
