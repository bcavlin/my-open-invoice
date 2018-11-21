package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.AttachmentEntity;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.repository.AttachmentRepository;
import com.bgh.myopeninvoice.db.repository.ContentRepository;
import com.querydsl.core.types.Predicate;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AttachmentService implements CommonService<AttachmentEntity> {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Override
    public Predicate getPredicate(SearchParameters searchParameters) {
        throw new NotImplementedException();
    }

    @Override
    public long count(SearchParameters searchParameters) {
        log.info("count");
        Predicate predicate = getPredicate(searchParameters);
        long count;

        if (predicate != null) {
            count = attachmentRepository.count(predicate);
        } else {
            count = attachmentRepository.count();
        }

        return count;
    }

    @Override
    public List<AttachmentEntity> findAll(SearchParameters searchParameters) {
        log.info("findAll");

        List<AttachmentEntity> entities;

        Predicate predicate = getPredicate(searchParameters);

        if (searchParameters.getPageRequest() != null) {
            if (predicate != null) {
                entities = Utils.convertIterableToList(attachmentRepository.findAll(predicate, searchParameters.getPageRequest()));
            } else {
                entities = Utils.convertIterableToList(attachmentRepository.findAll(searchParameters.getPageRequest()));
            }
        } else {
            if (predicate != null) {
                entities = Utils.convertIterableToList(attachmentRepository.findAll(predicate));
            } else {
                entities = Utils.convertIterableToList(attachmentRepository.findAll());
            }
        }

        return entities;
    }

    @Override
    public List<AttachmentEntity> findById(Integer id) {
        log.info("findById: {}", id);
        List<AttachmentEntity> entities = new ArrayList<>();
        Optional<AttachmentEntity> byId = attachmentRepository.findById(id);
        byId.ifPresent(entities::add);
        return entities;
    }

    @Override
    public ContentEntity findContentByParentEntityId(Integer id, ContentEntity.ContentEntityTable table) {
        log.info("Find content for attachment {}", id);
        return contentRepository.findContentByAttachmentId(id, table.name());
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<AttachmentEntity> saveContent(Integer id, ContentEntity content) {
        log.info("Save content to attachment {}, file {}", id, content.getFilename());
        List<AttachmentEntity> save = null;
        List<AttachmentEntity> entityList = this.findById(id);
        if (entityList.size() == 1) {
            AttachmentEntity attachmentEntity = entityList.get(0);
            attachmentEntity.setContentByContentId(content);
            save = this.save(attachmentEntity);
        }
        return save;
    }

    @Transactional
    @Override
    public List<AttachmentEntity> save(AttachmentEntity entity) {
        log.info("Saving entity");
        List<AttachmentEntity> entities = new ArrayList<>();
        AttachmentEntity saved = attachmentRepository.save(entity);
        log.info("Saved entity: {}", entity);
        entities.add(saved);
        return entities;
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        log.info("Deleting AttachmentDTO with id [{}]", id);
        Assert.notNull(id, "ID cannot be empty when deleting data");
        attachmentRepository.deleteById(id);
    }

}