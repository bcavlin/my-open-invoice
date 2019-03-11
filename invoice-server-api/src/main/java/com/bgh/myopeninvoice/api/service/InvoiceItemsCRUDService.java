package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.InvoiceItemsEntity;
import com.bgh.myopeninvoice.db.domain.QInvoiceItemsEntity;
import com.bgh.myopeninvoice.db.domain.QTimesheetEntity;
import com.bgh.myopeninvoice.db.repository.InvoiceItemsRepository;
import com.bgh.myopeninvoice.db.repository.TimesheetRepository;
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
public class InvoiceItemsCRUDService implements CommonCRUDService<InvoiceItemsEntity> {

  @Autowired private InvoiceItemsRepository invoiceitemsRepository;

  @Autowired private TimesheetRepository timesheetRepository;

  @Override
  public Predicate getPredicate(SearchParameters searchParameters) {

    if (searchParameters.getFilter() != null) {
      searchParameters
          .getBuilder()
          .andAnyOf(
              QInvoiceItemsEntity.invoiceItemsEntity.code.containsIgnoreCase(
                  searchParameters.getFilter()),
              QInvoiceItemsEntity.invoiceItemsEntity.description.containsIgnoreCase(
                  searchParameters.getFilter()),
              QInvoiceItemsEntity.invoiceItemsEntity.unit.containsIgnoreCase(
                  searchParameters.getFilter()));
    }
    return searchParameters.getBuilder();
  }

  @Override
  public long count(SearchParameters searchParameters) {
    log.info("count");
    Predicate predicate = getPredicate(searchParameters);
    long count;

    if (predicate != null) {
      count = invoiceitemsRepository.count(predicate);
    } else {
      count = invoiceitemsRepository.count();
    }

    return count;
  }

  @Override
  public List<InvoiceItemsEntity> findAll(SearchParameters searchParameters) {
    log.info("findAll");

    List<InvoiceItemsEntity> entities;

    Predicate predicate = getPredicate(searchParameters);

    if (searchParameters.getPageRequest() != null) {
      if (predicate != null) {
        entities =
            Utils.convertIterableToList(
                invoiceitemsRepository.findAll(predicate, searchParameters.getPageRequest()));
      } else {
        entities =
            Utils.convertIterableToList(
                invoiceitemsRepository.findAll(searchParameters.getPageRequest()));
      }
    } else {
      if (predicate != null) {
        entities = Utils.convertIterableToList(invoiceitemsRepository.findAll(predicate));
      } else {
        entities = Utils.convertIterableToList(invoiceitemsRepository.findAll());
      }
    }

    return entities;
  }

  @Override
  public List<InvoiceItemsEntity> findById(Integer id) {
    log.info("findById: {}", id);
    List<InvoiceItemsEntity> entities = new ArrayList<>();
    Optional<InvoiceItemsEntity> byId = invoiceitemsRepository.findById(id);
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
  public List<InvoiceItemsEntity> saveContent(Integer id, ContentEntity content) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Transactional
  @Override
  public List<InvoiceItemsEntity> save(InvoiceItemsEntity entity) {
    log.info("Saving entity");

    validate(entity, Action.C);

    List<InvoiceItemsEntity> entities = new ArrayList<>();
    InvoiceItemsEntity saved = invoiceitemsRepository.save(entity);
    log.info("Saved entity: {}", entity);
    entities.add(saved);
    return entities;
  }

  @Transactional
  @Override
  public void delete(Integer id) {
    log.info("Deleting InvoiceItemsDTO with id [{}]", id);
    Assert.notNull(id, "ID cannot be empty when deleting data");

    InvoiceItemsEntity entity = new InvoiceItemsEntity();
    entity.setInvoiceItemId(id);
    validate(entity, Action.D);

    invoiceitemsRepository.deleteById(id);
  }

  @Override
  public void validate(InvoiceItemsEntity entity, Action action) throws InvalidDataException {
    List<String> errors = new ArrayList<>();
    if (Action.D.equals(action)) {
      validateInvoiceItemIDNotNull(entity, errors);
      validateTimesheetCount(entity, errors);
    }
    if (CollectionUtils.isNotEmpty(errors)) {
      throw new InvalidDataException("Validation exceptions detected", errors);
    }
  }

  private void validateInvoiceItemIDNotNull(InvoiceItemsEntity entity, List<String> errors) {
    if (entity.getInvoiceItemId() == null) {
      errors.add("Delete without ID");
    }
  }

  private void validateTimesheetCount(InvoiceItemsEntity entity, List<String> errors) {
    BooleanBuilder b = new BooleanBuilder();
    b.and(QTimesheetEntity.timesheetEntity.invoiceItemId.eq(entity.getInvoiceItemId()));
    long count = timesheetRepository.count(b);
    if (count > 0) {
      errors.add("Timesheet exists for Invoice Item " + entity.getInvoiceItemId());
    }
  }
}
