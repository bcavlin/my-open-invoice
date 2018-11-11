package com.bgh.myopeninvoice.api.util;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.domain.SortOrder;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.NumberUtils;

import java.util.*;

@Slf4j
public class Utils {

    private static final String SORT_ORDER = "sortOrder";

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

            return searchParameters;
        }

        return null;
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
        if (queryParameters.get("sortField") != null && queryParameters.get(SORT_ORDER) != null) {
            String sortField = queryParameters.get("sortField");
            SortOrder sortOrder;

            if (sortField.length() > 30 || !sortField.matches("[a-zA-Z0-9_]+")) {
                throw new InvalidParameterException("Invalid parameters 'sortField' or 'sortOrder'. Rule: sortField.length() > 30 || !sortField.matches('[a-zA-Z0-9_]') and sortOrder in (ASC,DESC)");
            }

            if (queryParameters.get(SORT_ORDER) != null) {
                sortOrder = SortOrder.valueOf(queryParameters.get(SORT_ORDER));

                if (sortOrder.equals(SortOrder.ASC)) {
                    searchParameters.setSort(Sort.by(sortField).ascending());
                } else if (sortOrder.equals(SortOrder.DESC)) {
                    searchParameters.setSort(Sort.by(sortField).descending());
                } else {
                    searchParameters.setSort(Sort.by(sortField));
                }
            } else {
                searchParameters.setSort(Sort.by(sortField));
            }
        }
    }

    public static <T> ResponseEntity<DefaultResponse<T>> getErrorResponse(Class<T> clazz, Exception e) {
        log.error(e.toString(), e);
        @SuppressWarnings("unchecked") DefaultResponse<T> defaultResponse = new DefaultResponse<>(clazz);
        defaultResponse.setOperationMessage(e.toString());
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.ERROR);
        return new ResponseEntity<DefaultResponse<T>>(defaultResponse, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<DefaultResponse<T>> getErrorResponse(Class<T> clazz, Exception e, T element) {
        log.error(e.toString(), e);
        @SuppressWarnings("unchecked") DefaultResponse<T> defaultResponse = new DefaultResponse<>(clazz);
        defaultResponse.setOperationMessage(e.toString());
        defaultResponse.setDetails(Collections.singletonList(element));
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.ERROR);
        return new ResponseEntity<DefaultResponse<T>>(defaultResponse, HttpStatus.BAD_REQUEST);
    }

}
