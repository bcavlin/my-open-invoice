package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.UserDTO;
import com.bgh.myopeninvoice.db.domain.UserEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CustomAbstractTransformer<R, K> implements Transformer<R, K>{

    @Override
    public K transformEntityToDTO(R entity) {
        return getMapper().map(entity);
    }

    @Override
    public R transformDTOToEntity(K dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<K> transformEntityToDTO(List<R> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<R> transformDTOToEntity(List<K> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    protected abstract BoundMapperFacade<R, K> getMapper();
}
