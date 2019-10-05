package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.CompanyContactDTO;
import com.bgh.myopeninvoice.db.domain.CompanyContactEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class CompanyContactTransformer
        extends CustomAbstractTransformer<CompanyContactEntity, CompanyContactDTO> {

  @PostConstruct
  @Override
  public void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    mapperFactory = mapFields(mapperFactory);
    mapperFacade = mapperFactory.getMapperFacade();
  }


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

}
