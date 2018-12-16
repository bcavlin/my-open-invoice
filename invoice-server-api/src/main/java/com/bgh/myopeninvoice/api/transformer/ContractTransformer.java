package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.CompanyDTO;
import com.bgh.myopeninvoice.api.domain.dto.ContractDTO;
import com.bgh.myopeninvoice.db.domain.CompanyEntity;
import com.bgh.myopeninvoice.db.domain.ContractEntity;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractTransformer extends CustomAbstractTransformer<ContractEntity, ContractDTO> {

    @Autowired
    private CompanyContactTransformer companyContactTransformer;

    @Autowired
    private CompanyTransformer companyTransformer;

    @Override
    public MapperFactory mapFields(MapperFactory mapperFactory) {
        mapperFactory.classMap(CompanyDTO.class, CompanyEntity.class)
                .field("content", "contentByContentId")
                .field("companyContacts", "companyContactsByCompanyId")
                .byDefault()
                .register();
        return mapperFactory;
    }

    @Override
    protected MapperFacade getMapper() {
        factory = this.mapFields(factory);
        factory = companyTransformer.mapFields(factory);
        factory = companyContactTransformer.mapFields(factory);
        return factory.getMapperFacade();
    }

}