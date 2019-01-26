package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.InvoiceItemsDTO;
import com.bgh.myopeninvoice.db.domain.InvoiceItemsEntity;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceItemsTransformer
    extends CustomAbstractTransformer<InvoiceItemsEntity, InvoiceItemsDTO> {

  @Autowired private InvoiceTransformer invoiceTransformer;

  @Autowired private TimeSheetTransformer timeSheetTransformer;

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    mapperFactory
        .classMap(InvoiceItemsDTO.class, InvoiceItemsEntity.class)
        .field("invoice", "invoiceByInvoiceId")
        .field("timeSheets", "timeSheetsByInvoiceItemId")
        .byDefault()
        .register();

    return mapperFactory;
  }

  @Override
  protected MapperFacade getMapper() {
    factory = mapFields(factory);
    factory = invoiceTransformer.mapFields(factory);
    factory = timeSheetTransformer.mapFields(factory);
    return factory.getMapperFacade();
  }
}
