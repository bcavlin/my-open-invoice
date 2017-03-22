package com.bgh.myopeninvoice.db.dao;

import com.bgh.myopeninvoice.db.model.UserRoleEntity;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by bcavlin on 14/03/17.
 */
public interface UserRoleRepository extends PagingAndSortingRepository<UserRoleEntity, Integer>, QueryDslPredicateExecutor<UserRoleEntity> {

}
