package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.TaxDTO;
import com.bgh.myopeninvoice.db.domain.TaxEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaxTransformer implements Transformer<TaxEntity, TaxDTO> {

    @Override
    public TaxDTO transformEntityToDTO(TaxEntity entity) {
        return getMapper().map(entity);
    }

    @Override
    public TaxEntity transformDTOToEntity(TaxDTO dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<TaxDTO> transformEntityToDTO(List<TaxEntity> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaxEntity> transformDTOToEntity(List<TaxDTO> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    private BoundMapperFacade<TaxEntity, TaxDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(TaxEntity.class, TaxDTO.class);
    }

}