package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.QTaxEntity;
import com.bgh.myopeninvoice.db.domain.TaxEntity;
import com.bgh.myopeninvoice.db.repository.TaxRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaxService implements CommonService<TaxEntity>{

    @Autowired
    private TaxRepository taxRepository;

    @Override
    public long count(SearchParameters searchParameters) {
        Predicate predicate = getPredicate(searchParameters);

        long count;

        if (predicate != null) {
            count = taxRepository.count(predicate);
        } else {
            count = taxRepository.count();
        }

        return count;
    }

    @Override
    public List<TaxEntity> findAll(SearchParameters searchParameters) {
        log.info("findAllTaxEntities");

        List<TaxEntity> taxEntities = null;

        Predicate predicate = getPredicate(searchParameters);

        if (searchParameters.getPageRequest() != null) {
            if (predicate != null) {
                taxEntities = Utils.convertIterableToList(taxRepository.findAll(predicate, searchParameters.getPageRequest()));
            } else {
                taxEntities = Utils.convertIterableToList(taxRepository.findAll(searchParameters.getPageRequest()));
            }
        } else {
            if (predicate != null) {
                taxEntities = Utils.convertIterableToList(taxRepository.findAll(predicate));
            } else {
                taxEntities = Utils.convertIterableToList(taxRepository.findAll());
            }
        }

        return taxEntities;
    }

    @Override
    public Predicate getPredicate(SearchParameters searchParameters) {

        BooleanBuilder builder = new BooleanBuilder();

        if (searchParameters.getFilter() != null) {

            builder.andAnyOf(QTaxEntity.taxEntity.identifier.contains(searchParameters.getFilter()),
                    QTaxEntity.taxEntity.percent.stringValue().contains(searchParameters.getFilter()));

        }
        return builder;
    }

}
