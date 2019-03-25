package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Data
@Entity
@Table(name = "CONTRACT", schema = "INVOICE")
public class ContractEntity implements java.io.Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CONTRACT_ID", nullable = false)
  private Integer contractId;

  @Basic
  @Column(name = "COMPANY_CONTACT_ID", nullable = false)
  private Integer companyContactId;

  @Basic
  @Column(name = "COMPANY_ID", nullable = false)
  private Integer companyId;

  @Basic
  @Column(name = "COMPANY_ID_SUBCONTRACT", nullable = false)
  private Integer companyIdSubcontract;

  @Basic
  @Column(name = "RATE", nullable = false, precision = 32767)
  private BigDecimal rate;

  @Basic
  @Column(name = "RATE_UNIT", nullable = false, length = 10)
  private String rateUnit;

  @Basic
  @Column(name = "CCY_ID", nullable = false)
  private Integer ccyId;

  @Column(name = "VALID_FROM", nullable = false)
  private LocalDate validFrom;

  @Column(name = "VALID_TO")
  private LocalDate validTo;

  @Basic
  @Column(name = "DESCRIPTION", nullable = false, length = 100)
  private String description;

  @Basic
  @Column(name = "CONTRACT_NUMBER", length = 50)
  private String contractNumber;

  @Basic
  @Column(name = "PURCHASE_ORDER", length = 50)
  private String purchaseOrder;

  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne
  @JoinColumn(
      name = "COMPANY_CONTACT_ID",
      referencedColumnName = "COMPANY_CONTACT_ID",
      nullable = false,
      insertable = false,
      updatable = false)
  private CompanyContactEntity companyContactByCompanyContactId;

  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne
  @JoinColumn(
      name = "COMPANY_ID",
      referencedColumnName = "COMPANY_ID",
      nullable = false,
      insertable = false,
      updatable = false)
  private CompanyEntity companyByCompanyId;

  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne
  @JoinColumn(
      name = "COMPANY_ID_SUBCONTRACT",
      referencedColumnName = "COMPANY_ID",
      insertable = false,
      updatable = false)
  private CompanyEntity companyByCompanyIdSubcontract;

  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne
  @JoinColumn(
      name = "CCY_ID",
      referencedColumnName = "CCY_ID",
      nullable = false,
      insertable = false,
      updatable = false)
  private CurrencyEntity currencyByCcyId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "CONTENT_ID", referencedColumnName = "CONTENT_ID")
  private ContentEntity contentByContentId;

  @OneToMany(mappedBy = "contractByCompanyContractTo")
  private Collection<InvoiceEntity> invoicesByContractId;

  @Transient
  public boolean isContractValid() {
    if (validFrom != null && validTo != null) {

      LocalDate now = LocalDate.now();
      return now.isAfter(validFrom) && now.isBefore(validTo);

    } else {
      return false;
    }
  }

    @Transient
    public Integer getInvoicesByContractIdSize() {
        return Optional.ofNullable(invoicesByContractId).orElse(Collections.emptyList()).size();
    }
}
