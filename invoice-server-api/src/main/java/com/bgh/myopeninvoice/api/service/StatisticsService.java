package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.db.repository.InvoiceMarker;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

  @Autowired
  private InvoiceMarker invoiceMarker;

  public Map getTotalForCurrentYear() {
    Map<StatisticsEnum, Object> result = new HashMap<>();
    int year = LocalDate.now().getYear();
    BigDecimal totalForCurrentYear = invoiceMarker.getTotalPerYear(year);
    if (totalForCurrentYear != null) {
      result.put(StatisticsEnum.TOTAL_CURRENT_YEAR, totalForCurrentYear);
      result.put(StatisticsEnum.YEAR, year);
    }
    return result;
  }

  public Map getTotalForLastYear() {
    Map<StatisticsEnum, Object> result = new HashMap<>();
    int year = LocalDate.now().getYear();
    BigDecimal totalForLastYear = invoiceMarker.getTotalPerYear(year - 1);
    if (totalForLastYear != null) {
      result.put(StatisticsEnum.TOTAL_LAST_YEAR, totalForLastYear);
      result.put(StatisticsEnum.YEAR, year - 1);
    }
    return result;
  }

  public Map getMaxForLast12Mths() {
    Map<StatisticsEnum, Object> result = new HashMap<>();
    List<Object[]> max = invoiceMarker.getMaxForLast12Mths();
    if (CollectionUtils.isNotEmpty(max)) {
      result.put(StatisticsEnum.MAX_LAST_12MTHS_AMOUNT, max.get(0)[0]);
      result.put(StatisticsEnum.MAX_LAST_12MTHS_MONTH, max.get(0)[1]);
    }
    return result;
  }

  public Map getMinForLast12Mths() {
    Map<StatisticsEnum, Object> result = new HashMap<>();
    List<Object[]> min = invoiceMarker.getMinForLast12Mths();
    if (CollectionUtils.isNotEmpty(min)) {
      result.put(StatisticsEnum.MIN_LAST_12MTHS_AMOUNT, min.get(0)[0]);
      result.put(StatisticsEnum.MIN_LAST_12MTHS_MONTH, min.get(0)[1]);
    }
    return result;
  }

  enum StatisticsEnum {
    TOTAL_CURRENT_YEAR,
    TOTAL_LAST_YEAR,
    YEAR,
    MAX_LAST_12MTHS_AMOUNT,
    MAX_LAST_12MTHS_MONTH,
    MIN_LAST_12MTHS_AMOUNT,
    MIN_LAST_12MTHS_MONTH
  }
}
