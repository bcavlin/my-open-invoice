package com.bgh.myopeninvoice.db.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CURRENCY", schema = "INVOICE")
public class CurrencyEntity implements java.io.Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CCY_ID", nullable = false)
  private Integer ccyId;

  @Basic
  @Column(name = "NAME", nullable = false, length = 10)
  private String name;

  @Basic
  @Column(name = "DESCRIPTION", nullable = false, length = 100)
  private String description;
}
