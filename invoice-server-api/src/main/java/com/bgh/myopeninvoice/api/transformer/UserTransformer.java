package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.UserDTO;
import com.bgh.myopeninvoice.db.domain.UserEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class UserTransformer extends CustomAbstractTransformer<UserEntity, UserDTO> {

  @Override
  public UserDTO transformEntityToDTO(UserEntity entity, Class<UserDTO> dto) {
    UserDTO userDTO = super.transformEntityToDTO(entity, dto);
    /** remove password when displaying to the front end * */
    userDTO.setPasswordHash(null);
    return userDTO;
  }

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    return null;
  }

  @PostConstruct
  @Override
  public void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    boundMapperFacade = mapperFactory.getMapperFacade(UserEntity.class, UserDTO.class);
  }

}
