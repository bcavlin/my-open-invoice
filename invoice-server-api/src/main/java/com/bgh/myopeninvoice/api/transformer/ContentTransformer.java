package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.ContentDTO;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContentTransformer extends CustomAbstractTransformer<ContentEntity, ContentDTO> {

    @Override protected BoundMapperFacade<ContentEntity, ContentDTO> getMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade(ContentEntity.class, ContentDTO.class);
    }

}