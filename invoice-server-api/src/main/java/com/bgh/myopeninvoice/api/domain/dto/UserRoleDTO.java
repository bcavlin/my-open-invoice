package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserRoleDTO implements java.io.Serializable {

    private Integer userRoleId;

    private Integer userId;

    private Integer roleId;

    private Date assignedDate;

}
