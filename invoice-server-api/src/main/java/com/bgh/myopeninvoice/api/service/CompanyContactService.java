package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.CompanyContactEntity;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.repository.CompanyContactRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.PredicateTemplate;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CompanyContactService implements CommonService<CompanyContactEntity> {

    @Autowired
    private CompanyContactRepository companycontactRepository;

    @Override
    public Predicate getPredicate(SearchParameters searchParameters) {
        return searchParameters.getBuilder();
    }

    @Override
    public long count(SearchParameters searchParameters) {
        log.info("count");
        Predicate predicate = getPredicate(searchParameters);
        long count;

        if (predicate != null) {
            count = companycontactRepository.count(predicate);
        } else {
            count = companycontactRepository.count();
        }

        return count;
    }

    @Override
    public List<CompanyContactEntity> findAll(SearchParameters searchParameters) {
        log.info("findAll");

        List<CompanyContactEntity> entities;

        Predicate predicate = getPredicate(searchParameters);

        if (searchParameters.getPageRequest() != null) {
            if (predicate != null) {
                entities = Utils.convertIterableToList(companycontactRepository.findAll(predicate, searchParameters.getPageRequest()));
            } else {
                entities = Utils.convertIterableToList(companycontactRepository.findAll(searchParameters.getPageRequest()));
            }
        } else {
            if (predicate != null) {
                entities = Utils.convertIterableToList(companycontactRepository.findAll(predicate));
            } else {
                entities = Utils.convertIterableToList(companycontactRepository.findAll());
            }
        }

        return entities;
    }

    @Override
    public List<CompanyContactEntity> findById(Integer id) {
        log.info("findById: {}", id);
        List<CompanyContactEntity> entities = new ArrayList<>();
        Optional<CompanyContactEntity> byId = companycontactRepository.findById(id);
        byId.ifPresent(entities::add);
        return entities;
    }

    @Override
    public ContentEntity findContentByParentEntityId(Integer id, ContentEntity.ContentEntityTable table) {
        throw new org.apache.commons.lang.NotImplementedException();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<CompanyContactEntity> saveContent(Integer id, ContentEntity content) {
        throw new org.apache.commons.lang.NotImplementedException();
    }

    @Transactional
    @Override
    public List<CompanyContactEntity> save(CompanyContactEntity entity) {
        log.info("Saving entity");
        List<CompanyContactEntity> entities = new ArrayList<>();
        CompanyContactEntity saved = companycontactRepository.save(entity);
        log.info("Saved entity: {}", entity);
        entities.add(saved);
        return entities;
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        log.info("Deleting CompanyContactDTO with id [{}]", id);
        Assert.notNull(id, "ID cannot be empty when deleting data");
        companycontactRepository.deleteById(id);
    }

}
