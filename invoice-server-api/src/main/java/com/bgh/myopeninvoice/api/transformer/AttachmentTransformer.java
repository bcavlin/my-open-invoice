package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.AttachmentDTO;
import com.bgh.myopeninvoice.db.domain.AttachmentEntity;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

@Component
public class AttachmentTransformer
    extends CustomAbstractTransformer<AttachmentEntity, AttachmentDTO> {

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    mapperFactory
        .classMap(AttachmentDTO.class, AttachmentEntity.class)
        .field("content", "contentByContentId")
        .byDefault()
        .register();

    return mapperFactory;
  }

  @Override
  protected MapperFacade getMapper() {
    factory = mapFields(factory);
    return factory.getMapperFacade();
  }
}
