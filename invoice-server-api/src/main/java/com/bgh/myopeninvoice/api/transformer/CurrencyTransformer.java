package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.CurrencyDTO;
import com.bgh.myopeninvoice.db.domain.CurrencyEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CurrencyTransformer extends CustomAbstractTransformer<CurrencyEntity, CurrencyDTO> {

  @Override
  protected void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    boundMapperFacade = mapperFactory.getMapperFacade(CurrencyEntity.class, CurrencyDTO.class);
  }

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    return null;
  }

}
