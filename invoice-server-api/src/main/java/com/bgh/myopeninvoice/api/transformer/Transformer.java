package com.bgh.myopeninvoice.api.transformer;

import java.util.List;

public interface Transformer<E, D> {

    D transformEntityToDTO(E entity, Class<D> dto);

    E transformDTOToEntity(D dto, Class<E> entity);

    List<D> transformEntityToDTO(List<E> entity, Class<D> dto);

    List<E> transformDTOToEntity(List<D> dto, Class<E> entity);

}
