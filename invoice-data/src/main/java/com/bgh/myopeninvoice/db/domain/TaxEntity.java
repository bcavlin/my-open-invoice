package com.bgh.myopeninvoice.db.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "TAX", schema = "INVOICE")
public class TaxEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAX_ID", nullable = false)
    private Integer taxId;

    @Basic
    @Column(name = "PERCENT", nullable = false, precision = 0)
    private BigDecimal percent;

    @Basic
    @Column(name = "IDENTIFIER", nullable = false, length = 50)
    private String identifier;

}
