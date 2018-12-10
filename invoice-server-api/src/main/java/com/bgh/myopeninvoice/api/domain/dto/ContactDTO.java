package com.bgh.myopeninvoice.api.domain.dto;

import com.bgh.myopeninvoice.db.domain.CompanyContactEntity;
import com.bgh.myopeninvoice.db.domain.CompanyEntity;
import com.bgh.myopeninvoice.db.domain.ContractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ContactDTO implements Serializable {

    private Integer contactId;

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

    private String addressLine1;

    private String addressLine2;

    private String phone1;

    private Integer userId;

    @JsonIgnoreProperties({"contactByContactId"})
    private Collection<CompanyContactDTO> companyContactsByContactId;

}
