package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CompanyContactDTO implements java.io.Serializable {

  private Integer companyContactId;

  private Integer contactId;

  private Integer companyId;

  @JsonIgnoreProperties({"companyContacts"})
  private ContactDTO contact;

  @JsonIgnoreProperties({"content", "companyContacts"})
  private CompanyDTO company;

  private boolean markedForRemoval;

  private Integer participatesInContracts;
}
