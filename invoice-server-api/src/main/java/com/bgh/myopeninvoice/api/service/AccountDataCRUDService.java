package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.db.domain.AccountDataEntity;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.QAccountDataEntity;
import com.bgh.myopeninvoice.db.repository.AccountDataRepository;
import com.querydsl.core.types.Predicate;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountDataCRUDService implements CommonCRUDService<AccountDataEntity> {

  @Autowired
  private AccountDataRepository accountdataRepository;

  @Override
  public Predicate getPredicate(SearchParameters searchParameters) {

    if (searchParameters.getFilter() != null) {
      searchParameters
              .getBuilder()
              .andAnyOf(
                      QAccountDataEntity.accountDataEntity.description.contains(
                              searchParameters.getFilter()),
              QAccountDataEntity.accountDataEntity
                      .itemDate
                      .stringValue()
                      .contains(searchParameters.getFilter()),
              QAccountDataEntity.accountDataEntity
                      .credit
                      .stringValue()
                      .contains(searchParameters.getFilter()),
              QAccountDataEntity.accountDataEntity
                      .debit
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
      count = accountdataRepository.count(predicate);
    } else {
      count = accountdataRepository.count();
    }

    return count;
  }

  @Override
  public List<AccountDataEntity> findAll(SearchParameters searchParameters) {
    log.info("findAll");

    List<AccountDataEntity> entities;

    Predicate predicate = getPredicate(searchParameters);

    if (searchParameters.getPageRequest() != null) {
      if (predicate != null) {
        entities =
                Utils.convertIterableToList(
                        accountdataRepository.findAll(predicate, searchParameters.getPageRequest()));
      } else {
        entities =
                Utils.convertIterableToList(
                        accountdataRepository.findAll(searchParameters.getPageRequest()));
      }
    } else {
      if (predicate != null) {
        entities = Utils.convertIterableToList(accountdataRepository.findAll(predicate));
      } else {
        entities = Utils.convertIterableToList(accountdataRepository.findAll());
      }
    }

    return entities;
  }

  public List<AccountDataEntity> findByAccountId(Integer id) {
    log.info("findByAccountId: {}", id);
    return accountdataRepository.findAllByAccountId(id);
  }

  @Override
  public List<AccountDataEntity> findById(Integer id) {
    log.info("findById: {}", id);
    List<AccountDataEntity> entities = new ArrayList<>();
    Optional<AccountDataEntity> byId = accountdataRepository.findById(id);
    byId.ifPresent(entities::add);
    return entities;
  }

  @Override
  public ContentEntity findContentByParentEntityId(
          Integer id, ContentEntity.ContentEntityTable table) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Override
  public void validate(AccountDataEntity entity, Action action) throws InvalidDataException {
  }

  @SuppressWarnings("unchecked")
  @Transactional
  @Override
  public List<AccountDataEntity> saveContent(Integer id, ContentEntity content) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Transactional
  @Override
  public List<AccountDataEntity> save(AccountDataEntity entity) {
    log.info("Saving entity");
    List<AccountDataEntity> entities = new ArrayList<>();
    AccountDataEntity saved = accountdataRepository.save(entity);
    log.info("Saved entity: {}", entity);
    entities.add(saved);
    return entities;
  }

  @Transactional
  @Override
  public void delete(Integer id) {
    log.info("Deleting AccountDataDTO with id [{}]", id);
    Assert.notNull(id, "ID cannot be empty when deleting data");
    accountdataRepository.deleteById(id);
  }

  @Transactional
  public Integer parse(
          String data, Boolean firstRowHeader, String format, String lineSeparator, Integer provider) {
    assert data != null;
    List<AccountDataEntity> accountDataEntityList = new ArrayList<>();

    if ("CSV".equals(format)) {
      accountDataEntityList = processCSVData(data, firstRowHeader, lineSeparator, provider);
      log.info(accountDataEntityList.toString());
    }

    if (accountDataEntityList.size() > 0) {
      accountdataRepository.saveAll(accountDataEntityList);
    }

    return accountDataEntityList.size();
  }

  private List<AccountDataEntity> processCSVData(
          String data, Boolean firstRowHeader, String lineSeparator, Integer provider) {
    final List<AccountDataEntity> accountDataEntityList = new ArrayList<>();
    String[] rows = new String[0];

    if ("CRLF".equalsIgnoreCase(lineSeparator)) {
      rows = data.split("\n");
    }

    if (rows.length > 0) {
      if (firstRowHeader) {
        rows = (String[]) ArrayUtils.remove(rows, 0);
      }
      List<String> rowsList = Arrays.asList(rows);

      rowsList.forEach(
              i -> {
                String[] col = i.split(",");
                if (col.length == 5) {
                  AccountDataEntity ent = new AccountDataEntity();
                  ent.setAccountId(Integer.valueOf(col[0]));
                  ent.setItemDate(LocalDate.parse(col[1]));
                  ent.setDescription(col[2]);
                  ent.setDebit(new BigDecimal(col[3]));
                  ent.setCredit(new BigDecimal(col[4]));
                  accountDataEntityList.add(ent);
                } else if (col.length == 4 && provider != null) {
                  AccountDataEntity ent = new AccountDataEntity();
                  ent.setAccountId(provider);
                  ent.setItemDate(LocalDate.parse(col[0]));
                  ent.setDescription(col[1]);
                  ent.setDebit(new BigDecimal(col[2]));
                  ent.setCredit(new BigDecimal(col[3]));
                  accountDataEntityList.add(ent);
                }
              });
    }

    return accountDataEntityList;
  }
}