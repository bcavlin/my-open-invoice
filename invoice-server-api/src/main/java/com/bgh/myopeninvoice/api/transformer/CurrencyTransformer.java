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
public class CurrencyTransformer extends CustomAbstractTransformer<CurrencyEntity, CurrencyDTO> {

    @Override protected BoundMapperFacade<CurrencyEntity, CurrencyDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(CurrencyEntity.class, CurrencyDTO.class);
    }

}