package com.bgh.myopeninvoice.db.dao;

import com.bgh.myopeninvoice.db.model.ContractsEntity;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by bcavlin on 14/03/17.
 */
public interface ContractsRepository extends PagingAndSortingRepository<ContractsEntity, Integer>, QueryDslPredicateExecutor<ContractsEntity> {

}
