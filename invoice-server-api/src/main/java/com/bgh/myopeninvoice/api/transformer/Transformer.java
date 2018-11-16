package com.bgh.myopeninvoice.api.transformer;

import java.util.List;

public interface Transformer<F, T> {

    T transformEntityToDTO(F entity);

    F transformDTOToEntity(T dto);

    List<T> transformEntityToDTO(List<F> entity);

    List<F> transformDTOToEntity(List<T> dto);

}
