package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.TaxDTO;
import com.bgh.myopeninvoice.db.domain.TaxEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class TaxTransformer extends CustomAbstractTransformer<TaxEntity, TaxDTO> {


  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    return null;
  }

  @PostConstruct
  @Override
  protected void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    boundMapperFacade = mapperFactory.getMapperFacade(TaxEntity.class, TaxDTO.class);
  }

}
