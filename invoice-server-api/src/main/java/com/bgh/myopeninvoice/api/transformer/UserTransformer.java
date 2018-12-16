package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.UserDTO;
import com.bgh.myopeninvoice.db.domain.UserEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer extends CustomAbstractTransformer<UserEntity, UserDTO> {

    @Override
    public UserDTO transformEntityToDTO(UserEntity entity, Class<UserDTO> dto) {
        UserDTO userDTO = super.transformEntityToDTO(entity, dto);
        /** remove password when displaying to the front end **/
        userDTO.setPasswordHash(null);
        return userDTO;
    }

    @Override
    public MapperFactory mapFields(MapperFactory mapperFactory) {
        return null;
    }

    @Override
    protected BoundMapperFacade<UserEntity, UserDTO> getBoundMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(UserEntity.class, UserDTO.class);
    }

    @Override
    protected MapperFacade getMapper() {
        return null;
    }

}