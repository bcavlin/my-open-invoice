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
public class UserRoleTransformer implements Transformer<UserRoleEntity, UserRoleDTO> {

    @Override
    public UserRoleDTO transformEntityToDTO(UserRoleEntity entity) {
        return getMapper().map(entity);
    }

    @Override
    public UserRoleEntity transformDTOToEntity(UserRoleDTO dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<UserRoleDTO> transformEntityToDTO(List<UserRoleEntity> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserRoleEntity> transformDTOToEntity(List<UserRoleDTO> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    private BoundMapperFacade<UserRoleEntity, UserRoleDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(UserRoleEntity.class, UserRoleDTO.class);
    }

}