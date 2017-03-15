package com.bgh.myopeninvoice.db.dao;

import com.bgh.myopeninvoice.db.model.UsersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by bcavlin on 14/03/17.
 */
public interface UsersRepository extends CrudRepository<UsersEntity, Integer> {

    @Query("select e from UsersEntity e where e.username = :username")
    Optional<UsersEntity> findByUsername(String username);

}
