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
public class TimeSheetTransformer implements Transformer<TimeSheetEntity, TimeSheetDTO> {

    @Override
    public TimeSheetDTO transformEntityToDTO(TimeSheetEntity entity) {
        return getMapper().map(entity);
    }

    @Override
    public TimeSheetEntity transformDTOToEntity(TimeSheetDTO dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<TimeSheetDTO> transformEntityToDTO(List<TimeSheetEntity> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TimeSheetEntity> transformDTOToEntity(List<TimeSheetDTO> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    private BoundMapperFacade<TimeSheetEntity, TimeSheetDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(TimeSheetEntity.class, TimeSheetDTO.class);
    }

}