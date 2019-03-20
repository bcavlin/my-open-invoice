package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.CompanyContactDTO;
import com.bgh.myopeninvoice.db.domain.CompanyContactEntity;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

@Component
public class CompanyContactTransformer
    extends CustomAbstractTransformer<CompanyContactEntity, CompanyContactDTO> {

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    mapperFactory
        .classMap(CompanyContactDTO.class, CompanyContactEntity.class)
        .field("contact", "contactByContactId")
        .field("company", "companyByCompanyId")
        .byDefault()
        .register();
    return mapperFactory;
  }

  @Override
  protected MapperFacade getMapper() {
    factory = this.mapFields(factory);
    return factory.getMapperFacade();
  }
}
