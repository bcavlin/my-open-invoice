package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.querydsl.core.types.Predicate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommonService<T> {

    List<T> findAll(SearchParameters searchParameters);

    Predicate getPredicate(SearchParameters searchParameters);

    long count(SearchParameters searchParameters);

    List<T> findById(Integer id);

    @Transactional
    List<T> save(T entity);

    @Transactional
    void delete(Integer id);

}
