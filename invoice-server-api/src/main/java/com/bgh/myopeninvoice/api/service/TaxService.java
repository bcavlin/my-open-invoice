package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.TaxEntity;
import com.bgh.myopeninvoice.db.repository.TaxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaxService {

    @Autowired
    private TaxRepository taxRepository;

    public long countTaxEntity(SearchParameters searchParameters) {
        return taxRepository.count();
    }

    public List<TaxEntity> findTaxEntity(SearchParameters searchParameters) {
        log.info("findAllTaxEntities");

        List<TaxEntity> taxEntities = null;

        if (searchParameters.getPageRequest() != null) {
            taxEntities = Utils.makeList(taxRepository.findAll(searchParameters.getPageRequest()));
        } else {
            taxEntities = Utils.makeList(taxRepository.findAll());
        }

        return taxEntities;
    }

}
