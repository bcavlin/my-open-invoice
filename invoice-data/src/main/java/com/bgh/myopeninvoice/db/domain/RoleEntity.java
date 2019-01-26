package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "ROLE", schema = "INVOICE")
@ToString(exclude = {"userRolesByRoleId"})
public class RoleEntity implements java.io.Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ROLE_ID", nullable = false)
  private Integer roleId;

  @Basic
  @Column(name = "ROLE_NAME", nullable = false, length = 45)
  private String roleName;

  @LazyCollection(LazyCollectionOption.TRUE)
  @OneToMany(mappedBy = "roleByRoleId")
  private Collection<UserRoleEntity> userRolesByRoleId;
}
