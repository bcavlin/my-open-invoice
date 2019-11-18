package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.AccountDataDTO;
import com.bgh.myopeninvoice.db.domain.AccountDataEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class AccountDataTransformer extends CustomAbstractTransformer<AccountDataEntity, AccountDataDTO> {


  @PostConstruct
  @Override
  public void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    boundMapperFacade = mapperFactory.getMapperFacade(AccountDataEntity.class, AccountDataDTO.class);
  }

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    return null;
  }

}
