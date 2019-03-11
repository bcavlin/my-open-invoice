package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class InvoiceDTO implements java.io.Serializable {

  private Integer invoiceId;

  @NotNull private Integer companyContactFrom;

  @NotNull private Integer companyContractTo;

  @NotNull
  private LocalDate fromDate;

  @NotNull
  private LocalDate toDate;

  private LocalDate fromDateAdjusted;

  private LocalDate toDateAdjusted;

  private Long fromToDays;

  @NotNull
  private ZonedDateTime createdAt;

  @NotNull private String title;

  @NotNull
  private LocalDate dueDate;

  @NotNull private BigDecimal taxPercent;

  private String note;

  private LocalDate paidDate;

  @NotNull private Integer ccyId;

  @NotNull private BigDecimal rate;

  @NotNull private String rateUnit;

  private BigDecimal totalValue;

  private BigDecimal totalValueWithTax;

  private Integer attachmentsSize;

  private CompanyContactDTO companyContact;

  private CurrencyDTO currency;

  @JsonIgnoreProperties({"content", "companyContact"})
  private ContractDTO contract;

  private Integer invoiceItemsSize;

  private Integer reportsSize;

}
