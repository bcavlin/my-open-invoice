package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.AttachmentDTO;
import com.bgh.myopeninvoice.db.domain.AttachmentEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class AttachmentTransformer
        extends CustomAbstractTransformer<AttachmentEntity, AttachmentDTO> {

  @PostConstruct
  @Override
  public void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    mapperFactory = mapFields(mapperFactory);
    mapperFacade = mapperFactory.getMapperFacade();
  }

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    mapperFactory
            .classMap(AttachmentDTO.class, AttachmentEntity.class)
            .field("content", "contentByContentId")
            .byDefault()
            .register();

    return mapperFactory;
  }

}
