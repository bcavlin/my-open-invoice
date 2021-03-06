package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.QTaxEntity;
import com.bgh.myopeninvoice.db.domain.TaxEntity;
import com.bgh.myopeninvoice.db.repository.TaxRepository;
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
public class TaxCRUDService implements CommonCRUDService<TaxEntity> {

  @Autowired private TaxRepository taxRepository;

  @Override
  public Predicate getPredicate(SearchParameters searchParameters) {

    if (searchParameters.getFilter() != null) {
      searchParameters
          .getBuilder()
          .andAnyOf(
              QTaxEntity.taxEntity.identifier.containsIgnoreCase(searchParameters.getFilter()),
              QTaxEntity.taxEntity.percent.stringValue().contains(searchParameters.getFilter()));
    }
    return searchParameters.getBuilder();
  }

  @Override
  public long count(SearchParameters searchParameters) {
    log.info("count");
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
    log.info("findAll");

    List<TaxEntity> entities;

    Predicate predicate = getPredicate(searchParameters);

    if (searchParameters.getPageRequest() != null) {
      if (predicate != null) {
        entities =
            Utils.convertIterableToList(
                taxRepository.findAll(predicate, searchParameters.getPageRequest()));
      } else {
        entities =
            Utils.convertIterableToList(taxRepository.findAll(searchParameters.getPageRequest()));
      }
    } else {
      if (predicate != null) {
        entities = Utils.convertIterableToList(taxRepository.findAll(predicate));
      } else {
        entities = Utils.convertIterableToList(taxRepository.findAll());
      }
    }

    return entities;
  }

  @Override
  public List<TaxEntity> findById(Integer id) {
    log.info("findById: {}", id);
    List<TaxEntity> entities = new ArrayList<>();
    Optional<TaxEntity> byId = taxRepository.findById(id);
    byId.ifPresent(entities::add);
    return entities;
  }

  @Override
  public ContentEntity findContentByParentEntityId(
      Integer id, ContentEntity.ContentEntityTable table) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  /**
   * No need to use validate here as tax cannot affect any existing invoices (tax is copied)
   * @param entity
   * @param action
   * @return
   * @throws InvalidDataException
   */
  @Override
  public void validate(TaxEntity entity, Action action) throws InvalidDataException {
  }

  @SuppressWarnings("unchecked")
  @Transactional
  @Override
  public List<TaxEntity> saveContent(Integer id, ContentEntity content) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Transactional
  @Override
  public List<TaxEntity> save(TaxEntity entity) {
    log.info("Saving entity");
    List<TaxEntity> entities = new ArrayList<>();
    TaxEntity saved = taxRepository.save(entity);
    log.info("Saved entity: {}", entity);
    entities.add(saved);
    return entities;
  }

  @Transactional
  @Override
  public void delete(Integer id) {
    log.info("Deleting TaxDTO with id [{}]", id);
    Assert.notNull(id, "ID cannot be empty when deleting data");
    taxRepository.deleteById(id);
  }
}
