package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO implements java.io.Serializable {

    private Integer userId;

    private String username;

    private String passwordHash;

    private String firstName;

    private String lastName;

    private Boolean enabled;

    private String email;

    private Date createdDate;

    private Date resetDate;

    private String lastModifiedBy;

    private Date lastModifiedDate;

    private Date lastLoggedDate;

}
