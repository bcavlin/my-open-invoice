package com.bgh.myopeninvoice.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by bcavlin on 01/04/17.
 */
@Entity
@Table(name = "CONTRACTS", schema = "INVOICE", catalog = "INVOICEDB")
public class ContractsEntity implements Serializable{
    private Integer contractId;
    private Integer contractIsFor;
    private Integer contractSignedWith;
    private BigDecimal rate;
    private String rateUnit;
    private Integer ccy;
    private Date validFrom;
    private Date validTo;
    private String description;
    private CompanyContactEntity companyContactByContractIsFor;
    private CompaniesEntity companiesByContractSignedWith;
    private CurrencyEntity currencyByCcy;

    @GeneratedValue
    @Id
    @Column(name = "CONTRACT_ID", nullable = false)
    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    @Basic
    @Column(name = "CONTRACT_IS_FOR", nullable = false)
    public Integer getContractIsFor() {
        return contractIsFor;
    }

    public void setContractIsFor(Integer contractIsFor) {
        this.contractIsFor = contractIsFor;
    }

    @Basic
    @Column(name = "CONTRACT_SIGNED_WITH", nullable = false)
    public Integer getContractSignedWith() {
        return contractSignedWith;
    }

    public void setContractSignedWith(Integer contractSignedWith) {
        this.contractSignedWith = contractSignedWith;
    }

    @Basic
    @Column(name = "RATE", nullable = false, precision = 32767)
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "RATE_UNIT", nullable = false, length = 10)
    public String getRateUnit() {
        return rateUnit;
    }

    public void setRateUnit(String rateUnit) {
        this.rateUnit = rateUnit;
    }

    @Basic
    @Column(name = "CCY", nullable = false)
    public Integer getCcy() {
        return ccy;
    }

    public void setCcy(Integer ccy) {
        this.ccy = ccy;
    }

    @Temporal(TemporalType.DATE)
    @Basic
    @Column(name = "VALID_FROM", nullable = false)
    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    @Temporal(TemporalType.DATE)
    @Basic
    @Column(name = "VALID_TO", nullable = true)
    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContractsEntity that = (ContractsEntity) o;

        if (contractId != null ? !contractId.equals(that.contractId) : that.contractId != null) return false;
        if (contractIsFor != null ? !contractIsFor.equals(that.contractIsFor) : that.contractIsFor != null)
            return false;
        if (contractSignedWith != null ? !contractSignedWith.equals(that.contractSignedWith) : that.contractSignedWith != null)
            return false;
        if (rate != null ? !rate.equals(that.rate) : that.rate != null) return false;
        if (rateUnit != null ? !rateUnit.equals(that.rateUnit) : that.rateUnit != null) return false;
        if (ccy != null ? !ccy.equals(that.ccy) : that.ccy != null) return false;
        if (validFrom != null ? !validFrom.equals(that.validFrom) : that.validFrom != null) return false;
        if (validTo != null ? !validTo.equals(that.validTo) : that.validTo != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contractId != null ? contractId.hashCode() : 0;
        result = 31 * result + (contractIsFor != null ? contractIsFor.hashCode() : 0);
        result = 31 * result + (contractSignedWith != null ? contractSignedWith.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (rateUnit != null ? rateUnit.hashCode() : 0);
        result = 31 * result + (ccy != null ? ccy.hashCode() : 0);
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (validTo != null ? validTo.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "CONTRACT_SIGNED_WITH", referencedColumnName = "COMPANY_ID", nullable = false, insertable = false, updatable = false)
    public CompaniesEntity getCompaniesByContractSignedWith() {
        return companiesByContractSignedWith;
    }

    public void setCompaniesByContractSignedWith(CompaniesEntity companiesByContractSignedWith) {
        this.companiesByContractSignedWith = companiesByContractSignedWith;
    }

    @ManyToOne
    @JoinColumn(name = "CONTRACT_IS_FOR", referencedColumnName = "COMPANY_CONTACT_ID", nullable = false, insertable = false, updatable = false)
    public CompanyContactEntity getCompanyContactByContractIsFor() {
        return companyContactByContractIsFor;
    }

    public void setCompanyContactByContractIsFor(CompanyContactEntity companyContactByContractIsFor) {
        this.companyContactByContractIsFor = companyContactByContractIsFor;
    }

    @ManyToOne
    @JoinColumn(name = "CCY", referencedColumnName = "CCY", nullable = false)
    public CurrencyEntity getCurrencyByCcy() {
        return currencyByCcy;
    }

    public void setCurrencyByCcy(CurrencyEntity currencyByCcy) {
        this.currencyByCcy = currencyByCcy;
    }

    @Override
    public String toString() {
        return "ContractsEntity{" +
                "contractId=" + contractId +
                ", contractIsFor=" + contractIsFor +
                ", contractSignedWith=" + contractSignedWith +
                ", rate=" + rate +
                ", rateUnit='" + rateUnit + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", description='" + description + '\'' +
                '}';
    }
}
