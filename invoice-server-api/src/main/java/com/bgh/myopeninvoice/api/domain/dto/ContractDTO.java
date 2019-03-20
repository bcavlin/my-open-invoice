package com.bgh.myopeninvoice.api.domain.dto;

import com.bgh.myopeninvoice.common.util.EnumValidator;
import com.bgh.myopeninvoice.common.util.RateType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ContractDTO implements java.io.Serializable {

  private Integer contractId;

  @NotNull private Integer companyContactId;

  @NotNull private Integer companyId;

  private Integer companyIdSubcontract;

  @NotNull private BigDecimal rate;

  @EnumValidator(enumClass = RateType.class)
  @NotNull
  private String rateUnit;

  @NotNull private Integer ccyId;

  @NotNull
  private LocalDate validFrom;

  @NotNull
  private LocalDate validTo;

  private String description;

  private String contractNumber;

  private String purchaseOrder;

  @JsonIgnoreProperties({"content"})
  private ContentDTO content;

  @JsonIgnoreProperties({"contracts", "invoices"})
  private CompanyContactDTO companyContact;

  @JsonIgnoreProperties({"content", "companyContacts"})
  private CompanyDTO company;

  @JsonIgnoreProperties({"content", "companyContacts"})
  private CompanyDTO companySubcontract;

  private CurrencyDTO currency;

  private boolean contractValid;
}
