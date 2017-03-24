package com.bgh.myopeninvoice.db.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bcavlin on 23/03/17.
 */
@Entity
@Table(name = "COMPANY_CONTACT", schema = "INVOICE", catalog = "INVOICEDB")
public class CompanyContactEntity implements Serializable{
    private Integer companyContactId;
    private Integer contactId;
    private Integer companyId;
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

    @Basic
    @Column(name = "CONTACT_ID", nullable = false)
    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    @Basic
    @Column(name = "COMPANY_ID", nullable = false)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyContactEntity that = (CompanyContactEntity) o;

        if (companyContactId != null ? !companyContactId.equals(that.companyContactId) : that.companyContactId != null)
            return false;
        if (contactId != null ? !contactId.equals(that.contactId) : that.contactId != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = companyContactId != null ? companyContactId.hashCode() : 0;
        result = 31 * result + (contactId != null ? contactId.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
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
                ", contactId=" + contactId +
                ", companyId=" + companyId +
                '}';
    }
}
