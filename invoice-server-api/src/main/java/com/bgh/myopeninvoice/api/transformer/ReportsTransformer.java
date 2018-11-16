package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.ReportsDTO;
import com.bgh.myopeninvoice.db.domain.ReportsEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportsTransformer implements Transformer<ReportsEntity, ReportsDTO> {

    @Override
    public ReportsDTO transformEntityToDTO(ReportsEntity entity) {
        return getMapper().map(entity);
    }

    @Override
    public ReportsEntity transformDTOToEntity(ReportsDTO dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<ReportsDTO> transformEntityToDTO(List<ReportsEntity> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReportsEntity> transformDTOToEntity(List<ReportsDTO> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    private BoundMapperFacade<ReportsEntity, ReportsDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(ReportsEntity.class, ReportsDTO.class);
    }

}