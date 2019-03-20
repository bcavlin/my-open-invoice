package com.bgh.myopeninvoice.db.domain.audit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class UserDateAudit extends DateAudit {

  @CreatedBy
  @Column(name = "CREATED_BY", updatable = false)
  private Integer createdBy;

  @LastModifiedBy
  @Column(name = "UPDATED_BY")
  private Integer updatedBy;
}
