package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserDTO implements java.io.Serializable {

    private Integer userId;

    @NotNull
    private String username;

    private String passwordHash;

    private String firstName;

    private String lastName;

    @NotNull
    private Boolean enabled;

    @NotNull
    private String email;

    private Instant createdAt;

    private Instant updatedAt;

    private Integer createdBy;

    private Integer updatedBy;

    private Collection<UserRoleDTO> userRoles;
}
