package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Data
public class ContractDTO implements java.io.Serializable {

    private Integer contractId;

    private Integer contractIsFor;

    private Integer contractSignedWith;

    private Integer contractSignedWithSubcontract;

    private BigDecimal rate;

    private String rateUnit;

    private Integer ccyId;

    private Date validFrom;

    private Date validTo;

    private String description;

    private String contractNumber;

    private Integer contentId;

    private String purchaseOrder;

}
