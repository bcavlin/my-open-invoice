package com.bgh.myopeninvoice.db.repository;

import com.bgh.myopeninvoice.db.domain.InvoiceEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Profile("prod")
public interface StatisticsPostgresRepository extends CrudRepository<InvoiceEntity, Integer>, InvoiceMarker {

    @Query(
            value =
                    "select sum(qt) + sum(hw) as total "
                            + "from ( "
                            + "       select i.invoice_id, "
                            + "              i.rate_unit, "
                            + "              sum(case "
                            + "                    when i.rate_unit <> 'total' then coalesce(ii.quantity * i.rate, 0) "
                            + "                    else ii.quantity end)                                                            as qt, "
                            + "              sum( "
                            + "                  case when i.rate_unit <> 'total' then coalesce(ts.hours_worked * i.rate, 0) else 0 end) as hw "
                            + "       from invoice.invoice i "
                            + "              left join invoice.invoice_items ii on i.invoice_id = ii.invoice_id "
                            + "              left join (select invoice_item_id, sum(hours_worked) hours_worked "
                            + "                         from invoice.timesheet "
                            + "                         group by invoice_item_id) as ts "
                            + "                        on ts.invoice_item_id = ii.invoice_item_id "
                            + "       where date_part('year', i.to_date) = :_year "
                            + "         and ii.invoice_id is not null "
                            + "       group by i.rate_unit, i.invoice_id "
                            + "     ) as _inter",
            nativeQuery = true)
    BigDecimal getTotalPerYear(@Param("_year") int year);

    @Query(
            value =
                    "select sum(qt + hw) total, mth "
                            + "from ( "
                            + "       select i.invoice_id, "
                            + "              i.rate_unit, "
                            + "              date_part('month', i.to_date) || '/' || date_part('year', i.to_date)                        as mth, "
                            + "              sum(case "
                            + "                    when i.rate_unit <> 'TOTAL' then coalesce(ii.quantity * i.rate, 0) "
                            + "                    else ii.quantity end)                                                                 as qt, "
                            + "              sum( "
                            + "                  case when i.rate_unit <> 'TOTAL' then coalesce(ts.hours_worked * i.rate, 0) else 0 end) as hw "
                            + "       from invoice.invoice i "
                            + "              left join invoice.invoice_items ii on i.invoice_id = ii.invoice_id "
                            + "              left join (select invoice_item_id, sum(hours_worked) hours_worked "
                            + "                         from invoice.timesheet "
                            + "                         group by invoice_item_id) as ts "
                            + "                        on ts.invoice_item_id = ii.invoice_item_id "
                            + "       where i.to_date >= (now() - interval '12 month') "
                            + "         and ii.invoice_id is not null "
                            + "       group by i.rate_unit, i.invoice_id, date_part('month',i.to_date) "
                            + "     ) as _inter "
                            + "group by mth "
                            + "order by total desc",
            nativeQuery = true)
    List<Object[]> getMaxForLast12Mths();

    @Query(
            value =
                    "select sum(qt + hw) total, mth "
                            + "from ( "
                            + "       select i.invoice_id, "
                            + "              i.rate_unit, "
                            + "              date_part('month', i.to_date) || '/' || date_part('year', i.to_date)                        as mth, "
                            + "              sum(case "
                            + "                    when i.rate_unit <> 'TOTAL' then coalesce(ii.quantity * i.rate, 0) "
                            + "                    else ii.quantity end)                                                                 as qt, "
                            + "              sum( "
                            + "                  case when i.rate_unit <> 'TOTAL' then coalesce(ts.hours_worked * i.rate, 0) else 0 end) as hw "
                            + "       from invoice.invoice i "
                            + "              left join invoice.invoice_items ii on i.invoice_id = ii.invoice_id "
                            + "              left join (select invoice_item_id, sum(hours_worked) hours_worked "
                            + "                         from invoice.timesheet "
                            + "                         group by invoice_item_id) as ts "
                            + "                        on ts.invoice_item_id = ii.invoice_item_id "
                            + "       where i.to_date >= (now() - interval '12 month') "
                            + "         and ii.invoice_id is not null "
                            + "       group by i.rate_unit, i.invoice_id, date_part('month',i.to_date)  "
                            + "     ) as _inter "
                            + "group by mth "
                            + "order by total asc "
                            + "limit 1",
            nativeQuery = true)
    List<Object[]> getMinForLast12Mths();

}
