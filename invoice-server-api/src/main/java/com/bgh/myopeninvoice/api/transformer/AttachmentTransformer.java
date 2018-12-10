package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.AttachmentDTO;
import com.bgh.myopeninvoice.db.domain.AttachmentEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class AttachmentTransformer extends CustomAbstractTransformer<AttachmentEntity, AttachmentDTO> {

    @Override
    protected BoundMapperFacade<AttachmentEntity, AttachmentDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(AttachmentEntity.class, AttachmentDTO.class);
    }

}