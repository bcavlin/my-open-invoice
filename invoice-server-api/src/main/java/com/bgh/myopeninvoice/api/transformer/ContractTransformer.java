package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.ContractDTO;
import com.bgh.myopeninvoice.db.domain.ContractEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContractTransformer implements Transformer<ContractEntity, ContractDTO> {

    @Override
    public ContractDTO transformEntityToDTO(ContractEntity entity) {
        return getMapper().map(entity);
    }

    @Override
    public ContractEntity transformDTOToEntity(ContractDTO dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<ContractDTO> transformEntityToDTO(List<ContractEntity> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ContractEntity> transformDTOToEntity(List<ContractDTO> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    private BoundMapperFacade<ContractEntity, ContractDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(ContractEntity.class, ContractDTO.class);
    }

}