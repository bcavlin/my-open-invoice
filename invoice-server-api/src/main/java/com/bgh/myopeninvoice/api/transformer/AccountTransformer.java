package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.AccountDTO;
import com.bgh.myopeninvoice.db.domain.AccountEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class AccountTransformer extends CustomAbstractTransformer<AccountEntity, AccountDTO> {

  @PostConstruct
  @Override
  public void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    boundMapperFacade = mapperFactory.getMapperFacade(AccountEntity.class, AccountDTO.class);
  }

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    return null;
  }

}
