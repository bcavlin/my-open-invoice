package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDTO implements Serializable {

    private Integer roleId;

    private String roleName;

}
