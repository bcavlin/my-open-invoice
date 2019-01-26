package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.annotation.MatchesPattern;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CompanyDTO implements java.io.Serializable {

  private Integer companyId;

  @NotNull private String companyName;

  @NotNull
  @MatchesPattern("[A-Z0-9]+")
  private String shortName;

  private String address1;

  @MatchesPattern("^(\\+\\d{1,3})?(-|\\s|\\()?(\\d{3})(\\))?(-|\\s)?(\\d{3})(-|\\s)?(\\d{4})$")
  private String phone1;

  @NotNull private Boolean ownedByMe;

  private String businessNumber;

  @NotNull
  @MatchesPattern("^[1-7]$")
  private Integer weekStart;

  @JsonIgnoreProperties({"content"})
  private ContentDTO content;

  @JsonIgnoreProperties({"company"})
  private Collection<CompanyContactDTO> companyContacts;

  private Integer numberOfContacts;

  private Integer numberOfContracts;
}
