package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.ReportsDTO;
import com.bgh.myopeninvoice.db.domain.ReportsEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class ReportsTransformer extends CustomAbstractTransformer<ReportsEntity, ReportsDTO> {

    @Override protected BoundMapperFacade<ReportsEntity, ReportsDTO> getBoundMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(ReportsEntity.class, ReportsDTO.class);
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