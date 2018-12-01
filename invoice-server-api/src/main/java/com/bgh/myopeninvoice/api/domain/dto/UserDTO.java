package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.Instant;
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

    private Instant createdAt;

    private Instant updatedAt;

    private Integer createdBy;

    private Integer updatedBy;

}
