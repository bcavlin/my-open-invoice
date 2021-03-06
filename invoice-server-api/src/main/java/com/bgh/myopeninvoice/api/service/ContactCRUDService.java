package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.db.domain.ContactEntity;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.QCompanyContactEntity;
import com.bgh.myopeninvoice.db.domain.QContactEntity;
import com.bgh.myopeninvoice.db.repository.CompanyContactRepository;
import com.bgh.myopeninvoice.db.repository.ContactRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContactCRUDService implements CommonCRUDService<ContactEntity> {

  @Autowired private ContactRepository contactRepository;

    @Autowired
    private CompanyContactRepository companyContactRepository;

  @Override
  public Predicate getPredicate(SearchParameters searchParameters) {

    if (searchParameters.getFilter() != null) {
      searchParameters
          .getBuilder()
          .andAnyOf(
              QContactEntity.contactEntity.lastName.contains(searchParameters.getFilter()),
              QContactEntity.contactEntity.email.contains(searchParameters.getFilter()),
              QContactEntity.contactEntity.phone1.contains(searchParameters.getFilter()),
              QContactEntity.contactEntity
                  .firstName
                  .stringValue()
                  .contains(searchParameters.getFilter()),
              QContactEntity.contactEntity.address1.contains(searchParameters.getFilter()));
    }
    return searchParameters.getBuilder();
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
        entities =
            Utils.convertIterableToList(
                contactRepository.findAll(predicate, searchParameters.getPageRequest()));
      } else {
        entities =
            Utils.convertIterableToList(
                contactRepository.findAll(searchParameters.getPageRequest()));
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

  @Override
  public ContentEntity findContentByParentEntityId(
      Integer id, ContentEntity.ContentEntityTable table) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @SuppressWarnings("unchecked")
  @Transactional
  @Override
  public List<ContactEntity> saveContent(Integer id, ContentEntity content) {
    throw new org.apache.commons.lang.NotImplementedException();
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
    log.info("Deleting ContactDTO with id [{}]", id);
    Assert.notNull(id, "ID cannot be empty when deleting data");
      ContactEntity entity = new ContactEntity();
      entity.setContactId(id);
      validate(entity, Action.D);
    contactRepository.deleteById(id);
  }

    @Override
    public void validate(ContactEntity entity, Action action) throws InvalidDataException {
        List<String> errors = new ArrayList<>();
        if (Action.D.equals(action)) {
            validateCompanyContact(entity, errors);
        }
        if (CollectionUtils.isNotEmpty(errors)) {
            throw new InvalidDataException("Validation exceptions detected", errors);
        }
    }

    private void validateCompanyContact(ContactEntity entity, List<String> errors) {
        BooleanBuilder b = new BooleanBuilder();
        b.and(QCompanyContactEntity.companyContactEntity.contactId.eq(entity.getContactId()));
        long count = companyContactRepository.count(b);
        if (count > 0) {
            errors.add(
                    String.format(
                            "Contact %d is used in other tables. Please clear contact from other tables first.",
                            entity.getContactId()));
        }
    }
}
