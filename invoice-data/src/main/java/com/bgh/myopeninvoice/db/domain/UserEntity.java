package com.bgh.myopeninvoice.db.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "USER", schema = "INVOICE")
@ToString(exclude = {"contactsByUserId"})
public class UserEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private Integer userId;

    @Basic
    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;

    @Basic
    @Column(name = "PASSWORD_HASH", length = 80)
    private String passwordHash;

    @Basic
    @Column(name = "FIRST_NAME", length = 50)
    private String firstName;

    @Basic
    @Column(name = "LAST_NAME", length = 50)
    private String lastName;

    @Basic
    @Column(name = "ENABLED")
    private Boolean enabled;

    @Basic
    @Column(name = "EMAIL", nullable = false, length = 254)
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RESET_DATE")
    private Date resetDate;

    @Basic
    @Column(name = "LAST_MODIFIED_BY", length = 50)
    private String lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED_DATE")
    private Date lastModifiedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_LOGGED_DATE")
    private Date lastLoggedDate;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "userByUserId")
    private Collection<ContactEntity> contactsByUserId;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserRoleEntity> userRolesByUserId;


}
