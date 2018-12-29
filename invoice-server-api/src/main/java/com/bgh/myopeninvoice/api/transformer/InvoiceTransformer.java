package com.bgh.myopeninvoice.api.transformer;

import com.bgh.myopeninvoice.api.domain.dto.ContactDTO;
import com.bgh.myopeninvoice.api.domain.dto.InvoiceDTO;
import com.bgh.myopeninvoice.db.domain.ContactEntity;
import com.bgh.myopeninvoice.db.domain.InvoiceEntity;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceTransformer extends CustomAbstractTransformer<InvoiceEntity, InvoiceDTO> {

    @Autowired
    private CompanyTransformer companyTransformer;

    @Autowired
    private CompanyContactTransformer companyContactTransformer;

    @Autowired
    private ContractTransformer contractTransformer;

    @Override
    public MapperFactory mapFields(MapperFactory mapperFactory) {
        mapperFactory.classMap(InvoiceDTO.class, InvoiceEntity.class)
                .field("attachments", "attachmentsByInvoiceId")
                .field("company", "companyByCompanyTo")
                .field("companyContact", "companyContactByCompanyContactFrom")
                .field("currency", "currencyByCcyId")
                .field("contract", "contractByCompanyContractTo")
                .field("invoiceItems", "invoiceItemsByInvoiceId")
                .field("reports", "reportsByInvoiceId")
                .byDefault()
                .register();

        return mapperFactory;
    }

    @Override
    protected MapperFacade getMapper() {
        factory = mapFields(factory);
        factory = companyTransformer.mapFields(factory);
        factory = companyContactTransformer.mapFields(factory);
        factory = contractTransformer.mapFields(factory);
        return factory.getMapperFacade();
    }

}