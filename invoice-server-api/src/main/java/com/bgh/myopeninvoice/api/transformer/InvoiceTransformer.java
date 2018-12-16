package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.InvoiceDTO;
import com.bgh.myopeninvoice.db.domain.InvoiceEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class InvoiceTransformer extends CustomAbstractTransformer<InvoiceEntity, InvoiceDTO> {

    @Override protected BoundMapperFacade<InvoiceEntity, InvoiceDTO> getBoundMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(InvoiceEntity.class, InvoiceDTO.class);
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