package com.bgh.myopeninvoice.db.dao;

import com.bgh.myopeninvoice.db.model.CompanyContactEntity;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by bcavlin on 14/03/17.
 */
public interface CompanyContactRepository extends PagingAndSortingRepository<CompanyContactEntity, Integer>, QueryDslPredicateExecutor<CompanyContactEntity> {

}
