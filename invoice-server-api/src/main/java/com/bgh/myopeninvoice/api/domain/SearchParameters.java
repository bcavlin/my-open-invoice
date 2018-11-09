package com.bgh.myopeninvoice.api.domain;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Map;

@Data
public class SearchParameters implements Serializable {

    Map<String, String> filters;

    PageRequest pageRequest;

    Sort sort;

}
