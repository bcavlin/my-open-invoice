package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "CONTRACT", schema = "INVOICE")
public class ContractEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTRACT_ID", nullable = false)
    private Integer contractId;

    @Basic
    @Column(name = "CONTRACT_IS_FOR", nullable = false)
    private Integer contractIsFor;

    @Basic
    @Column(name = "CONTRACT_SIGNED_WITH", nullable = false)
    private Integer contractSignedWith;

    @Basic
    @Column(name = "CONTRACT_SIGNED_WITH_SUBCONTRACT", nullable = false)
    private Integer contractSignedWithSubcontract;

    @Basic
    @Column(name = "RATE", nullable = false, precision = 32767)
    private BigDecimal rate;

    @Basic
    @Column(name = "RATE_UNIT", nullable = false, length = 10)
    private String rateUnit;

    @Basic
    @Column(name = "CCY_ID", nullable = false)
    private Integer ccyId;

    @Temporal(TemporalType.DATE)
    @Column(name = "VALID_FROM", nullable = false)
    private Date validFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "VALID_TO")
    private Date validTo;

    @Basic
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;

    @Basic
    @Column(name = "CONTRACT_NUMBER", length = 50)
    private String contractNumber;

    @Basic
    @Column(name = "CONTENT_ID")
    private Integer contentId;

    @Basic
    @Column(name = "PURCHASE_ORDER", length = 50)
    private String purchaseOrder;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JoinColumn(name = "CONTRACT_IS_FOR", referencedColumnName = "COMPANY_CONTACT_ID", nullable = false,
            insertable = false, updatable = false)
    private CompanyContactEntity companyContactByContractIsFor;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JoinColumn(name = "CONTRACT_SIGNED_WITH", referencedColumnName = "COMPANY_ID", nullable = false,
            insertable = false, updatable = false)
    private CompanyEntity companyByContractSignedWith;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JoinColumn(name = "CONTRACT_SIGNED_WITH_SUBCONTRACT", referencedColumnName = "COMPANY_ID", nullable = false,
            insertable = false, updatable = false)
    private CompanyEntity companyByContractSignedWithSubcontract;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JoinColumn(name = "CCY_ID", referencedColumnName = "CCY_ID", nullable = false,
            insertable = false, updatable = false)
    private CurrencyEntity currencyByCcyId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JoinColumn(name = "CONTENT_ID", referencedColumnName = "CONTENT_ID",
            insertable = false, updatable = false)
    private ContentEntity contentByContentId;

    @OneToMany(mappedBy = "contractByCompanyContractTo")
    private Collection<InvoiceEntity> invoicesByContractId;

}