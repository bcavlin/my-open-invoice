package com.bgh.myopeninvoice.common.util;

import com.bgh.myopeninvoice.common.domain.DefaultResponse;
import com.bgh.myopeninvoice.common.domain.OperationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Utils {

  private Utils() {}

  public static <E> List<E> convertIterableToList(Iterable<E> iter) {
    List<E> list = new ArrayList<>();
    if (iter != null) {
      for (E item : iter) {
        list.add(item);
      }
    }
    return list;
  }

  public static <T> ResponseEntity<DefaultResponse<T>> getErrorResponse(
      Class<T> clazz, Exception e) {
    log.error(e.toString(), e);
    @SuppressWarnings("unchecked")
    DefaultResponse<T> defaultResponse = new DefaultResponse<>(clazz);
    defaultResponse.setMessage(e.toString());
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.ERROR);
    defaultResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(defaultResponse, HttpStatus.BAD_REQUEST);
  }

  @SuppressWarnings("unchecked")
  public static <T> ResponseEntity<DefaultResponse<T>> getErrorResponse(
      Class<T> clazz, Throwable e, T element) {
    log.error(e.toString(), e);
    DefaultResponse<T> defaultResponse = new DefaultResponse<>(clazz);
    defaultResponse.setMessage(e.toString());
    if (element instanceof List) {
      defaultResponse.setDetails((List) element);
    } else {
      defaultResponse.setDetails(Collections.singletonList(element));
    }
    defaultResponse.setType(e.getClass().getSimpleName());
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.ERROR);
    defaultResponse.setHttpStatus(HttpStatus.BAD_REQUEST);

    return new ResponseEntity<>(defaultResponse, HttpStatus.BAD_REQUEST);
  }

  @SuppressWarnings("unchecked")
  public static <T> ResponseEntity<DefaultResponse<T>> getErrorResponse(
      Class<T> clazz, Throwable e, T element, HttpStatus status) {
    log.error(e.toString(), e);
    DefaultResponse<T> defaultResponse = new DefaultResponse<>(clazz);
    defaultResponse.setMessage(e.toString());
    if (element instanceof List) {
      defaultResponse.setDetails((List) element);
    } else {
      defaultResponse.setDetails(Collections.singletonList(element));
    }
    defaultResponse.setType(e.getClass().getSimpleName());
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.ERROR);
    defaultResponse.setHttpStatus(status);

    return new ResponseEntity<>(defaultResponse, status);
  }

  public static void addCORSHeaders(HttpServletResponse res) {
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Expose-Headers", "Authorization,Content-Type");
    res.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
    res.setHeader(
        "Access-Control-Allow-Headers",
        "Authorization, x-xsrf-token, "
            + "Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
            + "Content-Type, Access-Control-Request-Method, Custom-Filter-Header");
  }

  public static boolean isDateBetween(LocalDate current, LocalDate from, LocalDate to) {
    return current.isAfter(from) && current.isBefore(to);
  }

  public static LocalDate getFromDateAdjusted(int weekStart, LocalDate fromDate) {
    if (fromDate.getDayOfWeek().getValue() - weekStart < 0) {
      return fromDate.minusWeeks(1).with(ChronoField.DAY_OF_WEEK, weekStart);
    } else {
      return fromDate.with(ChronoField.DAY_OF_WEEK, weekStart);
    }
  }

  public static LocalDate getToDateAdjusted(int weekStart, LocalDate toDate) {
    int weekEnd = weekStart == 1 ? 7 : weekStart - 1;

    if (toDate.getDayOfWeek().getValue() - weekEnd < 0) {
      return toDate.with(ChronoField.DAY_OF_WEEK, weekEnd);
    } else {
      return toDate.plusWeeks(1).with(ChronoField.DAY_OF_WEEK, weekEnd);
    }
  }

  public static Long getFromToDays(LocalDate fromDate, LocalDate toDate) {
    if (fromDate != null && toDate != null) {
      long between = ChronoUnit.DAYS.between(fromDate, toDate);
      return between % 7 != 0 ? between + 1 : between;
    } else {
      return null;
    }
  }
}
