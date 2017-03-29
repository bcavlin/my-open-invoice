package com.bgh.myopeninvoice.converters;

import com.bgh.myopeninvoice.db.dao.InvoiceDAO;
import com.bgh.myopeninvoice.db.model.ContactsEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Created by bcavlin on 21/03/17.
 */
@ManagedBean(name = "contactsEntityConverter")
@RequestScoped
public class ContactsEntityConverter implements Converter {

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
        return invoiceDAO.getContactsRepository().findOne(Integer.valueOf(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((ContactsEntity)o).getContactId().toString();
    }
}
