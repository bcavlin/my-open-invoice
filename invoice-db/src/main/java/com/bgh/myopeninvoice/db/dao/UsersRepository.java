package com.bgh.myopeninvoice.db.dao;

import com.bgh.myopeninvoice.db.model.UsersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Created by bcavlin on 14/03/17.
 */
public interface UsersRepository extends PagingAndSortingRepository<UsersEntity, Integer>, QueryDslPredicateExecutor<UsersEntity> {

    @Query("select e from UsersEntity e where e.username = :username")
    Optional<UsersEntity> findByUsername(@Param("username") String username);

}
