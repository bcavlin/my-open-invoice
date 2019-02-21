package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.TimesheetDTO;
import com.bgh.myopeninvoice.db.domain.TimesheetEntity;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimesheetTransformer extends CustomAbstractTransformer<TimesheetEntity, TimesheetDTO> {

  @Autowired private InvoiceItemsTransformer invoiceItemsTransformer;

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    mapperFactory
        .classMap(TimesheetDTO.class, TimesheetEntity.class)
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
