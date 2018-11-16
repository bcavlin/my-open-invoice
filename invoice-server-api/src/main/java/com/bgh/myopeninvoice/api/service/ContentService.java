package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.repository.ContentRepository;
import com.querydsl.core.types.Predicate;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContentService implements CommonService<ContentEntity> {

    @Autowired
    private ContentRepository contentRepository;

    @Override
    public Predicate getPredicate(SearchParameters searchParameters) {
        throw new NotImplementedException();
    }

    @Override
    public long count(SearchParameters searchParameters) {
        log.info("count");
        Predicate predicate = getPredicate(searchParameters);
        long count;

        if (predicate != null) {
            count = contentRepository.count(predicate);
        } else {
            count = contentRepository.count();
        }

        return count;
    }

    @Override
    public List<ContentEntity> findAll(SearchParameters searchParameters) {
        log.info("findAll");

        List<ContentEntity> entities;

        Predicate predicate = getPredicate(searchParameters);

        if (searchParameters.getPageRequest() != null) {
            if (predicate != null) {
                entities = Utils.convertIterableToList(contentRepository.findAll(predicate, searchParameters.getPageRequest()));
            } else {
                entities = Utils.convertIterableToList(contentRepository.findAll(searchParameters.getPageRequest()));
            }
        } else {
            if (predicate != null) {
                entities = Utils.convertIterableToList(contentRepository.findAll(predicate));
            } else {
                entities = Utils.convertIterableToList(contentRepository.findAll());
            }
        }

        return entities;
    }

    @Override
    public List<ContentEntity> findById(Integer id) {
        log.info("findById: {}", id);
        List<ContentEntity> entities = new ArrayList<>();
        Optional<ContentEntity> byId = contentRepository.findById(id);
        byId.ifPresent(entities::add);
        return entities;
    }

    @Override
    public ContentEntity findContentByParentEntityId(Integer id, ContentEntity.ContentEntityTable table) {
        throw new NotImplementedException();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<ContentEntity> saveContent(Integer id, ContentEntity content) {
        throw new NotImplementedException();
    }

    @Transactional
    @Override
    public List<ContentEntity> save(ContentEntity entity) {
        log.info("Saving entity");
        List<ContentEntity> entities = new ArrayList<>();
        ContentEntity saved = contentRepository.save(entity);
        log.info("Saved entity: {}", entity);
        entities.add(saved);
        return entities;
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        log.info("Deleting ContentDTO with id [{}]", id);
        Assert.notNull(id, "ID cannot be empty when deleting data");
        contentRepository.deleteById(id);
    }

}
