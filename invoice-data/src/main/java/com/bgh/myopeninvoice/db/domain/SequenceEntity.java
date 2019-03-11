package com.bgh.myopeninvoice.db.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "SEQUENCE", schema = "INVOICE")
public class SequenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQUENCE_ID", nullable = false)
    private Integer sequenceId;

    @Basic
    @Column(name = "SEQUENCE_VALUE", nullable = false)
    private Integer sequenceValue;

    @Basic
    @Column(name = "SEQUENCE_NAME", nullable = false, length = 10)
    private String sequenceName;

    @Basic
    @Column(name = "UPDATED_AT", nullable = false)
    private ZonedDateTime updatedAt;

}
