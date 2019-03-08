package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.InvoiceItemsEntity;
import com.bgh.myopeninvoice.db.domain.QTimesheetEntity;
import com.bgh.myopeninvoice.db.domain.TimesheetEntity;
import com.bgh.myopeninvoice.db.repository.InvoiceItemsRepository;
import com.bgh.myopeninvoice.db.repository.TimesheetRepository;
import com.querydsl.core.types.Predicate;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TimesheetCRUDService implements CommonCRUDService<TimesheetEntity> {

  @Autowired private TimesheetRepository timesheetRepository;

  @Autowired private InvoiceItemsRepository invoiceItemsRepository;

  @Override
  public Predicate getPredicate(SearchParameters searchParameters) {

    if (searchParameters.getFilter() != null) {
      searchParameters.getBuilder().andAnyOf(
          QTimesheetEntity.timesheetEntity
              .hoursWorked
              .stringValue()
              .contains(searchParameters.getFilter()),
          QTimesheetEntity.timesheetEntity
              .itemDate
              .stringValue()
              .contains(searchParameters.getFilter()));
    }
    return searchParameters.getBuilder();
  }

  @Override
  public long count(SearchParameters searchParameters) {
    log.info("count");
    Predicate predicate = getPredicate(searchParameters);
    long count;

    if (predicate != null) {
      count = timesheetRepository.count(predicate);
    } else {
      count = timesheetRepository.count();
    }

    return count;
  }

  @Override
  public List<TimesheetEntity> findAll(SearchParameters searchParameters) {
    log.info("findAll");

    List<TimesheetEntity> entities;

    Predicate predicate = getPredicate(searchParameters);

    if (searchParameters.getPageRequest() != null) {
      if (predicate != null) {
        entities =
            Utils.convertIterableToList(
                timesheetRepository.findAll(predicate, searchParameters.getPageRequest()));
      } else {
        entities =
            Utils.convertIterableToList(
                timesheetRepository.findAll(searchParameters.getPageRequest()));
      }
    } else {
      if (predicate != null) {
        entities = Utils.convertIterableToList(timesheetRepository.findAll(predicate));
      } else {
        entities = Utils.convertIterableToList(timesheetRepository.findAll());
      }
    }

    return entities;
  }

  @Override
  public List<TimesheetEntity> findById(Integer id) {
    log.info("findById: {}", id);
    List<TimesheetEntity> entities = new ArrayList<>();
    Optional<TimesheetEntity> byId = timesheetRepository.findById(id);
    byId.ifPresent(entities::add);
    return entities;
  }

  @Override
  public ContentEntity findContentByParentEntityId(
      Integer id, ContentEntity.ContentEntityTable table) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Override
  public void validate(TimesheetEntity entity, Action action) throws InvalidDataException {
  }

  @SuppressWarnings("unchecked")
  @Transactional
  @Override
  public List<TimesheetEntity> saveContent(Integer id, ContentEntity content) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Transactional
  @Override
  public List<TimesheetEntity> save(TimesheetEntity entity) {
    log.info("Saving entity");
    List<TimesheetEntity> entities = new ArrayList<>();
    TimesheetEntity saved = timesheetRepository.save(entity);
    log.info("Saved entity: {}", entity);
    entities.add(saved);
    return entities;
  }

  @Transactional
  @Override
  public void delete(Integer id) {
    log.info("Deleting TimesheetDTO with id [{}]", id);
    Assert.notNull(id, "ID cannot be empty when deleting data");
    timesheetRepository.deleteById(id);
  }

  @Transactional
  public List<TimesheetEntity> saveAll(List<TimesheetEntity> timesheetEntityList) {
    log.info("Saving/deleting timesheetEntityList");
    List<TimesheetEntity> toDelete =
        timesheetEntityList.stream()
            .filter(o -> o.getInvoiceItemId() != null && o.getHoursWorked() == null)
            .collect(Collectors.toList());

    List<TimesheetEntity> toSave =
        timesheetEntityList.stream()
            .filter(o -> o.getHoursWorked() != null)
            .collect(Collectors.toList());

    Iterable<TimesheetEntity> saved = timesheetRepository.saveAll(toSave);

    if (CollectionUtils.isNotEmpty(timesheetEntityList)) {
      //we need to reset ii quantity to 0 as now we have timesheet - both cannot exist
      updateInvoiceItemsQuantityTo0(timesheetEntityList.get(0).getInvoiceItemId());
    }

    if (CollectionUtils.isNotEmpty(toDelete)) {
      for (TimesheetEntity timesheetEntity : toDelete) {
        timesheetRepository.deleteTimesheetEntityByTimesheetId(timesheetEntity.getTimesheetId());
      }
    }

    log.info("Deleted timesheetEntityList: {}", toDelete);
    log.info("Saved timesheetEntityList: {}", saved);
    return Utils.convertIterableToList(saved);
  }

  private void updateInvoiceItemsQuantityTo0(Integer invoiceItemId) {
    Optional<InvoiceItemsEntity> byId = invoiceItemsRepository.findById(invoiceItemId);
    if(byId.isPresent()){
      log.info("Setting invoice item quantity to 0");
      InvoiceItemsEntity invoiceItemsEntity = byId.get();
      invoiceItemsEntity.setQuantity(BigDecimal.valueOf(0));
      invoiceItemsRepository.save(invoiceItemsEntity);
    }
  }

}
