package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.ReportDTO;
import com.bgh.myopeninvoice.db.domain.ReportEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ReportTransformer extends CustomAbstractTransformer<ReportEntity, ReportDTO> {

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    mapperFactory
            .classMap(ReportDTO.class, ReportEntity.class)
            .field("content", "contentByContentId")
            .byDefault()
            .register();

    return mapperFactory;
  }

  @PostConstruct
  @Override
  protected void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    mapperFactory = mapFields(mapperFactory);
    mapperFacade = mapperFactory.getMapperFacade();
  }

}
