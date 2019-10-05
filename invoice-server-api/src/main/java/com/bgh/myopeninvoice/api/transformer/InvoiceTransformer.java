package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.InvoiceDTO;
import com.bgh.myopeninvoice.db.domain.InvoiceEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class InvoiceTransformer extends CustomAbstractTransformer<InvoiceEntity, InvoiceDTO> {

  @Autowired private CompanyTransformer companyTransformer;

  @Autowired private CompanyContactTransformer companyContactTransformer;

  @Autowired private ContractTransformer contractTransformer;

  @Autowired private InvoiceItemsTransformer invoiceItemsTransformer;

  @PostConstruct
  @Override
  public void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    mapperFactory = mapFields(mapperFactory);
    mapperFactory = companyTransformer.mapFields(mapperFactory);
    mapperFactory = companyContactTransformer.mapFields(mapperFactory);
    mapperFactory = contractTransformer.mapFields(mapperFactory);
    mapperFactory = invoiceItemsTransformer.mapFields(mapperFactory);
    mapperFacade = mapperFactory.getMapperFacade();
  }

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

}
