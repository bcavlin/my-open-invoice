package com.bgh.myopeninvoice.db.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by bcavlin on 14/03/17.
 */
@Entity
@Table(name = "COMPANIES", schema = "INVOICE", catalog = "INVOICEDB")
public class CompaniesEntity implements Serializable {
    private Integer companyId;
    private String companyName;
    private String addressLine1;
    private String addressLine2;
    private String phone1;
    private Integer ownedByMe;
    private String businessNumber;
    private byte[] companyLogo;
    private Collection<CompanyContactEntity> companyContactsByCompanyId;
    private Collection<InvoiceEntity> invoicesByCompanyId;
    private Collection<InvoiceEntity> invoicesByCompanyId_0;
    private Collection<RatesEntity> ratesByCompanyId;

    @Id
    @GeneratedValue
    @Column(name = "COMPANY_ID", nullable = false)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "COMPANY_NAME", nullable = false, length = 255)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "ADDRESS_LINE1", nullable = false, length = 500)
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @Basic
    @Column(name = "ADDRESS_LINE2", nullable = true, length = 500)
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @Basic
    @Column(name = "PHONE1", nullable = true, length = 20)
    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    @Basic
    @Column(name = "OWNED_BY_ME", nullable = false)
    public Integer getOwnedByMe() {
        return ownedByMe;
    }

    public void setOwnedByMe(Integer ownedByMe) {
        this.ownedByMe = ownedByMe;
    }

    @Basic
    @Column(name = "BUSINESS_NUMBER", nullable = true, length = 30)
    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    @Lob
    @Column(name = "COMPANY_LOGO", nullable = true)
    public byte[] getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompaniesEntity that = (CompaniesEntity) o;

        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (addressLine1 != null ? !addressLine1.equals(that.addressLine1) : that.addressLine1 != null) return false;
        if (addressLine2 != null ? !addressLine2.equals(that.addressLine2) : that.addressLine2 != null) return false;
        if (phone1 != null ? !phone1.equals(that.phone1) : that.phone1 != null) return false;
        if (ownedByMe != null ? !ownedByMe.equals(that.ownedByMe) : that.ownedByMe != null) return false;
        if (businessNumber != null ? !businessNumber.equals(that.businessNumber) : that.businessNumber != null)
            return false;
        if (!Arrays.equals(companyLogo, that.companyLogo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = companyId != null ? companyId.hashCode() : 0;
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (addressLine1 != null ? addressLine1.hashCode() : 0);
        result = 31 * result + (addressLine2 != null ? addressLine2.hashCode() : 0);
        result = 31 * result + (phone1 != null ? phone1.hashCode() : 0);
        result = 31 * result + (ownedByMe != null ? ownedByMe.hashCode() : 0);
        result = 31 * result + (businessNumber != null ? businessNumber.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(companyLogo);
        return result;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "companiesByCompanyId")
    public Collection<CompanyContactEntity> getCompanyContactsByCompanyId() {
        return companyContactsByCompanyId;
    }

    public void setCompanyContactsByCompanyId(Collection<CompanyContactEntity> companyContactsByCompanyId) {
        this.companyContactsByCompanyId = companyContactsByCompanyId;
    }

    @OneToMany(mappedBy = "companiesByCompanyFrom")
    public Collection<InvoiceEntity> getInvoicesByCompanyId() {
        return invoicesByCompanyId;
    }

    public void setInvoicesByCompanyId(Collection<InvoiceEntity> invoicesByCompanyId) {
        this.invoicesByCompanyId = invoicesByCompanyId;
    }

    @OneToMany(mappedBy = "companiesByCompanyTo")
    public Collection<InvoiceEntity> getInvoicesByCompanyId_0() {
        return invoicesByCompanyId_0;
    }

    public void setInvoicesByCompanyId_0(Collection<InvoiceEntity> invoicesByCompanyId_0) {
        this.invoicesByCompanyId_0 = invoicesByCompanyId_0;
    }

    @OneToMany(mappedBy = "companiesByCompanyId")
    public Collection<RatesEntity> getRatesByCompanyId() {
        return ratesByCompanyId;
    }

    public void setRatesByCompanyId(Collection<RatesEntity> ratesByCompanyId) {
        this.ratesByCompanyId = ratesByCompanyId;
    }
}
