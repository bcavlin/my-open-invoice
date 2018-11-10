package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.querydsl.core.types.Predicate;

import java.util.List;

public interface CommonService<T> {

    List<T> findAll(SearchParameters searchParameters);

    Predicate getPredicate(SearchParameters searchParameters);

    long count(SearchParameters searchParameters);

}
