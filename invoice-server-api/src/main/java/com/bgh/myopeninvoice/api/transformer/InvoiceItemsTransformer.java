package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.InvoiceItemsDTO;
import com.bgh.myopeninvoice.db.domain.InvoiceItemsEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceItemsTransformer implements Transformer<InvoiceItemsEntity, InvoiceItemsDTO> {

    @Override
    public InvoiceItemsDTO transformEntityToDTO(InvoiceItemsEntity entity) {
        return getMapper().map(entity);
    }

    @Override
    public InvoiceItemsEntity transformDTOToEntity(InvoiceItemsDTO dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<InvoiceItemsDTO> transformEntityToDTO(List<InvoiceItemsEntity> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceItemsEntity> transformDTOToEntity(List<InvoiceItemsDTO> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    private BoundMapperFacade<InvoiceItemsEntity, InvoiceItemsDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(InvoiceItemsEntity.class, InvoiceItemsDTO.class);
    }

}