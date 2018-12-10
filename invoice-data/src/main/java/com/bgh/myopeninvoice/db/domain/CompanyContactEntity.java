package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "COMPANY_CONTACT", schema = "INVOICE")
public class CompanyContactEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_CONTACT_ID", nullable = false)
    private Integer companyContactId;

    @Basic
    @Column(name = "CONTACT_ID", nullable = false)
    private Integer contactId;

    @Basic
    @Column(name = "COMPANY_ID")
    private Integer companyId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JoinColumn(name = "CONTACT_ID", referencedColumnName = "CONTACT_ID", nullable = false,
            insertable = false, updatable = false)
    private ContactEntity contactByContactId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID",
            insertable = false, updatable = false)
    private CompanyEntity companyByCompanyId;

//    @LazyCollection(LazyCollectionOption.FALSE)
//    @OneToMany(mappedBy = "companyContactByContractIsFor")
//    private Collection<ContractEntity> contractsByCompanyContactId;
//
//    @LazyCollection(LazyCollectionOption.FALSE)
//    @OneToMany(mappedBy = "companyContactByCompanyContactFrom")
//    private Collection<InvoiceEntity> invoicesByCompanyContactId;


}
