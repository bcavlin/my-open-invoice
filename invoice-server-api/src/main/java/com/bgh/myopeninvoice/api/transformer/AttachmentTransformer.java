package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.AttachmentDTO;
import com.bgh.myopeninvoice.db.domain.AttachmentEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttachmentTransformer implements Transformer<AttachmentEntity, AttachmentDTO> {

    @Override
    public AttachmentDTO transformEntityToDTO(AttachmentEntity entity) {
        return getMapper().map(entity);
    }

    @Override
    public AttachmentEntity transformDTOToEntity(AttachmentDTO dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<AttachmentDTO> transformEntityToDTO(List<AttachmentEntity> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AttachmentEntity> transformDTOToEntity(List<AttachmentDTO> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    private BoundMapperFacade<AttachmentEntity, AttachmentDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(AttachmentEntity.class, AttachmentDTO.class);
    }

}