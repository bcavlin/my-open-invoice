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

import com.bgh.myopeninvoice.db.domain.InvoiceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

/** Created by bcavlin on 14/03/17. */
@Repository
public interface InvoiceRepository
    extends PagingAndSortingRepository<InvoiceEntity, Integer>,
        QuerydslPredicateExecutor<InvoiceEntity> {

  @Query(value = "SELECT INVOICE.INVOICE_COUNTER_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
  Integer getNextSequence();

  @Query(
      value =
          "select sum(qt) + sum(hw) as total "
              + "from ( "
              + "       select i.INVOICE_ID, "
              + "              i.RATE_UNIT, "
              + "              sum(case "
              + "                    when i.RATE_UNIT <> 'TOTAL' then nvl(ii.QUANTITY * i.rate,0) "
              + "                    else ii.QUANTITY end)                                                    as qt, "
              + "              sum( "
              + "                  case when i.RATE_UNIT <> 'TOTAL' then nvl(ts.hours_worked * i.rate,0) else 0 end) as hw "
              + "       from invoice.INVOICE I "
              + "              left join invoice.INVOICE_ITEMS ii on I.INVOICE_ID = ii.INVOICE_ID "
              + "              left join (select INVOICE_ITEM_ID, sum(HOURS_WORKED) hours_worked "
              + "                         from invoice.TIME_SHEET "
              + "                         group by INVOICE_ITEM_ID) ts "
              + "                        on ts.INVOICE_ITEM_ID = ii.INVOICE_ITEM_ID "
              + "       where year(i.TO_DATE) = :_year "
              + "         and ii.INVOICE_ID is not null "
              + "       group by i.RATE_UNIT, i.INVOICE_ID "
              + "     )",
      nativeQuery = true)
  BigDecimal getTotalPerYear(@Param("_year") int year);

  @Query(
      value =
          "select sum(qt + hw) total, mth "
              + "from ( "
              + "       select i.INVOICE_ID, "
              + "              i.RATE_UNIT, "
              + "              month(i.TO_DATE) || '/' || year(i.TO_DATE)                                             mth, "
              + "              sum(case "
              + "                    when i.RATE_UNIT <> 'TOTAL' then nvl(ii.QUANTITY * i.rate, 0) "
              + "                    else ii.QUANTITY end)                                                            qt, "
              + "              sum( "
              + "                  case when i.RATE_UNIT <> 'TOTAL' then nvl(ts.hours_worked * i.rate, 0) else 0 end) hw "
              + "       from invoice.INVOICE I "
              + "              left join invoice.INVOICE_ITEMS ii on I.INVOICE_ID = ii.INVOICE_ID "
              + "              left join (select INVOICE_ITEM_ID, sum(HOURS_WORKED) hours_worked "
              + "                         from invoice.TIME_SHEET "
              + "                         group by INVOICE_ITEM_ID) ts "
              + "                        on ts.INVOICE_ITEM_ID = ii.INVOICE_ITEM_ID "
              + "       where i.TO_DATE >= DATEADD('MONTH', -12, CURRENT_DATE) "
              + "         and ii.INVOICE_ID is not null "
              + "       group by i.RATE_UNIT, i.INVOICE_ID,month(i.TO_DATE) "
              + "     ) "
              + "group by mth "
              + "order by total desc "
              + "limit 1",
      nativeQuery = true)
  List<Object[]> getMaxForLast12Mths();

  @Query(
          value =
                  "select sum(qt + hw) total, mth "
                          + "from ( "
                          + "       select i.INVOICE_ID, "
                          + "              i.RATE_UNIT, "
                          + "              month(i.TO_DATE) || '/' || year(i.TO_DATE)                                             mth, "
                          + "              sum(case "
                          + "                    when i.RATE_UNIT <> 'TOTAL' then nvl(ii.QUANTITY * i.rate, 0) "
                          + "                    else ii.QUANTITY end)                                                            qt, "
                          + "              sum( "
                          + "                  case when i.RATE_UNIT <> 'TOTAL' then nvl(ts.hours_worked * i.rate, 0) else 0 end) hw "
                          + "       from invoice.INVOICE I "
                          + "              left join invoice.INVOICE_ITEMS ii on I.INVOICE_ID = ii.INVOICE_ID "
                          + "              left join (select INVOICE_ITEM_ID, sum(HOURS_WORKED) hours_worked "
                          + "                         from invoice.TIME_SHEET "
                          + "                         group by INVOICE_ITEM_ID) ts "
                          + "                        on ts.INVOICE_ITEM_ID = ii.INVOICE_ITEM_ID "
                          + "       where i.TO_DATE >= DATEADD('MONTH', -12, CURRENT_DATE) "
                          + "         and ii.INVOICE_ID is not null "
                          + "       group by i.RATE_UNIT, i.INVOICE_ID,month(i.TO_DATE) "
                          + "     ) "
                          + "group by mth "
                          + "order by total asc "
                          + "limit 1",
          nativeQuery = true)
  List<Object[]> getMinForLast12Mths();
}
