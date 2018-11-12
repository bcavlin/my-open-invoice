package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.QContactEntity;
import com.bgh.myopeninvoice.db.domain.ContactEntity;
import com.bgh.myopeninvoice.db.repository.ContactRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
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
public class ContactService implements CommonService<ContactEntity> {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Predicate getPredicate(SearchParameters searchParameters) {

        BooleanBuilder builder = new BooleanBuilder();

        if (searchParameters.getFilter() != null) {

            builder.andAnyOf(
                    QContactEntity.contactEntity.firstName.contains(searchParameters.getFilter()),
                    QContactEntity.contactEntity.email.contains(searchParameters.getFilter()),
                    QContactEntity.contactEntity.lastName.contains(searchParameters.getFilter()),
                    QContactEntity.contactEntity.phone1.stringValue().contains(searchParameters.getFilter())
            );

        }
        return builder;
    }

    @Override
    public long count(SearchParameters searchParameters) {
        log.info("count");

        Predicate predicate = getPredicate(searchParameters);

        long count;

        if (predicate != null) {
            count = contactRepository.count(predicate);
        } else {
            count = contactRepository.count();
        }

        return count;
    }

    @Override
    public List<ContactEntity> findAll(SearchParameters searchParameters) {
        log.info("findAll");

        List<ContactEntity> entities;

        Predicate predicate = getPredicate(searchParameters);

        if (searchParameters.getPageRequest() != null) {
            if (predicate != null) {
                entities = Utils.convertIterableToList(contactRepository.findAll(predicate, searchParameters.getPageRequest()));
            } else {
                entities = Utils.convertIterableToList(contactRepository.findAll(searchParameters.getPageRequest()));
            }
        } else {
            if (predicate != null) {
                entities = Utils.convertIterableToList(contactRepository.findAll(predicate));
            } else {
                entities = Utils.convertIterableToList(contactRepository.findAll());
            }
        }

        return entities;
    }

    @Override
    public List<ContactEntity> findById(Integer id) {
        log.info("findById: {}", id);

        List<ContactEntity> entities = new ArrayList<>();

        Optional<ContactEntity> byId = contactRepository.findById(id);

        byId.ifPresent(entities::add);

        return entities;
    }

    @Transactional
    @Override
    public List<ContactEntity> save(ContactEntity entity) {
        log.info("Saving entity");

        List<ContactEntity> entities = new ArrayList<>();

        ContactEntity saved = contactRepository.save(entity);

        log.info("Saved entity: {}", entity);

        entities.add(saved);

        return entities;
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        log.info("Deleting ContactEntity with id [{}]", id);

        Assert.notNull(id, "ID cannot be empty when deleting data");

        contactRepository.deleteById(id);
    }

}
