package com.bgh.myopeninvoice.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by bcavlin on 14/03/17.
 */
@Entity
@Table(name = "RATES", schema = "INVOICE", catalog = "INVOICEDB")
public class RatesEntity implements Serializable {
    private Integer rateId;
    private Integer companyId;
    private BigDecimal ratePerHour;
    private BigDecimal ratePerDay;
    private Integer rateForUser;
    private Date validFrom;
    private Date validTo;

    @Id
    @GeneratedValue
    @Column(name = "RATE_ID", nullable = false)
    public Integer getRateId() {
        return rateId;
    }

    public void setRateId(Integer rateId) {
        this.rateId = rateId;
    }

    @Basic
    @Column(name = "COMPANY_ID", nullable = false)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "RATE_PER_HOUR", nullable = true, precision = 32767)
    public BigDecimal getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(BigDecimal ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    @Basic
    @Column(name = "RATE_PER_DAY", nullable = true, precision = 32767)
    public BigDecimal getRatePerDay() {
        return ratePerDay;
    }

    public void setRatePerDay(BigDecimal ratePerDay) {
        this.ratePerDay = ratePerDay;
    }

    @Basic
    @Column(name = "RATE_FOR_USER", nullable = true)
    public Integer getRateForUser() {
        return rateForUser;
    }

    public void setRateForUser(Integer rateForUser) {
        this.rateForUser = rateForUser;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "VALID_FROM", nullable = false)
    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "VALID_TO", nullable = true)
    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatesEntity that = (RatesEntity) o;

        if (rateId != null ? !rateId.equals(that.rateId) : that.rateId != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (ratePerHour != null ? !ratePerHour.equals(that.ratePerHour) : that.ratePerHour != null) return false;
        if (ratePerDay != null ? !ratePerDay.equals(that.ratePerDay) : that.ratePerDay != null) return false;
        if (rateForUser != null ? !rateForUser.equals(that.rateForUser) : that.rateForUser != null) return false;
        if (validFrom != null ? !validFrom.equals(that.validFrom) : that.validFrom != null) return false;
        if (validTo != null ? !validTo.equals(that.validTo) : that.validTo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rateId != null ? rateId.hashCode() : 0;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (ratePerHour != null ? ratePerHour.hashCode() : 0);
        result = 31 * result + (ratePerDay != null ? ratePerDay.hashCode() : 0);
        result = 31 * result + (rateForUser != null ? rateForUser.hashCode() : 0);
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (validTo != null ? validTo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RatesEntity{" +
                "rateId=" + rateId +
                ", companyId=" + companyId +
                ", ratePerHour=" + ratePerHour +
                ", ratePerDay=" + ratePerDay +
                ", rateForUser=" + rateForUser +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                '}';
    }
}
