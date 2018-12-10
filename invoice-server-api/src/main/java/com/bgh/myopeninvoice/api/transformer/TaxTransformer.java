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
public class TaxTransformer extends CustomAbstractTransformer<TaxEntity, TaxDTO> {

    @Override protected BoundMapperFacade<TaxEntity, TaxDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(TaxEntity.class, TaxDTO.class);
    }

}