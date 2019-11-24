package com.bgh.myopeninvoice.db.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Entity
@Table(name = "ACCOUNT_DATA", schema = "INVOICE")
public class AccountDataEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ACCOUNT_ITEM_ID", nullable = false)
  private Integer accountItemId;

  @Basic
  @Column(name = "ACCOUNT_ID", nullable = false)
  private Integer accountId;

  @Column(name = "ITEM_DATE", nullable = false)
  private LocalDate itemDate;

  @Basic
  @Column(name = "DESCRIPTION", nullable = false, length = 255)
  private String description;

  @Basic
  @Column(name = "DEBIT", nullable = true, precision = 2)
  private BigDecimal debit;

  @Basic
  @Column(name = "CREDIT", nullable = true, precision = 2)
  private BigDecimal credit;

  @ManyToOne
  @JoinColumn(
          name = "ACCOUNT_ID",
          referencedColumnName = "ACCOUNT_ID",
          nullable = false,
          insertable = false,
          updatable = false)
  private AccountEntity accountByAccountId;

  @Transient
  public int hash() {
    return Objects.hash(
            accountId,
            itemDate,
            description,
            debit != null ? debit.setScale(2, RoundingMode.HALF_UP) : null,
            credit != null ? credit.setScale(2, RoundingMode.HALF_UP) : null);
  }
}
