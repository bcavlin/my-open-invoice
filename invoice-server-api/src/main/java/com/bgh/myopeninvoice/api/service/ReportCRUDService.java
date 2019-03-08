package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.QReportEntity;
import com.bgh.myopeninvoice.db.domain.ReportEntity;
import com.bgh.myopeninvoice.db.repository.ContentRepository;
import com.bgh.myopeninvoice.db.repository.ReportsRepository;
import com.bgh.myopeninvoice.reporting.BIRTReport;
import com.bgh.myopeninvoice.reporting.ReportRunner;
import com.bgh.myopeninvoice.reporting.util.Constants;
import com.querydsl.core.types.Predicate;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

@Slf4j
@Service
public class ReportCRUDService implements CommonCRUDService<ReportEntity> {

  @Autowired private ReportsRepository reportsRepository;

  @Autowired private ContentRepository contentRepository;

  @Autowired private ReportRunner reportRunner;

  @Override
  public Predicate getPredicate(SearchParameters searchParameters) {

    if (searchParameters.getFilter() != null) {
      searchParameters
          .getBuilder()
          .andAnyOf(
              QReportEntity.reportEntity.reportName.contains(searchParameters.getFilter()),
              QReportEntity.reportEntity
                  .dateCreated
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
      count = reportsRepository.count(predicate);
    } else {
      count = reportsRepository.count();
    }

    return count;
  }

  @Override
  public List<ReportEntity> findAll(SearchParameters searchParameters) {
    log.info("findAll");

    List<ReportEntity> entities;

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
  public List<ReportEntity> findById(Integer id) {
    log.info("findById: {}", id);
    List<ReportEntity> entities = new ArrayList<>();
    Optional<ReportEntity> byId = reportsRepository.findById(id);
    byId.ifPresent(entities::add);
    return entities;
  }

  @Override
  public ContentEntity findContentByParentEntityId(
      Integer id, ContentEntity.ContentEntityTable table) {
    log.info("Find content for reports {}", id);
    return contentRepository.findContentByReportsId(id, table.name());
  }

  @Override
  public void validate(ReportEntity entity, Action action) throws InvalidDataException {
  }

  @SuppressWarnings("unchecked")
  @Transactional
  @Override
  public List<ReportEntity> saveContent(Integer id, ContentEntity content) {
    log.info("Save content to reports {}, file {}", id, content.getFilename());
    List<ReportEntity> save = new ArrayList<>();

    Optional<ReportEntity> byId = reportsRepository.findById(id);
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
  public List<ReportEntity> save(ReportEntity entity) {
    log.info("Saving entity");
    List<ReportEntity> entities = new ArrayList<>();

    ClassPathResource report1 = new ClassPathResource(Constants.REPORT_3);
    byte[] bytes = new byte[0];
    try {
      bytes = IOUtils.toByteArray(report1.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }

    Map<String, Object> params = new HashMap<>();
    params.put("p_invoice_id", entity.getInvoiceId());

    final BIRTReport myReport = new BIRTReport(entity.getReportName(), params, bytes, reportRunner);
    final ByteArrayOutputStream reportContent = myReport.runReport().getReportContent();

    ContentEntity contentEntity = new ContentEntity();
    contentEntity.setContent(reportContent.toByteArray());
    contentEntity.setContentTable(ContentEntity.ContentEntityTable.REPORTS.toString());
    contentEntity.setDateCreated(LocalDateTime.now());
    contentEntity.setFilename(entity.getReportName() + ".pdf");

    entity.setContentByContentId(contentEntity);
    entity.setDateCreated(ZonedDateTime.now());

    ReportEntity saved = reportsRepository.save(entity);
    log.info("Saved entity: {}", entity);
    entities.add(saved);
    return entities;
  }

  @Transactional
  @Override
  public void delete(Integer id) {
    log.info("Deleting ReportDTO with id [{}]", id);
    Assert.notNull(id, "ID cannot be empty when deleting data");
    reportsRepository.deleteById(id);
  }
}
