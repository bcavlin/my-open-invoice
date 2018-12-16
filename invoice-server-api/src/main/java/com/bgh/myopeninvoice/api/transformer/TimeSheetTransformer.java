package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.TimeSheetDTO;
import com.bgh.myopeninvoice.db.domain.TimeSheetEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class TimeSheetTransformer extends CustomAbstractTransformer<TimeSheetEntity, TimeSheetDTO> {

    @Override protected BoundMapperFacade<TimeSheetEntity, TimeSheetDTO> getBoundMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(TimeSheetEntity.class, TimeSheetDTO.class);
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