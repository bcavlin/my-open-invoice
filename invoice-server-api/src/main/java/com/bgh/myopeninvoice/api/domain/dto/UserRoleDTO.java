package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserRoleDTO implements java.io.Serializable {

    private Integer userRoleId;

    private Integer userId;

    private Integer roleId;

    private Date assignedDate;

    private RoleDTO role;

}
