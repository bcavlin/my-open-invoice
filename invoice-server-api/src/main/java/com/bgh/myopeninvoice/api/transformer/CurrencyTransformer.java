package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.CurrencyDTO;
import com.bgh.myopeninvoice.db.domain.CurrencyEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CurrencyTransformer implements Transformer<CurrencyEntity, CurrencyDTO> {

    @Override
    public CurrencyDTO transformEntityToDTO(CurrencyEntity entity) {
        return getMapper().map(entity);
    }

    @Override
    public CurrencyEntity transformDTOToEntity(CurrencyDTO dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<CurrencyDTO> transformEntityToDTO(List<CurrencyEntity> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CurrencyEntity> transformDTOToEntity(List<CurrencyDTO> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    private BoundMapperFacade<CurrencyEntity, CurrencyDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(CurrencyEntity.class, CurrencyDTO.class);
    }

}