package com.bgh.myopeninvoice.api.domain.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DefaultResponse<T> extends OperationResponse {

    private long count;

    private String objectType;

    private List<T> details = new ArrayList<>();

}
