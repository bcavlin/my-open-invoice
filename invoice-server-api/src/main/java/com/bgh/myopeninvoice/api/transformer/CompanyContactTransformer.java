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
public class CompanyContactTransformer implements Transformer<CompanyContactEntity, CompanyContactDTO> {

    @Override
    public CompanyContactDTO transformEntityToDTO(CompanyContactEntity entity) {
        return getMapper().map(entity);
    }

    @Override
    public CompanyContactEntity transformDTOToEntity(CompanyContactDTO dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<CompanyContactDTO> transformEntityToDTO(List<CompanyContactEntity> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CompanyContactEntity> transformDTOToEntity(List<CompanyContactDTO> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    private BoundMapperFacade<CompanyContactEntity, CompanyContactDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(CompanyContactEntity.class, CompanyContactDTO.class);
    }

}