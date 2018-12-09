package com.bgh.myopeninvoice.db.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.annotation.MatchesPattern;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;

@Data
@Entity
@Table(name = "COMPANY", schema = "INVOICE")
public class CompanyEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_ID", nullable = false)
    private Integer companyId;

    @Basic
    @Column(name = "COMPANY_NAME", nullable = false, length = 255, unique = true)
    private String companyName;

    @MatchesPattern("[A-Z0-9]+")
    @Basic
    @Column(name = "SHORT_NAME", nullable = false, length = 10, unique = true)
    private String shortName;

    @Basic
    @Column(name = "ADDRESS_LINE_1", nullable = false, length = 500)
    private String addressLine1;

    @Basic
    @Column(name = "ADDRESS_LINE_2", length = 500)
    private String addressLine2;

    @Basic
    @Column(name = "PHONE_1", length = 20)
    private String phone1;

    @Basic
    @Column(name = "OWNED_BY_ME", nullable = false)
    private Boolean ownedByMe;

    @Basic
    @Column(name = "BUSINESS_NUMBER", length = 30)
    private String businessNumber;

    @MatchesPattern("[1-7]")
    @Basic
    @Column(name = "WEEK_START", nullable = false)
    private Integer weekStart;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTENT_ID", referencedColumnName = "CONTENT_ID")
    private ContentEntity contentByContentId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "companyByCompanyId")
    private Collection<CompanyContactEntity> companyContactsByCompanyId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "companyByContractSignedWith")
    private Collection<ContractEntity> contractsByCompanyId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "companyByContractSignedWithSubcontract")
    private Collection<ContractEntity> contractsByCompanyId_0;

    @OneToMany(mappedBy = "companyByCompanyTo")
    private Collection<InvoiceEntity> invoicesByCompanyId;

    @Formula("(select count(*) from invoice.company_contact cc where cc.company_id = company_id)")
    private Integer numberOfContacts;

    @Formula("(select count(*) from invoice.contract cc where cc.contract_is_for = company_id)")
    private Integer numberOfContracts;

}
