package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.CompanyContactEntity;
import com.bgh.myopeninvoice.db.domain.CompanyEntity;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.QCompanyEntity;
import com.bgh.myopeninvoice.db.repository.CompanyContactRepository;
import com.bgh.myopeninvoice.db.repository.CompanyRepository;
import com.bgh.myopeninvoice.db.repository.ContentRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CompanyService implements CommonService<CompanyEntity> {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private CompanyContactRepository companyContactRepository;

    @Override
    public Predicate getPredicate(SearchParameters searchParameters) {

        BooleanBuilder builder = new BooleanBuilder();

        if (searchParameters.getFilter() != null) {
            builder.andAnyOf(
                    QCompanyEntity.companyEntity.companyName.contains(searchParameters.getFilter()),
                    QCompanyEntity.companyEntity.phone1.contains(searchParameters.getFilter()),
                    QCompanyEntity.companyEntity.shortName.contains(searchParameters.getFilter()),
                    QCompanyEntity.companyEntity.weekStart.stringValue().contains(searchParameters.getFilter()),
                    QCompanyEntity.companyEntity.businessNumber.contains(searchParameters.getFilter()),
                    QCompanyEntity.companyEntity.companyName.contains(searchParameters.getFilter()),
                    QCompanyEntity.companyEntity.address1.stringValue().contains(searchParameters.getFilter())
            );

        }
        return builder;
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
                entities = Utils.convertIterableToList(companyRepository.findAll(predicate, searchParameters.getPageRequest()));
            } else {
                entities = Utils.convertIterableToList(companyRepository.findAll(searchParameters.getPageRequest()));
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
    public ContentEntity findContentByParentEntityId(Integer id, ContentEntity.ContentEntityTable table) {
        log.info("Find content for company {}", id);
        return contentRepository.findContentByCompanyId(id, table.name());
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<CompanyEntity> saveContent(Integer id, ContentEntity content) throws InvalidDataException {
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

        //delete missing contacts - orphan removal is making problems with update
        removeMissingContacts(entity);

        List<CompanyEntity> entities = new ArrayList<>();
        if (entity.getCompanyId() != null) {
            //update (we are missing image)
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

    private void removeMissingContacts(CompanyEntity entity) throws InvalidDataException {
        if (entity.getCompanyId() != null) {
            log.debug("CompanyId found... checking owned change");
            Optional<CompanyEntity> byId = companyRepository.findById(entity.getCompanyId());
            if (byId.isPresent()) {
                if (!Collections.isEmpty(byId.get().getContractsByCompanyId())
                        && !entity.getOwnedByMe()
                        && byId.get().getOwnedByMe()) {
                    throw new InvalidDataException("To change company from owned to not owned you have to remove contracts!");
                }
                if (entity.getCompanyContactsByCompanyId()!=null &&
                        !entity.getCompanyContactsByCompanyId().equals(byId.get().getCompanyContactsByCompanyId())) {
                    log.debug("Deleting missing data");
                    List<Integer> toDelete = new ArrayList<>();
                    if (!Collections.isEmpty(entity.getCompanyContactsByCompanyId())) {
                        toDelete = entity.getCompanyContactsByCompanyId().stream()
                                .map(CompanyContactEntity::getCompanyContactId).collect(Collectors.toList());
                    }
                    companyContactRepository.deleteAllByCompanyContactIdIsNotInAndCompanyIdEquals(
                            toDelete,
                            entity.getCompanyId()
                    );
                }
            }
        }
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        log.info("Deleting CompanyDTO with id [{}]", id);
        Assert.notNull(id, "ID cannot be empty when deleting data");
        companyRepository.deleteById(id);
    }

}
