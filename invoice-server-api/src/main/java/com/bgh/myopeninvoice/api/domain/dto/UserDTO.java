package com.bgh.myopeninvoice.api.domain.dto;

import com.bgh.myopeninvoice.db.domain.UserRoleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

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

    private Collection<UserRoleDTO> userRolesByUserId;
}
