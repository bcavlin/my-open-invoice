package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.CompanyDTO;
import com.bgh.myopeninvoice.db.domain.CompanyEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyTransformer implements Transformer<CompanyEntity, CompanyDTO> {

    @Override
    public CompanyDTO transformEntityToDTO(CompanyEntity entity) {
        return getMapper().map(entity);
    }

    @Override
    public CompanyEntity transformDTOToEntity(CompanyDTO dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<CompanyDTO> transformEntityToDTO(List<CompanyEntity> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CompanyEntity> transformDTOToEntity(List<CompanyDTO> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    private BoundMapperFacade<CompanyEntity, CompanyDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(CompanyEntity.class, CompanyDTO.class);
    }

}