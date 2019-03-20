package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.CurrencyEntity;
import com.bgh.myopeninvoice.db.domain.QCurrencyEntity;
import com.bgh.myopeninvoice.db.repository.CurrencyRepository;
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
public class CurrencyCRUDService implements CommonCRUDService<CurrencyEntity> {

  @Autowired private CurrencyRepository currencyRepository;

  @Override
  public Predicate getPredicate(SearchParameters searchParameters) {

    if (searchParameters.getFilter() != null) {
      searchParameters
          .getBuilder()
          .andAnyOf(
              QCurrencyEntity.currencyEntity.description.contains(searchParameters.getFilter()),
              QCurrencyEntity.currencyEntity.name.contains(searchParameters.getFilter()));
    }
    return searchParameters.getBuilder();
  }

  @Override
  public long count(SearchParameters searchParameters) {
    log.info("count");
    Predicate predicate = getPredicate(searchParameters);
    long count;

    if (predicate != null) {
      count = currencyRepository.count(predicate);
    } else {
      count = currencyRepository.count();
    }

    return count;
  }

  @Override
  public List<CurrencyEntity> findAll(SearchParameters searchParameters) {
    log.info("findAll");

    List<CurrencyEntity> entities;

    Predicate predicate = getPredicate(searchParameters);

    if (searchParameters.getPageRequest() != null) {
      if (predicate != null) {
        entities =
            Utils.convertIterableToList(
                currencyRepository.findAll(predicate, searchParameters.getPageRequest()));
      } else {
        entities =
            Utils.convertIterableToList(
                currencyRepository.findAll(searchParameters.getPageRequest()));
      }
    } else {
      if (predicate != null) {
        entities = Utils.convertIterableToList(currencyRepository.findAll(predicate));
      } else {
        entities = Utils.convertIterableToList(currencyRepository.findAll());
      }
    }

    return entities;
  }

  @Override
  public List<CurrencyEntity> findById(Integer id) {
    log.info("findById: {}", id);
    List<CurrencyEntity> entities = new ArrayList<>();
    Optional<CurrencyEntity> byId = currencyRepository.findById(id);
    byId.ifPresent(entities::add);
    return entities;
  }

  @Override
  public ContentEntity findContentByParentEntityId(
      Integer id, ContentEntity.ContentEntityTable table) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Override
  public void validate(CurrencyEntity entity, Action action) throws InvalidDataException {
  }

  @SuppressWarnings("unchecked")
  @Transactional
  @Override
  public List<CurrencyEntity> saveContent(Integer id, ContentEntity content) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Transactional
  @Override
  public List<CurrencyEntity> save(CurrencyEntity entity) {
    log.info("Saving entity");
    List<CurrencyEntity> entities = new ArrayList<>();
    CurrencyEntity saved = currencyRepository.save(entity);
    log.info("Saved entity: {}", entity);
    entities.add(saved);
    return entities;
  }

  @Transactional
  @Override
  public void delete(Integer id) {
    log.info("Deleting CurrencyDTO with id [{}]", id);
    Assert.notNull(id, "ID cannot be empty when deleting data");
    currencyRepository.deleteById(id);
  }
}
