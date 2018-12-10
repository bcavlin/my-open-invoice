package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.CompanyContactDTO;
import com.bgh.myopeninvoice.db.domain.CompanyContactEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyContactTransformer extends CustomAbstractTransformer<CompanyContactEntity, CompanyContactDTO> {

    @Override protected BoundMapperFacade<CompanyContactEntity, CompanyContactDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(CompanyContactEntity.class, CompanyContactDTO.class);
    }

}