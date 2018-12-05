package com.bgh.myopeninvoice.api.domain.dto;

import com.bgh.myopeninvoice.db.domain.CompanyEntity;
import com.bgh.myopeninvoice.db.domain.ContactEntity;
import com.bgh.myopeninvoice.db.domain.ContractEntity;
import com.bgh.myopeninvoice.db.domain.InvoiceEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;

@Data
public class CompanyContactDTO implements java.io.Serializable {

    private Integer companyContactId;

    private Integer contactId;

    private Integer companyId;

    @JsonIgnoreProperties({"userByUserId","contactsByCompanyEntity"})
    private ContactDTO contactByContactId;

    @JsonIgnoreProperties({"contentByContentId","contactsByContactEntity"})
    private CompanyDTO companyByCompanyId;

}
