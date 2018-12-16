package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.CurrencyDTO;
import com.bgh.myopeninvoice.db.domain.CurrencyEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class CurrencyTransformer extends CustomAbstractTransformer<CurrencyEntity, CurrencyDTO> {

    @Override protected BoundMapperFacade<CurrencyEntity, CurrencyDTO> getBoundMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(CurrencyEntity.class, CurrencyDTO.class);
    }

    @Override
    public MapperFactory mapFields(MapperFactory mapperFactory) {
        return null;
    }

    @Override
    protected MapperFacade getMapper() {
        return null;
    }

}