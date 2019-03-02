package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.AttachmentDTO;
import com.bgh.myopeninvoice.api.domain.dto.ReportDTO;
import com.bgh.myopeninvoice.db.domain.AttachmentEntity;
import com.bgh.myopeninvoice.db.domain.ReportEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

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

  @Override
  protected MapperFacade getMapper() {
    factory = mapFields(factory);
    return factory.getMapperFacade();
  }
}
