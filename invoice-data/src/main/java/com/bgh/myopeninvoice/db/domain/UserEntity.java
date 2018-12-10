package com.bgh.myopeninvoice.db.domain;

import com.bgh.myopeninvoice.db.domain.audit.UserDateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "USER", schema = "INVOICE")
@ToString(exclude = {"passwordHash"})
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends UserDateAudit {

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
    @Column(name = "LAST_LOGGED_DATE")
    private Date lastLoggedDate;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserRoleEntity> userRolesByUserId;


}
