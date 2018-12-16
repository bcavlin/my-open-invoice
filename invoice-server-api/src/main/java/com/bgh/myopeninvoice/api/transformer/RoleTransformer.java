package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.RoleDTO;
import com.bgh.myopeninvoice.db.domain.RoleEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class RoleTransformer extends CustomAbstractTransformer<RoleEntity, RoleDTO> {

    @Override protected BoundMapperFacade<RoleEntity, RoleDTO> getBoundMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(RoleEntity.class, RoleDTO.class);
    }

    @Override
    public MapperFactory mapFields(MapperFactory mapperFactory) {
        return null;
    }

    @Override
    protected MapperFacade getMapper() {
        return null;
    }

}