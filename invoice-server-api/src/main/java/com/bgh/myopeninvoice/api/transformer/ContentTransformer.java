package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.ContentDTO;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContentTransformer implements Transformer<ContentEntity, ContentDTO> {

    @Override
    public ContentDTO transformEntityToDTO(ContentEntity entity) {
        return getMapper().map(entity);
    }

    @Override
    public ContentEntity transformDTOToEntity(ContentDTO dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<ContentDTO> transformEntityToDTO(List<ContentEntity> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ContentEntity> transformDTOToEntity(List<ContentDTO> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    private BoundMapperFacade<ContentEntity, ContentDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(ContentEntity.class, ContentDTO.class);
    }

}