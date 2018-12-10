package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.ContactDTO;
import com.bgh.myopeninvoice.db.domain.ContactEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactTransformer extends CustomAbstractTransformer<ContactEntity, ContactDTO> {

    @Override protected BoundMapperFacade<ContactEntity, ContactDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(ContactEntity.class, ContactDTO.class);
    }

}