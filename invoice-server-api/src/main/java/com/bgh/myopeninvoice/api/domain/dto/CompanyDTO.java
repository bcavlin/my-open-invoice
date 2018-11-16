package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.annotation.MatchesPattern;
import javax.persistence.*;
import java.util.Collection;

@Data
public class CompanyDTO implements java.io.Serializable {

    private Integer companyId;

    private String companyName;

    @MatchesPattern("[A-Z0-9]+")
    private String shortName;

    private String addressLine1;

    private String addressLine2;

    private String phone1;

    private Boolean ownedByMe;

    private String businessNumber;

    private Integer contentId;

    @MatchesPattern("[1-7]")
    private Integer weekStart;

    @JsonIgnoreProperties({"content"})
    private ContentDTO contentByContentId;

}
