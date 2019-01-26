package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.ContactDTO;
import com.bgh.myopeninvoice.db.domain.ContactEntity;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactTransformer extends CustomAbstractTransformer<ContactEntity, ContactDTO> {

  @Autowired private CompanyContactTransformer companyContactTransformer;

  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    mapperFactory
        .classMap(ContactDTO.class, ContactEntity.class)
        .field("companyContacts", "companyContactsByContactId")
        .byDefault()
        .register();

    return mapperFactory;
  }

  @Override
  protected MapperFacade getMapper() {
    factory = mapFields(factory);
    factory = companyContactTransformer.mapFields(factory);
    return factory.getMapperFacade();
  }
}
