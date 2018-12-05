package com.bgh.myopeninvoice.api.domain;

import com.querydsl.core.BooleanBuilder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@Data
public class SearchParameters implements Serializable {

    public SearchParameters() {
        this.builder = new BooleanBuilder();
    }

    String filter;

    PageRequest pageRequest;

    Sort sort;

    //this will replace regular filter if set for special calls
    BooleanBuilder builder;
}
