package com.bgh.myopeninvoice.db.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InvoiceMarker {

    BigDecimal getTotalPerYear(@Param("_year") int year);

    List<Object[]> getMaxForLast12Mths();

    List<Object[]> getMinForLast12Mths();

}
