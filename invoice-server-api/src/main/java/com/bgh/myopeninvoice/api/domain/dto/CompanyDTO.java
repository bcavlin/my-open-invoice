package com.bgh.myopeninvoice.api.domain.dto;

import com.bgh.myopeninvoice.db.domain.ContactEntity;
import com.bgh.myopeninvoice.db.domain.ContractEntity;
import com.bgh.myopeninvoice.db.domain.InvoiceEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.annotation.MatchesPattern;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CompanyDTO implements java.io.Serializable {

    private Integer companyId;

    @NotNull
    private String companyName;

    @NotNull
    @MatchesPattern("[A-Z0-9]+")
    private String shortName;

    private String addressLine1;

    private String addressLine2;

    @MatchesPattern("^(\\+\\d{1,3})?(-|\\s|\\()?(\\d{3})(\\))?(-|\\s)?(\\d{3})(-|\\s)?(\\d{4})$")
    private String phone1;

    @NotNull
    private Boolean ownedByMe;

    private String businessNumber;

    @NotNull
    @MatchesPattern("^[1-7]$")
    private Integer weekStart;

    @JsonIgnoreProperties({"content"})
    private ContentDTO contentByContentId;

    @JsonIgnoreProperties({"companyByCompanyId"})
    private Collection<CompanyContactDTO> companyContactsByCompanyId;

    private Integer numberOfContacts;

    private Integer numberOfContracts;


}
