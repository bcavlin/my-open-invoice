package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.UserRoleDTO;
import com.bgh.myopeninvoice.db.domain.UserRoleEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRoleTransformer extends CustomAbstractTransformer<UserRoleEntity, UserRoleDTO> {

    @Override protected BoundMapperFacade<UserRoleEntity, UserRoleDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(UserRoleEntity.class, UserRoleDTO.class);
    }

}