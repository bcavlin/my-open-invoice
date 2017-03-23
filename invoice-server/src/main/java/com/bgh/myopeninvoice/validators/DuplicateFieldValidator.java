package com.bgh.myopeninvoice.validators;

import org.apache.commons.lang3.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Created by bcavlin on 22/03/17.
 */
@FacesValidator(value = "duplicateFieldValidator")
public class DuplicateFieldValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        // Obtain the client ID of the first field from f:attribute.
        String attribute1 = (String) uiComponent.getAttributes().get("attribute1");

        // Find the actual JSF component for the client ID.
        UIInput textInput = (UIInput) facesContext.getViewRoot().findComponent(attribute1.trim());
        if (textInput == null)
            throw new IllegalArgumentException(String.format("Unable to find component with id %s", attribute1));
        // Get its value, the entered text of the first field.
        String value1 = (String) textInput.getValue();

        // Cast the value of the entered text of the second field back to String.
        String confirm = (String) o;

        if (StringUtils.isNotBlank(value1)) {
            if (!value1.equals(confirm)) {
                throw new ValidatorException(new FacesMessage());
            }
        } else if (StringUtils.isNotBlank(confirm)) {
            if (!confirm.equals(value1)) {
                throw new ValidatorException(new FacesMessage());
            }
        }
    }
}
