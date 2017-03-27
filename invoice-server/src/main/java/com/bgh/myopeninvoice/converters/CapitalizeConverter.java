package com.bgh.myopeninvoice.converters;

import org.apache.commons.lang3.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by bcavlin on 26/03/17.
 */
@FacesConverter("capitalizeConverter")
public class CapitalizeConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return StringUtils.capitalize(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return StringUtils.trimToNull((String)o);
    }
}
