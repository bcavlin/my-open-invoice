package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.UserRoleDTO;
import com.bgh.myopeninvoice.db.domain.UserRoleEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class UserRoleTransformer extends CustomAbstractTransformer<UserRoleEntity, UserRoleDTO> {

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    return null;
  }

  @PostConstruct
  @Override
  public void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    boundMapperFacade = mapperFactory.getMapperFacade(UserRoleEntity.class, UserRoleDTO.class);
  }

}
