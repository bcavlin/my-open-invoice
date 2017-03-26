package com.bgh.myopeninvoice.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.math.BigDecimal;

/**
 * Created by bcavlin on 17/03/17.
 */
@FacesConverter("com.bgh.myopeninvoice.converters.PercentConverter")
public class PercentConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return new BigDecimal(s).divide(new BigDecimal(100.00), 4, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if(o instanceof BigDecimal){
            BigDecimal bd = (BigDecimal)o;
            return bd.multiply(new BigDecimal(100.00)).toString();
        }
        return null;
    }
}
