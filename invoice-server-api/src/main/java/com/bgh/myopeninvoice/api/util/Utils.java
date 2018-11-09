package com.bgh.myopeninvoice.api.util;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.domain.SortOrder;
import com.bgh.myopeninvoice.api.exception.InvalidParameterException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {

    public static <E> List<E> makeList(Iterable<E> iter) {
        List<E> list = new ArrayList<E>();
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
            if (queryParameters.get("sortField") != null && queryParameters.get("sortOrder") != null) {
                String sortField = queryParameters.get("sortField");
                SortOrder sortOrder = null;

                if (sortField.length() > 30 || !sortField.matches("[a-zA-Z0-9_]+")) {
                    throw new InvalidParameterException("Invalid parameters 'sortField' or 'sortOrder'. Rule: sortField.length() > 30 || !sortField.matches('[a-zA-Z0-9_]') and sortOrder in (ASC,DESC)");
                }

                if (queryParameters.get("sortOrder") != null) {
                    sortOrder = SortOrder.valueOf(queryParameters.get("sortOrder"));

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

            return searchParameters;
        }

        return null;
    }

}
