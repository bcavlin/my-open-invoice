package com.bgh.myopeninvoice.converters;

import com.bgh.myopeninvoice.db.dao.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.RolesEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Created by bcavlin on 21/03/17.
 */
@ManagedBean(name = "rolesEntityConverter")
@RequestScoped
public class RolesEntityConverter implements Converter {

    @ManagedProperty(value = "#{invoiceDAO}")
    private InvoiceDAO invoiceDAO;

    public InvoiceDAO getInvoiceDAO() {
        return invoiceDAO;
    }

    public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return invoiceDAO.getRolesRepository().findByRoleName(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((RolesEntity)o).getRoleName();
    }
}
