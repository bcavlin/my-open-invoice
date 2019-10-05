package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.CompanyDTO;
import com.bgh.myopeninvoice.db.domain.CompanyEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class CompanyTransformer extends CustomAbstractTransformer<CompanyEntity, CompanyDTO> {

  @Autowired private CompanyContactTransformer companyContactTransformer;

  @PostConstruct
  @Override
  public void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    mapperFactory = this.mapFields(mapperFactory);
    mapperFactory = companyContactTransformer.mapFields(mapperFactory);
    mapperFacade = mapperFactory.getMapperFacade();
  }

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    mapperFactory
            .classMap(CompanyDTO.class, CompanyEntity.class)
            .field("content", "contentByContentId")
            .field("companyContacts", "companyContactsByCompanyId")
            .byDefault()
            .register();
    return mapperFactory;
  }

}
