package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class ContactDTO implements java.io.Serializable {

    private Integer contactId;

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

    private String addressLine1;

    private String addressLine2;

    private String phone1;

    private Integer userId;

    @JsonIgnoreProperties({"passwordHash"})
    private UserDTO userByUserId;

}
