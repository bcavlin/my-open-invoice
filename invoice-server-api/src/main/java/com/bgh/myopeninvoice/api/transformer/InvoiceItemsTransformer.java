package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.InvoiceItemsDTO;
import com.bgh.myopeninvoice.db.domain.InvoiceItemsEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class InvoiceItemsTransformer
        extends CustomAbstractTransformer<InvoiceItemsEntity, InvoiceItemsDTO> {

  @Autowired private TimesheetTransformer timesheetTransformer;

  @PostConstruct
  @Override
  protected void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    mapperFactory = mapFields(mapperFactory);
    mapperFactory = timesheetTransformer.mapFields(mapperFactory);
    mapperFacade = mapperFactory.getMapperFacade();
  }

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    mapperFactory
            .classMap(InvoiceItemsDTO.class, InvoiceItemsEntity.class)
            .field("timesheetsSize", "timesheetsByInvoiceItemIdSize")
            .byDefault()
            .register();

    return mapperFactory;
  }

}
