package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.InvoiceDTO;
import com.bgh.myopeninvoice.db.domain.InvoiceEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceTransformer implements Transformer<InvoiceEntity, InvoiceDTO> {

    @Override
    public InvoiceDTO transformEntityToDTO(InvoiceEntity entity) {
        return getMapper().map(entity);
    }

    @Override
    public InvoiceEntity transformDTOToEntity(InvoiceDTO dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<InvoiceDTO> transformEntityToDTO(List<InvoiceEntity> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceEntity> transformDTOToEntity(List<InvoiceDTO> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    private BoundMapperFacade<InvoiceEntity, InvoiceDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(InvoiceEntity.class, InvoiceDTO.class);
    }

}