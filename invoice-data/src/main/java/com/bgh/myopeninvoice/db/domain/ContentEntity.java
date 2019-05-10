package com.bgh.myopeninvoice.db.domain;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@ToString(exclude = {"content"})
@Table(name = "CONTENT", schema = "INVOICE")
public class ContentEntity implements java.io.Serializable {

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
  @Type(type = "org.hibernate.type.BinaryType")
  @Column(name = "CONTENT")
  private byte[] content;

  @Column(name = "CREATED_AT", nullable = false)
  private ZonedDateTime createdAt;

  @Transient
  public int getSizeInBytes() {
    return content != null ? content.length : 0;
  }

  public enum ContentEntityTable {
    COMPANY,
    REPORTS,
    CONTRACT,
    ATTACHMENT
  }
}
