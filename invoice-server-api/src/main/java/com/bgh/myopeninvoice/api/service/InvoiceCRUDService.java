package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.db.domain.*;
import com.bgh.myopeninvoice.db.repository.InvoiceRepository;
import com.bgh.myopeninvoice.db.repository.SequenceRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InvoiceCRUDService implements CommonCRUDService<InvoiceEntity> {

  @Autowired private InvoiceRepository invoiceRepository;

  @Autowired
  private SequenceRepository sequenceRepository;

  @Override
  public Predicate getPredicate(SearchParameters searchParameters) {

    if (searchParameters.getFilter() != null) {
      searchParameters
          .getBuilder()
          .andAnyOf(
              QInvoiceEntity.invoiceEntity.note.containsIgnoreCase(searchParameters.getFilter()),
              QInvoiceEntity.invoiceEntity.rateUnit.containsIgnoreCase(
                  searchParameters.getFilter()),
              QInvoiceEntity.invoiceEntity.title.containsIgnoreCase(searchParameters.getFilter()),
              QInvoiceEntity.invoiceEntity
                  .rate
                  .stringValue()
                  .containsIgnoreCase(searchParameters.getFilter()),
              QInvoiceEntity.invoiceEntity
                      .createdAt
                  .stringValue()
                  .containsIgnoreCase(searchParameters.getFilter()),
              QInvoiceEntity.invoiceEntity
                  .dueDate
                  .stringValue()
                  .containsIgnoreCase(searchParameters.getFilter()),
              QInvoiceEntity.invoiceEntity
                  .paidDate
                  .stringValue()
                  .containsIgnoreCase(searchParameters.getFilter()),
              QInvoiceEntity.invoiceEntity
                  .totalValue
                  .stringValue()
                  .containsIgnoreCase(searchParameters.getFilter()),
              QInvoiceEntity.invoiceEntity
                  .totalValue
                  .stringValue()
                  .containsIgnoreCase(searchParameters.getFilter()),
              QInvoiceEntity.invoiceEntity
                  .fromDate
                  .stringValue()
                  .containsIgnoreCase(searchParameters.getFilter()));
    }
    return searchParameters.getBuilder();
  }

  @Override
  public long count(SearchParameters searchParameters) {
    log.info("count");
    Predicate predicate = getPredicate(searchParameters);
    long count;

    if (predicate != null) {
      count = invoiceRepository.count(predicate);
    } else {
      count = invoiceRepository.count();
    }

    return count;
  }

  @Override
  public List<InvoiceEntity> findAll(SearchParameters searchParameters) {
    log.info("findAll");

    List<InvoiceEntity> entities;

    Predicate predicate = getPredicate(searchParameters);

    if (searchParameters.getPageRequest() != null) {
      if (predicate != null) {
        entities =
            Utils.convertIterableToList(
                invoiceRepository.findAll(predicate, searchParameters.getPageRequest()));
      } else {
        entities =
            Utils.convertIterableToList(
                invoiceRepository.findAll(searchParameters.getPageRequest()));
      }
    } else {
      if (predicate != null) {
        entities = Utils.convertIterableToList(invoiceRepository.findAll(predicate));
      } else {
        entities = Utils.convertIterableToList(invoiceRepository.findAll());
      }
    }

    return entities;
  }

  @Override
  public List<InvoiceEntity> findById(Integer id) {
    log.info("findById: {}", id);
    List<InvoiceEntity> entities = new ArrayList<>();
    Optional<InvoiceEntity> byId = invoiceRepository.findById(id);
    byId.ifPresent(entities::add);
    return entities;
  }

  @Override
  public ContentEntity findContentByParentEntityId(
      Integer id, ContentEntity.ContentEntityTable table) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Override
  public void validate(InvoiceEntity entity, Action action) throws InvalidDataException {
  }

  @SuppressWarnings("unchecked")
  @Transactional
  @Override
  public List<InvoiceEntity> saveContent(Integer id, ContentEntity content) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Transactional
  @Override
  public List<InvoiceEntity> save(InvoiceEntity entity) {
    log.info("Saving entity");
    List<InvoiceEntity> entities = new ArrayList<>();
    InvoiceEntity saved = invoiceRepository.save(entity);
    log.info("Saved entity: {}", entity);
    entities.add(saved);
    return entities;
  }

  @Transactional
  @Override
  public void delete(Integer id) {
    log.info("Deleting InvoiceDTO with id [{}]", id);
    Assert.notNull(id, "ID cannot be empty when deleting data");
    invoiceRepository.deleteById(id);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Integer getNextCounter() {
    BooleanBuilder b = new BooleanBuilder();
    b.and(QSequenceEntity.sequenceEntity.sequenceName.eq("INVOICE"));
    Optional<SequenceEntity> entity = sequenceRepository.findOne(b);
    if (entity.isPresent()) {
      SequenceEntity sequenceEntity = entity.get();
      sequenceEntity.setSequenceValue(sequenceEntity.getSequenceValue() + 1);
      SequenceEntity save = sequenceRepository.save(sequenceEntity);
      return save.getSequenceValue();
    }
    return null;
  }
}
