package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Data
public class CompanyContactDTO implements java.io.Serializable {

    private Integer companyContactId;

    private Integer contactId;

    private Integer companyId;

}
