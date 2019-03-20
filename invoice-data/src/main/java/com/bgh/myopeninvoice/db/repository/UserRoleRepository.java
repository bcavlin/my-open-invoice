/*
 * Copyright 2017 Branislav Cavlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bgh.myopeninvoice.db.repository;

import com.bgh.myopeninvoice.db.domain.RoleEntity;
import com.bgh.myopeninvoice.db.domain.UserRoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Created by bcavlin on 14/03/17. */
@Repository
public interface UserRoleRepository
    extends PagingAndSortingRepository<UserRoleEntity, Integer>,
        QuerydslPredicateExecutor<UserRoleEntity> {

  @SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
  @Query("select r.roleByRoleId from UserRoleEntity r where r.userByUserId.username = :username")
  List<RoleEntity> findRolesByUsername(@Param("username") String username);
}
