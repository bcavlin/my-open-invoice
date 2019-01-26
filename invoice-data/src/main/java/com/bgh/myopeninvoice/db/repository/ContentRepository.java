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

import com.bgh.myopeninvoice.db.domain.ContentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** Created by bcavlin on 14/03/17. */
@Repository
public interface ContentRepository
    extends PagingAndSortingRepository<ContentEntity, Integer>,
        QuerydslPredicateExecutor<ContentEntity> {

  @Query(
      "select c.contentByContentId from CompanyEntity c where c.companyId = :companyId "
          + "and c.contentByContentId.contentTable = :table")
  ContentEntity findContentByCompanyId(
      @Param("companyId") Integer companyId, @Param("table") String table);

  @Query(
      "select c.contentByContentId from AttachmentEntity c where c.attachmentId = :attachmentId "
          + "and c.contentByContentId.contentTable = :table")
  ContentEntity findContentByAttachmentId(
      @Param("attachmentId") Integer attachmentId, @Param("table") String table);

  @Query(
      "select c.contentByContentId from ContractEntity c where c.contractId = :contractId "
          + "and c.contentByContentId.contentTable = :table")
  ContentEntity findContentByContractId(
      @Param("contractId") Integer contractId, @Param("table") String table);

  @Query(
      "select c.contentByContentId from ReportsEntity c where c.reportId = :reportId "
          + "and c.contentByContentId.contentTable = :table")
  ContentEntity findContentByReportsId(
      @Param("reportId") Integer reportId, @Param("table") String table);
}
