package com.bgh.myopeninvoice.db.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bcavlin on 23/03/17.
 */
@Entity
@Table(name = "COMPANY_CONTACT", schema = "INVOICE", catalog = "INVOICEDB")
public class CompanyContactEntity implements Serializable{
    private Integer companyContactId;
    private CompaniesEntity companiesByCompanyId;
    private ContactsEntity contactsByContactId;

    @Id
    @GeneratedValue
    @Column(name = "COMPANY_CONTACT_ID", nullable = false)
    public Integer getCompanyContactId() {
        return companyContactId;
    }

    public void setCompanyContactId(Integer companyContactId) {
        this.companyContactId = companyContactId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyContactEntity)) return false;

        CompanyContactEntity that = (CompanyContactEntity) o;

        if (companyContactId != null ? !companyContactId.equals(that.companyContactId) : that.companyContactId != null)
            return false;
        if (companiesByCompanyId != null ? !companiesByCompanyId.equals(that.companiesByCompanyId) : that.companiesByCompanyId != null)
            return false;
        return contactsByContactId != null ? contactsByContactId.equals(that.contactsByContactId) : that.contactsByContactId == null;

    }

    @Override
    public int hashCode() {
        int result = companyContactId != null ? companyContactId.hashCode() : 0;
        result = 31 * result + (companiesByCompanyId != null ? companiesByCompanyId.hashCode() : 0);
        result = 31 * result + (contactsByContactId != null ? contactsByContactId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID", nullable = false, insertable = false, updatable = false)
    public CompaniesEntity getCompaniesByCompanyId() {
        return companiesByCompanyId;
    }

    public void setCompaniesByCompanyId(CompaniesEntity companiesByCompanyId) {
        this.companiesByCompanyId = companiesByCompanyId;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JoinColumn(name = "CONTACT_ID", referencedColumnName = "CONTACT_ID", nullable = false, insertable = false, updatable = false)
    public ContactsEntity getContactsByContactId() {
        return contactsByContactId;
    }

    public void setContactsByContactId(ContactsEntity contactsByContactId) {
        this.contactsByContactId = contactsByContactId;
    }

    @Override
    public String toString() {
        return "CompanyContactEntity{" +
                "companyContactId=" + companyContactId +
                '}';
    }
}
