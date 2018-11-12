package com.bgh.myopeninvoice.db.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.annotation.MatchesPattern;
import javax.persistence.*;
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

    @Basic
    @Column(name = "CONTENT_ID")
    private Integer contentId;

    @MatchesPattern("[1-7]")
    @Basic
    @Column(name = "WEEK_START", nullable = false)
    private Integer weekStart;

    @JsonIgnoreProperties({"content"})
    @ManyToOne
    @JoinColumn(name = "CONTENT_ID", referencedColumnName = "CONTENT_ID",
            insertable = false, updatable = false)
    private ContentEntity contentByContentId;

    @JsonIgnore
    @OneToMany(mappedBy = "companyByCompanyId")
    private Collection<CompanyContactEntity> companyContactsByCompanyId;

    @JsonIgnore
    @OneToMany(mappedBy = "companyByContractSignedWith")
    private Collection<ContractEntity> contractsByCompanyId;

    @JsonIgnore
    @OneToMany(mappedBy = "companyByContractSignedWithSubcontract")
    private Collection<ContractEntity> contractsByCompanyId_0;

    @JsonIgnore
    @OneToMany(mappedBy = "companyByCompanyTo")
    private Collection<InvoiceEntity> invoicesByCompanyId;

    @JsonIgnore
    @Transient
    public String getContactsToList() {
        StringBuilder sb = new StringBuilder();
        for (CompanyContactEntity companyContactEntity : companyContactsByCompanyId) {
            if (sb.length() > 0) sb.append(",");
            sb.append(companyContactEntity.getContactByContactId().getContactId());
        }
        return sb.toString();
    }

}
