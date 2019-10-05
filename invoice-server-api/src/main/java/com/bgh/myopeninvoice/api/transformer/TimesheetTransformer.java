package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.TimesheetDTO;
import com.bgh.myopeninvoice.db.domain.TimesheetEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
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

  @PostConstruct
  @Override
  public void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    mapperFactory = mapFields(mapperFactory);
    mapperFactory = invoiceItemsTransformer.mapFields(mapperFactory);
    mapperFacade = mapperFactory.getMapperFacade();
  }

}
