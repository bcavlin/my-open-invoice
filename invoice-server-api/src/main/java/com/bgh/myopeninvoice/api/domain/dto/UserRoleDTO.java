package com.bgh.myopeninvoice.api.domain.dto;

import com.bgh.myopeninvoice.db.domain.RoleEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;

@Data
public class UserRoleDTO implements java.io.Serializable {

    private Integer userRoleId;

    private Integer userId;

    private Integer roleId;

    private Date assignedDate;

}
