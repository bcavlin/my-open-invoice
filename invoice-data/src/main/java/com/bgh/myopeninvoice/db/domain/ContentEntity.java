package com.bgh.myopeninvoice.db.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "CONTENT", schema = "INVOICE")
public class ContentEntity implements java.io.Serializable {

    public enum ContentEntityTable {
        COMPANY, REPORTS, CONTRACT, ATTACHMENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENT_ID", nullable = false)
    private Integer contentId;

    @Basic
    @Column(name = "CONTENT_TABLE", nullable = false, length = 50)
    private String contentTable;

    @Basic
    @Column(name = "FILENAME", nullable = false, length = 255)
    private String filename;

    @Lob
    @Column(name = "CONTENT")
    private byte[] content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATED", nullable = false)
    private Date dateCreated;

    @OneToMany(mappedBy = "contentByContentId")
    private Collection<AttachmentEntity> attachmentsByContentId;

    @OneToMany(mappedBy = "contentByContentId")
    private Collection<CompanyEntity> companiesByContentId;

    @OneToMany(mappedBy = "contentByContentId")
    private Collection<ContractEntity> contractsByContentId;

    @OneToMany(mappedBy = "contentByContentId")
    private Collection<ReportsEntity> reportsByContentId;

}
