package com.bgh.myopeninvoice.api.domain.dto;

import com.bgh.myopeninvoice.common.util.EnumValidator;
import com.bgh.myopeninvoice.common.util.RateType;
import com.bgh.myopeninvoice.db.domain.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ContractDTO implements java.io.Serializable {

    private Integer contractId;

    @NotNull
    private Integer contractIsFor;

    @NotNull
    private Integer contractSignedWith;

    private Integer contractSignedWithSubcontract;

    @NotNull
    private BigDecimal rate;

    @EnumValidator(enumClass = RateType.class)
    @NotNull
    private String rateUnit;

    @NotNull
    private Integer ccyId;

    @NotNull
    private Date validFrom;

    @NotNull
    private Date validTo;

    private String description;

    private String contractNumber;

    private Integer contentId;

    private String purchaseOrder;

    @JsonIgnoreProperties({"content"})
    private ContentDTO contentByContentId;

    private CompanyContactDTO companyContactByContractIsFor;

    @JsonIgnoreProperties({"contentByContentId","companyContactsByCompanyId"})
    private CompanyDTO companyByContractSignedWith;

    @JsonIgnoreProperties({"contentByContentId","companyContactsByCompanyId"})
    private CompanyDTO companyByContractSignedWithSubcontract;

    private CurrencyDTO currencyByCcyId;

}
