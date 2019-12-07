package com.bgh.myopeninvoice.api.util;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.common.exception.InvalidParameterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

  public static SearchParameters mapQueryParametersToSearchParameters(
      Map<String, String> queryParameters) throws InvalidParameterException {
    SearchParameters searchParameters = new SearchParameters();

    if (queryParameters != null) {
      parseSort(queryParameters, searchParameters);

      parsePage(queryParameters, searchParameters);

      parseFilter(queryParameters, searchParameters);
    }

    return searchParameters;
  }

  private static void parseFilter(
      Map<String, String> queryParameters, SearchParameters searchParameters)
      throws InvalidParameterException {
    if (StringUtils.isNotEmpty(queryParameters.get("filter"))) {
      String filter = queryParameters.get("filter");
      if (filter.length() > 100) {
        throw new InvalidParameterException(
            "Invalid parameters: 'filter'. Size cannot be more than 100");
      }

      searchParameters.setFilter(filter);
    }
  }

  private static void parsePage(
      Map<String, String> queryParameters, SearchParameters searchParameters)
      throws InvalidParameterException {
    if (StringUtils.isNotEmpty(queryParameters.get("page"))
            && StringUtils.isNotEmpty(queryParameters.get("size"))) {
      int page = NumberUtils.parseNumber(queryParameters.get("page"), Integer.class);
      int size = NumberUtils.parseNumber(queryParameters.get("size"), Integer.class);
      if (page < 0 || size > 1000) {
        throw new InvalidParameterException(
            "Invalid parameters 'page' or 'size'. Rule: page < 0 || size > 1000: page: "
                + page
                + ", size: "
                + size);
      }

      if (searchParameters.getSort() != null) {
        searchParameters.setPageRequest(PageRequest.of(page, size, searchParameters.getSort()));
      } else {
        searchParameters.setPageRequest(PageRequest.of(page, size));
      }
    }
  }

  private static void parseSort(
      Map<String, String> queryParameters, SearchParameters searchParameters)
      throws InvalidParameterException {
    if (StringUtils.isNotEmpty(queryParameters.get("sort"))) {
      String[] fields = queryParameters.get("sort").trim().split("\\s*,\\s*");
      Sort finalSort = null;
      for (String field : fields) {

        if (!field.matches("^([+-]?)([a-zA-Z0-9_]{1,20})$")) {
          throw new InvalidParameterException(
              "Invalid parameters 'sort'. "
                  + "Rule: field.length() <= 20 and field.matches([+-]?)([a-zA-Z0-9_]{1,20}): "
                  + field);
        }

        finalSort = assignSortField(finalSort, field);
      }

      if (finalSort != null) {
        searchParameters.setSort(finalSort);
      }
    }
  }

  private static Sort assignSortField(Sort finalSort, String field) {
    Sort sort;
    if (field.startsWith("+")) {
      sort = Sort.by(field.substring(1)).ascending();
    } else if (field.startsWith("-")) {
      sort = Sort.by(field.substring(1)).descending();
    } else {
      sort = Sort.by(field);
    }

    finalSort = finalSort == null ? sort : finalSort.and(sort);
    return finalSort;
  }

}
