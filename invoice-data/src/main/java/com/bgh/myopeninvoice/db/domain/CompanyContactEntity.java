package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

@Data
@Entity
@ToString(exclude = {"contactByContactId", "companyByCompanyId"})
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
  @JoinColumn(
      name = "CONTACT_ID",
      referencedColumnName = "CONTACT_ID",
      nullable = false,
      insertable = false,
      updatable = false)
  private ContactEntity contactByContactId;

  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne
  @JoinColumn(
      name = "COMPANY_ID",
      referencedColumnName = "COMPANY_ID",
      insertable = false,
      updatable = false)
  private CompanyEntity companyByCompanyId;

  @Transient private boolean markedForRemoval;

  @Formula(
      "(select count(*) from invoice.contract cc where cc.company_contact_id = company_contact_id)")
  private Integer participatesInContracts;
}
