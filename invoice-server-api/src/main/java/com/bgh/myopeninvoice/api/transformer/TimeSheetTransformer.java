package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.TimeSheetDTO;
import com.bgh.myopeninvoice.db.domain.TimeSheetEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimeSheetTransformer extends CustomAbstractTransformer<TimeSheetEntity, TimeSheetDTO> {

    @Override protected BoundMapperFacade<TimeSheetEntity, TimeSheetDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(TimeSheetEntity.class, TimeSheetDTO.class);
    }

}