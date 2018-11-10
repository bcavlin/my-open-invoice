package com.bgh.myopeninvoice.api.domain;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@Data
public class SearchParameters implements Serializable {

    String filter;

    PageRequest pageRequest;

    Sort sort;

}
