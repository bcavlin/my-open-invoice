package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.ContractDTO;
import com.bgh.myopeninvoice.db.domain.ContractEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ContractTransformer extends CustomAbstractTransformer<ContractEntity, ContractDTO> {

  @Autowired private CompanyContactTransformer companyContactTransformer;

  @Autowired private CompanyTransformer companyTransformer;

  @PostConstruct
  @Override
  protected void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    mapperFactory = this.mapFields(mapperFactory);
    mapperFactory = companyTransformer.mapFields(mapperFactory);
    mapperFactory = companyContactTransformer.mapFields(mapperFactory);
    mapperFacade = mapperFactory.getMapperFacade();
  }

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    mapperFactory
            .classMap(ContractDTO.class, ContractEntity.class)
            .field("content", "contentByContentId")
            .field("companyContact", "companyContactByCompanyContactId")
            .field("company", "companyByCompanyId")
            .field("companySubcontract", "companyByCompanyIdSubcontract")
            .field("currency", "currencyByCcyId")
            .field("invoicesSize", "invoicesByContractIdSize")
            .byDefault()
            .register();
    return mapperFactory;
  }

}
