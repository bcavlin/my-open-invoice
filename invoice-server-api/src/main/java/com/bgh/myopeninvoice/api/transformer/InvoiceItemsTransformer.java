package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.InvoiceItemsDTO;
import com.bgh.myopeninvoice.db.domain.InvoiceItemsEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class InvoiceItemsTransformer extends CustomAbstractTransformer<InvoiceItemsEntity, InvoiceItemsDTO> {

    @Override protected BoundMapperFacade<InvoiceItemsEntity, InvoiceItemsDTO> getBoundMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(InvoiceItemsEntity.class, InvoiceItemsDTO.class);
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