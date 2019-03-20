package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserRoleDTO implements java.io.Serializable {

  private Integer userRoleId;

  private Integer userId;

  private Integer roleId;

    private ZonedDateTime assignedAt;

  private RoleDTO role;
}
