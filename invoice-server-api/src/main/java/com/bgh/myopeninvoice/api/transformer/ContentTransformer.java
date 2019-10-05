package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.ContentDTO;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ContentTransformer extends CustomAbstractTransformer<ContentEntity, ContentDTO> {

  @PostConstruct
  @Override
  public void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    boundMapperFacade = mapperFactory.getMapperFacade(ContentEntity.class, ContentDTO.class);
  }

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    return null;
  }

}
