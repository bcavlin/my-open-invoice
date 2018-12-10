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
public class InvoiceTransformer extends CustomAbstractTransformer<InvoiceEntity, InvoiceDTO> {

    @Override protected BoundMapperFacade<InvoiceEntity, InvoiceDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(InvoiceEntity.class, InvoiceDTO.class);
    }

}