package com.bgh.myopeninvoice.api.domain.dto;

import com.bgh.myopeninvoice.db.domain.AttachmentEntity;
import com.bgh.myopeninvoice.db.domain.CompanyContactEntity;
import com.bgh.myopeninvoice.db.domain.InvoiceItemsEntity;
import com.bgh.myopeninvoice.db.domain.ReportsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class InvoiceDTO implements java.io.Serializable {

    private Integer invoiceId;

    @NotNull
    private Integer companyContactFrom;

    @NotNull
    private Integer companyContractTo;

    @NotNull
    private Date fromDate;

    @NotNull
    private Date toDate;

    @NotNull
    private Date createdDate;

    @NotNull
    private String title;

    @NotNull
    private Date dueDate;

    @NotNull
    private BigDecimal taxPercent;

    private String note;

    private Date paidDate;

    private BigDecimal rate;

    private String rateUnit;

    private BigDecimal totalValue;

    private BigDecimal totalValueWithTax;

    @JsonIgnoreProperties({"invoice"})
    private Collection<AttachmentDTO> attachments;

    @NotNull
    private CompanyContactDTO companyContact;

    @NotNull
    private CurrencyDTO currency;

    @NotNull
    private ContractDTO contract;

    private Collection<InvoiceItemsDTO> invoiceItems;

    private Collection<ReportsDTO> reports;

}
