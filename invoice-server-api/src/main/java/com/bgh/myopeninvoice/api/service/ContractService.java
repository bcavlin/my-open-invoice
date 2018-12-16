package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.ContractEntity;
import com.bgh.myopeninvoice.db.domain.QContractEntity;
import com.bgh.myopeninvoice.db.repository.ContentRepository;
import com.bgh.myopeninvoice.db.repository.ContractRepository;
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
public class ContractService implements CommonService<ContractEntity> {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Override
    public Predicate getPredicate(SearchParameters searchParameters) {

        if (searchParameters.getFilter() != null && !searchParameters.getBuilder().hasValue()) {
            searchParameters.getBuilder().andAnyOf(
                    QContractEntity.contractEntity.contractNumber.contains(searchParameters.getFilter()),
                    QContractEntity.contractEntity.description.contains(searchParameters.getFilter()),
                    QContractEntity.contractEntity.purchaseOrder.contains(searchParameters.getFilter()),
                    QContractEntity.contractEntity.rate.stringValue().contains(searchParameters.getFilter()),
                    QContractEntity.contractEntity.rateUnit.contains(searchParameters.getFilter()),
                    QContractEntity.contractEntity.validFrom.stringValue().contains(searchParameters.getFilter()),
                    QContractEntity.contractEntity.validTo.stringValue().contains(searchParameters.getFilter())
            );

        }
        return searchParameters.getBuilder();
    }

    @Override
    public long count(SearchParameters searchParameters) {
        log.info("count");
        Predicate predicate = getPredicate(searchParameters);
        long count;

        if (predicate != null) {
            count = contractRepository.count(predicate);
        } else {
            count = contractRepository.count();
        }

        return count;
    }

    @Override
    public List<ContractEntity> findAll(SearchParameters searchParameters) {
        log.info("findAll");

        List<ContractEntity> entities;

        Predicate predicate = getPredicate(searchParameters);

        if (searchParameters.getPageRequest() != null) {
            if (predicate != null) {
                entities = Utils.convertIterableToList(contractRepository.findAll(predicate, searchParameters.getPageRequest()));
            } else {
                entities = Utils.convertIterableToList(contractRepository.findAll(searchParameters.getPageRequest()));
            }
        } else {
            if (predicate != null) {
                entities = Utils.convertIterableToList(contractRepository.findAll(predicate));
            } else {
                entities = Utils.convertIterableToList(contractRepository.findAll());
            }
        }

        return entities;
    }

    @Override
    public List<ContractEntity> findById(Integer id) {
        log.info("findById: {}", id);
        List<ContractEntity> entities = new ArrayList<>();
        Optional<ContractEntity> byId = contractRepository.findById(id);
        byId.ifPresent(entities::add);
        return entities;
    }

    @Override
    public ContentEntity findContentByParentEntityId(Integer id, ContentEntity.ContentEntityTable table) {
        log.info("Find content for contract {}", id);
        return contentRepository.findContentByContractId(id, table.name());
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<ContractEntity> saveContent(Integer id, ContentEntity content) {
        log.info("Save content to contract {}, file {}", id, content.getFilename());
        List<ContractEntity> save = new ArrayList<>();

        Optional<ContractEntity> byId = contractRepository.findById(id);
        byId.ifPresent(contractEntity -> {
            if (contractEntity.getContentByContentId() == null) {
                log.debug("Adding new content");
                contractEntity.setContentByContentId(content);
            } else {
                log.debug("Updating content: {}", contractEntity.getContentByContentId().getContentId());
                contractEntity.getContentByContentId().setDateCreated(content.getDateCreated());
                contractEntity.getContentByContentId().setContentTable(content.getContentTable());
                contractEntity.getContentByContentId().setContent(content.getContent());
                contractEntity.getContentByContentId().setFilename(content.getFilename());
            }
            save.addAll(this.save(contractEntity));
        });

        return save;
    }

    @Transactional
    @Override
    public List<ContractEntity> save(ContractEntity entity) {
        log.info("Saving entity");
        List<ContractEntity> entities = new ArrayList<>();
        ContractEntity saved = contractRepository.save(entity);
        log.info("Saved entity: {}", entity);
        entities.add(saved);
        return entities;
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        log.info("Deleting ContractDTO with id [{}]", id);
        Assert.notNull(id, "ID cannot be empty when deleting data");
        contractRepository.deleteById(id);
    }

}
