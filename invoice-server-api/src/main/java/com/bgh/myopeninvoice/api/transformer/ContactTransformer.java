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
public class ContactTransformer implements Transformer<ContactEntity, ContactDTO> {

    @Override
    public ContactDTO transformEntityToDTO(ContactEntity entity) {
        return getMapper().map(entity);
    }

    @Override
    public ContactEntity transformDTOToEntity(ContactDTO dto) {
        return getMapper().mapReverse(dto);
    }

    @Override
    public List<ContactDTO> transformEntityToDTO(List<ContactEntity> entity) {
        return entity.stream().map(this::transformEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ContactEntity> transformDTOToEntity(List<ContactDTO> dto) {
        return dto.stream().map(this::transformDTOToEntity).collect(Collectors.toList());
    }

    private BoundMapperFacade<ContactEntity, ContactDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(ContactEntity.class, ContactDTO.class);
    }

}