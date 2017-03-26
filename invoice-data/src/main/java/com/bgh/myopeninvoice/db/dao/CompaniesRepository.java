package com.bgh.myopeninvoice.db.dao;

import com.bgh.myopeninvoice.db.model.CompaniesEntity;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by bcavlin on 14/03/17.
 */
public interface CompaniesRepository extends PagingAndSortingRepository<CompaniesEntity, Integer>, QueryDslPredicateExecutor<CompaniesEntity> {

}
