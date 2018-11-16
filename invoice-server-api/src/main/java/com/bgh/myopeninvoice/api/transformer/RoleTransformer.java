package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.RoleDTO;
import com.bgh.myopeninvoice.db.domain.RoleEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleTransformer implements Transformer<RoleEntity, RoleDTO> {

    @Override
    public RoleDTO transformEntityToDTO(RoleEntity entity) {
        return getMapper().map(entity);
    }

    @Override
    public RoleEntity transformDTOToEntity(RoleDTO dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<RoleDTO> transformEntityToDTO(List<RoleEntity> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<RoleEntity> transformDTOToEntity(List<RoleDTO> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    private BoundMapperFacade<RoleEntity, RoleDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(RoleEntity.class, RoleDTO.class);
    }

}