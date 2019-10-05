package com.bgh.myopeninvoice.db.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ACCOUNT", schema = "INVOICE")
@Data
public class AccountEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ACCOUNT_ID", nullable = false)
  private Integer accountId;

  @Basic
  @Column(name = "NAME", nullable = false, length = 255)
  private String name;

}
