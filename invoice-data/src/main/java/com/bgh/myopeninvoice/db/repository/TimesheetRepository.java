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

import com.bgh.myopeninvoice.db.domain.TimesheetEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/** Created by bcavlin on 14/03/17. */
@Repository
public interface TimesheetRepository
    extends PagingAndSortingRepository<TimesheetEntity, Integer>,
        QuerydslPredicateExecutor<TimesheetEntity> {
  /*
  For some reason deleteall from out of th ebox is not working...
   */
  @Modifying
  @Transactional
  @Query("delete from TimesheetEntity e where e.timesheetId=:timesheetId")
  void deleteTimesheetEntityByTimesheetId(@Param("timesheetId") Integer timesheetId);
}
