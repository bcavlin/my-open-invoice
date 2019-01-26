package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.QReportsEntity;
import com.bgh.myopeninvoice.db.domain.ReportsEntity;
import com.bgh.myopeninvoice.db.repository.ContentRepository;
import com.bgh.myopeninvoice.db.repository.ReportsRepository;
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
public class ReportsService implements CommonService<ReportsEntity> {

  @Autowired private ReportsRepository reportsRepository;

  @Autowired private ContentRepository contentRepository;

  @Override
  public Predicate getPredicate(SearchParameters searchParameters) {

    BooleanBuilder builder = new BooleanBuilder();

    if (searchParameters.getFilter() != null) {
      builder.andAnyOf(
          QReportsEntity.reportsEntity.reportName.contains(searchParameters.getFilter()),
          QReportsEntity.reportsEntity
              .dateCreated
              .stringValue()
              .contains(searchParameters.getFilter()));
    }
    return builder;
  }

  @Override
  public long count(SearchParameters searchParameters) {
    log.info("count");
    Predicate predicate = getPredicate(searchParameters);
    long count;

    if (predicate != null) {
      count = reportsRepository.count(predicate);
    } else {
      count = reportsRepository.count();
    }

    return count;
  }

  @Override
  public List<ReportsEntity> findAll(SearchParameters searchParameters) {
    log.info("findAll");

    List<ReportsEntity> entities;

    Predicate predicate = getPredicate(searchParameters);

    if (searchParameters.getPageRequest() != null) {
      if (predicate != null) {
        entities =
            Utils.convertIterableToList(
                reportsRepository.findAll(predicate, searchParameters.getPageRequest()));
      } else {
        entities =
            Utils.convertIterableToList(
                reportsRepository.findAll(searchParameters.getPageRequest()));
      }
    } else {
      if (predicate != null) {
        entities = Utils.convertIterableToList(reportsRepository.findAll(predicate));
      } else {
        entities = Utils.convertIterableToList(reportsRepository.findAll());
      }
    }

    return entities;
  }

  @Override
  public List<ReportsEntity> findById(Integer id) {
    log.info("findById: {}", id);
    List<ReportsEntity> entities = new ArrayList<>();
    Optional<ReportsEntity> byId = reportsRepository.findById(id);
    byId.ifPresent(entities::add);
    return entities;
  }

  @Override
  public ContentEntity findContentByParentEntityId(
      Integer id, ContentEntity.ContentEntityTable table) {
    log.info("Find content for reports {}", id);
    return contentRepository.findContentByReportsId(id, table.name());
  }

  @SuppressWarnings("unchecked")
  @Transactional
  @Override
  public List<ReportsEntity> saveContent(Integer id, ContentEntity content) {
    log.info("Save content to reports {}, file {}", id, content.getFilename());
    List<ReportsEntity> save = new ArrayList<>();

    Optional<ReportsEntity> byId = reportsRepository.findById(id);
    byId.ifPresent(
        reportsEntity -> {
          if (reportsEntity.getContentByContentId() == null) {
            log.debug("Adding new content");
            reportsEntity.setContentByContentId(content);
          } else {
            log.debug("Updating content: {}", reportsEntity.getContentByContentId().getContentId());
            reportsEntity.getContentByContentId().setDateCreated(content.getDateCreated());
            reportsEntity.getContentByContentId().setContentTable(content.getContentTable());
            reportsEntity.getContentByContentId().setContent(content.getContent());
            reportsEntity.getContentByContentId().setFilename(content.getFilename());
          }
          save.addAll(this.save(reportsEntity));
        });

    return save;
  }

  @Transactional
  @Override
  public List<ReportsEntity> save(ReportsEntity entity) {
    log.info("Saving entity");
    List<ReportsEntity> entities = new ArrayList<>();
    ReportsEntity saved = reportsRepository.save(entity);
    log.info("Saved entity: {}", entity);
    entities.add(saved);
    return entities;
  }

  @Transactional
  @Override
  public void delete(Integer id) {
    log.info("Deleting ReportsDTO with id [{}]", id);
    Assert.notNull(id, "ID cannot be empty when deleting data");
    reportsRepository.deleteById(id);
  }
}
