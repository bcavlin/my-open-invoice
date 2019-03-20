package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "USER_ROLE", schema = "INVOICE")
@ToString(exclude = {"userByUserId"})
public class UserRoleEntity implements java.io.Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ROLE_ID", nullable = false)
  private Integer userRoleId;

  @Basic
  @Column(name = "USER_ID", nullable = false)
  private Integer userId;

  @Basic
  @Column(name = "ROLE_ID", nullable = false)
  private Integer roleId;

    @Column(name = "ASSIGNED_AT", nullable = false)
    private ZonedDateTime assignedAt;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(
      name = "USER_ID",
      referencedColumnName = "USER_ID",
      nullable = false,
      insertable = false,
      updatable = false)
  private UserEntity userByUserId;

  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(
      name = "ROLE_ID",
      referencedColumnName = "ROLE_ID",
      nullable = false,
      insertable = false,
      updatable = false)
  private RoleEntity roleByRoleId;
}
