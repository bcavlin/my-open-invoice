package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserDTO implements java.io.Serializable {

    private Integer userId;

    @NotNull
    private String username;

    @NotNull
    private String passwordHash;

    private String firstName;

    private String lastName;

    @NotNull
    private Boolean enabled;

    @NotNull
    private String email;

    private Date createdDate;

    private Date resetDate;

    private String lastModifiedBy;

    private Date lastModifiedDate;

    private Date lastLoggedDate;

}
