package com.bgh.myopeninvoice.api.domain.dto;

import com.bgh.myopeninvoice.api.domain.CustomJsonDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class InvoiceDTO implements java.io.Serializable {

  private Integer invoiceId;

  @NotNull private Integer companyContactFrom;

  @NotNull private Integer companyContractTo;

  @NotNull
  @JsonDeserialize(using = CustomJsonDateTimeDeserializer.class)
  private Date fromDate;

  @NotNull
  @JsonDeserialize(using = CustomJsonDateTimeDeserializer.class)
  private Date toDate;

  @NotNull
  @JsonDeserialize(using = CustomJsonDateTimeDeserializer.class)
  private Date createdDate;

  @NotNull private String title;

  @NotNull
  @JsonDeserialize(using = CustomJsonDateTimeDeserializer.class)
  private Date dueDate;

  @NotNull private BigDecimal taxPercent;

  private String note;

  @JsonDeserialize(using = CustomJsonDateTimeDeserializer.class)
  private Date paidDate;

  @NotNull private Integer ccyId;

  @NotNull private BigDecimal rate;

  @NotNull private String rateUnit;

  private BigDecimal totalValue;

  private BigDecimal totalValueWithTax;

  @JsonIgnoreProperties({"invoice"})
  private Collection<AttachmentDTO> attachments;

  private CompanyContactDTO companyContact;

  private CurrencyDTO currency;

  private ContractDTO contract;

  @JsonIgnoreProperties({"invoice"})
  private Collection<InvoiceItemsDTO> invoiceItems;

  private Collection<ReportsDTO> reports;
}
