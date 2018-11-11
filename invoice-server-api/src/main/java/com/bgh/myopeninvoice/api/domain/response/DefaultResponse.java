package com.bgh.myopeninvoice.api.domain.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DefaultResponse<T> extends OperationResponse {

    @SuppressWarnings("unchecked")
    public DefaultResponse(Class<T> objectType) {
        this.objectType = objectType.getSimpleName();
    }

    private Long count;

    @Setter(AccessLevel.NONE)
    private final String objectType;

    private List<T> details;

}
