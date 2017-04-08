package com.bgh.myopeninvoice.db.dao;

import com.bgh.myopeninvoice.db.model.CurrencyEntity;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by bcavlin on 14/03/17.
 */
public interface CurrencyRepository extends PagingAndSortingRepository<CurrencyEntity, Integer>, QueryDslPredicateExecutor<CurrencyEntity> {

}
