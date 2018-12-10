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
public class InvoiceItemsTransformer extends CustomAbstractTransformer<InvoiceItemsEntity, InvoiceItemsDTO> {

    @Override protected BoundMapperFacade<InvoiceItemsEntity, InvoiceItemsDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(InvoiceItemsEntity.class, InvoiceItemsDTO.class);
    }

}