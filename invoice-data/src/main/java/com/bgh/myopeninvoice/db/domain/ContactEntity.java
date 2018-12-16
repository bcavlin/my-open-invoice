package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "CONTACT", schema = "INVOICE")
public class ContactEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTACT_ID", nullable = false)
    private Integer contactId;

    @Basic
    @Column(name = "FIRST_NAME", nullable = false, length = 100)
    private String firstName;

    @Basic
    @Column(name = "LAST_NAME", length = 100)
    private String lastName;

    @Basic
    @Column(name = "MIDDLE_NAME", length = 100)
    private String middleName;

    @Basic
    @Column(name = "EMAIL", nullable = false, length = 255)
    private String email;

    @Basic
    @Column(name = "ADDRESS_LINE1", length = 500)
    private String addressLine1;

    @Basic
    @Column(name = "ADDRESS_LINE2", length = 500)
    private String addressLine2;

    @Basic
    @Column(name = "PHONE_1", length = 20)
    private String phone1;

    @Basic
    @Column(name = "USER_ID", nullable = false)
    private Integer userId;

    @OneToMany(mappedBy = "contactByContactId")
    private Collection<CompanyContactEntity> companyContactsByContactId;

}
