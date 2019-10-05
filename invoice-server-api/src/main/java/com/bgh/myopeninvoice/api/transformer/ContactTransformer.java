package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.ContactDTO;
import com.bgh.myopeninvoice.db.domain.ContactEntity;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ContactTransformer extends CustomAbstractTransformer<ContactEntity, ContactDTO> {

  @Autowired private CompanyContactTransformer companyContactTransformer;

  @PostConstruct
  @Override
  public void init() {
    log.info("Initializing " + this.getClass().getSimpleName());
    mapperFactory = mapFields(mapperFactory);
    mapperFactory = companyContactTransformer.mapFields(mapperFactory);
    mapperFacade = mapperFactory.getMapperFacade();
  }


  @Override
  public MapperFactory mapFields(MapperFactory mapperFactory) {
    mapperFactory
            .classMap(ContactDTO.class, ContactEntity.class)
            .field("companyContacts", "companyContactsByContactId")
            .byDefault()
            .register();

    return mapperFactory;
  }

}
