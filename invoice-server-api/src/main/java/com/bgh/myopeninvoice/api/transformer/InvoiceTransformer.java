package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.InvoiceDTO;
import com.bgh.myopeninvoice.db.domain.InvoiceEntity;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceTransformer extends CustomAbstractTransformer<InvoiceEntity, InvoiceDTO> {

  @Autowired private CompanyTransformer companyTransformer;

  @Autowired private CompanyContactTransformer companyContactTransformer;

  @Autowired private ContractTransformer contractTransformer;

  @Autowired private InvoiceItemsTransformer invoiceItemsTransformer;

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    mapperFactory
        .classMap(InvoiceDTO.class, InvoiceEntity.class)
        .field("companyContact", "companyContactByCompanyContactFrom")
        .field("currency", "currencyByCcyId")
        .field("contract", "contractByCompanyContractTo")
        .field("attachmentsSize", "attachmentsByInvoiceIdSize")
        .field("invoiceItemsSize", "invoiceItemsByInvoiceIdSize")
        .field("reportsSize", "reportsByInvoiceIdSize")
        .byDefault()
        .register();

    return mapperFactory;
  }

  @Override
  protected MapperFacade getMapper() {
    factory = mapFields(factory);
    factory = companyTransformer.mapFields(factory);
    factory = companyContactTransformer.mapFields(factory);
    factory = contractTransformer.mapFields(factory);
    factory = invoiceItemsTransformer.mapFields(factory);
    return factory.getMapperFacade();
  }
}
