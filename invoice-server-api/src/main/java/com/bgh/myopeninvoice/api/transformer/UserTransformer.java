package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.UserDTO;
import com.bgh.myopeninvoice.api.domain.dto.UserRoleDTO;
import com.bgh.myopeninvoice.db.domain.UserEntity;
import com.bgh.myopeninvoice.db.domain.UserRoleEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserTransformer extends CustomAbstractTransformer<UserEntity, UserDTO>{

    @Override
    public UserDTO transformEntityToDTO(UserEntity entity) {
        UserDTO userDTO = super.transformEntityToDTO(entity);
        /** remove password when displaying to the front end **/
        userDTO.setPasswordHash(null);
        return userDTO;
    }

    @Override
    protected BoundMapperFacade<UserEntity, UserDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(UserEntity.class, UserDTO.class);
    }

}