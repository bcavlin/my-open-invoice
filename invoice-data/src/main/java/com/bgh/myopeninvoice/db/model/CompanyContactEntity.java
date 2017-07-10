/*
 * Copyright 2017 Branislav Cavlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bgh.myopeninvoice.db.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by bcavlin on 23/03/17.
 */
@Entity
@Table(name = "COMPANY_CONTACT", schema = "INVOICE", catalog = "INVOICEDB")
public class CompanyContactEntity implements Serializable{
    private Integer companyContactId;
    private CompaniesEntity companiesByCompanyId;
    private ContactsEntity contactsByContactId;
    private Collection<ContractsEntity> contractsByCompanyContactId;
    private Collection<InvoiceEntity> invoicesByCompanyContactId;

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
        if (contactsByContactId != null ? !contactsByContactId.equals(that.contactsByContactId) : that.contactsByContactId != null)
            return false;
        if (contractsByCompanyContactId != null ? !contractsByCompanyContactId.equals(that.contractsByCompanyContactId) : that.contractsByCompanyContactId != null)
            return false;
        return invoicesByCompanyContactId != null ? invoicesByCompanyContactId.equals(that.invoicesByCompanyContactId) : that.invoicesByCompanyContactId == null;
    }

    @Override
    public int hashCode() {
        int result = companyContactId != null ? companyContactId.hashCode() : 0;
        result = 31 * result + (companiesByCompanyId != null ? companiesByCompanyId.hashCode() : 0);
        result = 31 * result + (contactsByContactId != null ? contactsByContactId.hashCode() : 0);
        result = 31 * result + (contractsByCompanyContactId != null ? contractsByCompanyContactId.hashCode() : 0);
        result = 31 * result + (invoicesByCompanyContactId != null ? invoicesByCompanyContactId.hashCode() : 0);
        return result;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID", nullable = false)
    public CompaniesEntity getCompaniesByCompanyId() {
        return companiesByCompanyId;
    }

    public void setCompaniesByCompanyId(CompaniesEntity companiesByCompanyId) {
        this.companiesByCompanyId = companiesByCompanyId;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CONTACT_ID", referencedColumnName = "CONTACT_ID", nullable = false)
    public ContactsEntity getContactsByContactId() {
        return contactsByContactId;
    }

    public void setContactsByContactId(ContactsEntity contactsByContactId) {
        this.contactsByContactId = contactsByContactId;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "companyContactByContractIsFor")
    public Collection<ContractsEntity> getContractsByCompanyContactId() {
        return contractsByCompanyContactId;
    }

    public void setContractsByCompanyContactId(Collection<ContractsEntity> contractsByCompanyContactId) {
        this.contractsByCompanyContactId = contractsByCompanyContactId;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "companyContactByCompanyContactFrom")
    public Collection<InvoiceEntity> getInvoicesByCompanyContactId() {
        return invoicesByCompanyContactId;
    }

    public void setInvoicesByCompanyContactId(Collection<InvoiceEntity> invoicesByCompanyContactId) {
        this.invoicesByCompanyContactId = invoicesByCompanyContactId;
    }

//    @Transient
//    public ContractsEntity findValidContract(CompaniesEntity company){
//        for (ContractsEntity contractsEntity : contractsByCompanyContactId) {
//            if(contractsEntity.getCompaniesByContractSignedWith().equals(company)){
//                return contractsEntity;
//            }
//        }
//        return null;
//    }

    @Override
    public String toString() {
        return "CompanyContactEntity{" +
                "companyContactId=" + companyContactId +
                ", companiesByCompanyId=" + companiesByCompanyId +
                ", contactsByContactId=" + contactsByContactId +
                ", contractsByCompanyContactId=" + contractsByCompanyContactId +
                ", invoicesByCompanyContactId=" + invoicesByCompanyContactId +
                '}';
    }
}
