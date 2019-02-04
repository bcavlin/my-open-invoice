package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.CompanyEntity;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.QCompanyEntity;
import com.bgh.myopeninvoice.db.repository.CompanyContactRepository;
import com.bgh.myopeninvoice.db.repository.CompanyRepository;
import com.bgh.myopeninvoice.db.repository.ContentRepository;
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
public class CompanyService implements CommonService<CompanyEntity> {

  @Autowired private CompanyRepository companyRepository;

  @Autowired private ContentRepository contentRepository;

  @Autowired private CompanyContactRepository companyContactRepository;

  @Override
  public Predicate getPredicate(SearchParameters searchParameters) {

    if (searchParameters.getFilter() != null) {
      searchParameters
          .getBuilder()
          .andAnyOf(
              QCompanyEntity.companyEntity.companyName.containsIgnoreCase(searchParameters.getFilter()),
              QCompanyEntity.companyEntity.phone1.contains(searchParameters.getFilter()),
              QCompanyEntity.companyEntity.shortName.containsIgnoreCase(searchParameters.getFilter()),
              QCompanyEntity.companyEntity
                  .weekStart
                  .stringValue()
                  .contains(searchParameters.getFilter()),
              QCompanyEntity.companyEntity.businessNumber.containsIgnoreCase(searchParameters.getFilter()),
              QCompanyEntity.companyEntity.companyName.containsIgnoreCase(searchParameters.getFilter()),
              QCompanyEntity.companyEntity
                  .address1
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
      count = companyRepository.count(predicate);
    } else {
      count = companyRepository.count();
    }

    return count;
  }

  @Override
  public List<CompanyEntity> findAll(SearchParameters searchParameters) {
    log.info("findAll");

    List<CompanyEntity> entities;

    Predicate predicate = getPredicate(searchParameters);

    if (searchParameters.getPageRequest() != null) {
      if (predicate != null) {
        entities =
            Utils.convertIterableToList(
                companyRepository.findAll(predicate, searchParameters.getPageRequest()));
      } else {
        entities =
            Utils.convertIterableToList(
                companyRepository.findAll(searchParameters.getPageRequest()));
      }
    } else {
      if (predicate != null) {
        entities = Utils.convertIterableToList(companyRepository.findAll(predicate));
      } else {
        entities = Utils.convertIterableToList(companyRepository.findAll());
      }
    }

    return entities;
  }

  @Override
  public List<CompanyEntity> findById(Integer id) {
    log.info("findById: {}", id);
    List<CompanyEntity> entities = new ArrayList<>();
    Optional<CompanyEntity> byId = companyRepository.findById(id);
    byId.ifPresent(entities::add);
    return entities;
  }

  @Override
  public ContentEntity findContentByParentEntityId(
      Integer id, ContentEntity.ContentEntityTable table) {
    log.info("Find content for company {}", id);
    return contentRepository.findContentByCompanyId(id, table.name());
  }

  @SuppressWarnings("unchecked")
  @Transactional
  @Override
  public List<CompanyEntity> saveContent(Integer id, ContentEntity content)
      throws InvalidDataException {
    log.info("Save content to company {}, file {}", id, content.getFilename());
    List<CompanyEntity> save = new ArrayList<>();

    Optional<CompanyEntity> byId = companyRepository.findById(id);
    if (byId.isPresent()) {
      CompanyEntity companyEntity = byId.get();
      if (companyEntity.getContentByContentId() == null) {
        log.debug("Adding new content");
        companyEntity.setContentByContentId(content);
      } else {
        log.debug("Updating content: {}", companyEntity.getContentByContentId().getContentId());
        companyEntity.getContentByContentId().setDateCreated(content.getDateCreated());
        companyEntity.getContentByContentId().setContentTable(content.getContentTable());
        companyEntity.getContentByContentId().setContent(content.getContent());
        companyEntity.getContentByContentId().setFilename(content.getFilename());
      }
      save.addAll(this.save(companyEntity));
    }

    return save;
  }

  @Transactional
  @Override
  public List<CompanyEntity> save(CompanyEntity entity) throws InvalidDataException {
    log.info("Saving entity");

    List<CompanyEntity> entities = new ArrayList<>();
    if (entity.getCompanyId() != null) {
      // update (we are missing image)
      entity.setContentByContentId(
          contentRepository.findContentByCompanyId(
              entity.getCompanyId(),
              ContentEntity.ContentEntityTable.COMPANY.name().toUpperCase()));
    } else if (entity.getContentByContentId() != null
        && entity.getContentByContentId().getContentId() == null
        && entity.getContentByContentId().getFilename() == null) {
      entity.setContentByContentId(null);
    }

    CompanyEntity saved = companyRepository.save(entity);

    log.info("Saved entity: {}", entity);
    entities.add(saved);
    return entities;
  }

  @Transactional
  @Override
  public void delete(Integer id) {
    log.info("Deleting CompanyDTO with id [{}]", id);
    Assert.notNull(id, "ID cannot be empty when deleting data");
    companyRepository.deleteById(id);
  }
}
