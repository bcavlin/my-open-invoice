package com.bgh.myopeninvoice.api.transformer;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CustomAbstractTransformer<R, K> implements Transformer<R, K> {

    protected MapperFactory factory;

    protected CustomAbstractTransformer() {
        this.factory = new DefaultMapperFactory.Builder().build();
    }

    @Override
    public K transformEntityToDTO(R entity, Class<K> dto) {
        if (getBoundMapper() != null) {
            return getBoundMapper().map(entity);
        } else {
            return getMapper().map(entity, dto);
        }
    }

    @Override
    public R transformDTOToEntity(K dto, Class<R> entity) {
        if (getBoundMapper() != null) {
            return getBoundMapper().mapReverse(dto);
        } else {
            return getMapper().map(dto, entity);
        }
    }

    @Override
    public List<K> transformEntityToDTO(List<R> entity, Class<K> dto) {
        return entity.stream().map(m -> transformEntityToDTO(m, dto)).collect(Collectors.toList());
    }

    @Override
    public List<R> transformDTOToEntity(List<K> dto, Class<R> entity) {
        return dto.stream().map(m -> transformDTOToEntity(m, entity)).collect(Collectors.toList());
    }

    protected BoundMapperFacade<R, K> getBoundMapper() {
        return null;
    }

    public abstract MapperFactory mapFields(MapperFactory mapperFactory);

    protected abstract MapperFacade getMapper();

}
