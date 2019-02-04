package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.TimeSheetDTO;
import com.bgh.myopeninvoice.db.domain.TimeSheetEntity;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeSheetTransformer extends CustomAbstractTransformer<TimeSheetEntity, TimeSheetDTO> {

  @Autowired private InvoiceItemsTransformer invoiceItemsTransformer;

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    mapperFactory
        .classMap(TimeSheetDTO.class, TimeSheetEntity.class)
        .byDefault()
        .register();

    return mapperFactory;
  }

  @Override
  protected MapperFacade getMapper() {
    factory = mapFields(factory);
    factory = invoiceItemsTransformer.mapFields(factory);
    return factory.getMapperFacade();
  }
}
