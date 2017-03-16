package com.bgh.myopeninvoice.jsfbeans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by bcavlin on 15/03/17.
 */
@ManagedBean
@ApplicationScoped
@Component
public class ApplicationBean implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(ApplicationBean.class);

    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/logout");
    }
}
