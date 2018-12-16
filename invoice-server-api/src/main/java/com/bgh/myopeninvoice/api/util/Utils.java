package com.bgh.myopeninvoice.api.util;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.NumberUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class Utils {

    private Utils() {
    }

    public static <E> List<E> convertIterableToList(Iterable<E> iter) {
        List<E> list = new ArrayList<>();
        if (iter != null) {
            for (E item : iter) {
                list.add(item);
            }
        }
        return list;
    }

    public static SearchParameters mapQueryParametersToSearchParameters(Map<String, String> queryParameters) throws InvalidParameterException {
        SearchParameters searchParameters = new SearchParameters();

        if (queryParameters != null) {
            parseSort(queryParameters, searchParameters);

            parsePage(queryParameters, searchParameters);

            parseFilter(queryParameters, searchParameters);

        }

        return searchParameters;
    }

    private static void parseFilter(Map<String, String> queryParameters, SearchParameters searchParameters) throws InvalidParameterException {
        if (queryParameters.get("filter") != null) {
            String filter = queryParameters.get("filter");
            if (filter.length() > 20) {
                throw new InvalidParameterException("Invalid parameters 'filter. Size cannot be more than 20");
            }

            searchParameters.setFilter(filter);
        }
    }

    private static void parsePage(Map<String, String> queryParameters, SearchParameters searchParameters) throws InvalidParameterException {
        if (queryParameters.get("page") != null && queryParameters.get("size") != null) {
            int page = NumberUtils.parseNumber(queryParameters.get("page"), Integer.class);
            int size = NumberUtils.parseNumber(queryParameters.get("size"), Integer.class);
            if (page < 0 || size > 1000) {
                throw new InvalidParameterException("Invalid parameters 'page' or 'size'. Rule: page < 0 || size > 1000");
            }

            if (searchParameters.getSort() != null) {
                searchParameters.setPageRequest(PageRequest.of(page, size, searchParameters.getSort()));
            } else {
                searchParameters.setPageRequest(PageRequest.of(page, size));
            }

        }
    }

    private static void parseSort(Map<String, String> queryParameters, SearchParameters searchParameters) throws InvalidParameterException {
        if (queryParameters.get("sort") != null) {
            String[] fields = queryParameters.get("sort").trim().split("\\s*,\\s*");
            Sort finalSort = null;
            for (String field : fields) {

                if (!field.matches("^([+-]?)([a-zA-Z0-9_]{1,20})$")) {
                    throw new InvalidParameterException("Invalid parameters 'sort'. " +
                            "Rule: field.length() <= 20 and field.matches([+-]?)([a-zA-Z0-9_]{1,20})");
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

    public static <T> ResponseEntity<DefaultResponse<T>> getErrorResponse(Class<T> clazz, Exception e) {
        log.error(e.toString(), e);
        @SuppressWarnings("unchecked") DefaultResponse<T> defaultResponse = new DefaultResponse<>(clazz);
        defaultResponse.setOperationMessage(e.toString());
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.ERROR);
        return new ResponseEntity<>(defaultResponse, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<DefaultResponse<T>> getErrorResponse(Class<T> clazz, Exception e, T element) {
        log.error(e.toString(), e);
        @SuppressWarnings("unchecked") DefaultResponse<T> defaultResponse = new DefaultResponse<>(clazz);
        defaultResponse.setOperationMessage(e.toString());
        defaultResponse.setDetails(Collections.singletonList(element));
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.ERROR);
        return new ResponseEntity<>(defaultResponse, HttpStatus.BAD_REQUEST);
    }

    public static void addCorsHeaders(HttpServletResponse res){
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Expose-Headers", "Authorization,Content-Type");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, " +
                "Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Custom-Filter-Header");
    }
}
