package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.InvoiceEntity;
import com.bgh.myopeninvoice.db.domain.QInvoiceEntity;
import com.bgh.myopeninvoice.db.repository.InvoiceRepository;
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
public class InvoiceService implements CommonService<InvoiceEntity> {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Predicate getPredicate(SearchParameters searchParameters) {

        BooleanBuilder builder = new BooleanBuilder();

        if (searchParameters.getFilter() != null) {
            builder.andAnyOf(
                    QInvoiceEntity.invoiceEntity.note.contains(searchParameters.getFilter()),
                    QInvoiceEntity.invoiceEntity.rateUnit.contains(searchParameters.getFilter()),
                    QInvoiceEntity.invoiceEntity.title.contains(searchParameters.getFilter()),
                    QInvoiceEntity.invoiceEntity.rate.stringValue().contains(searchParameters.getFilter()),
                    QInvoiceEntity.invoiceEntity.createdDate.stringValue().contains(searchParameters.getFilter()),
                    QInvoiceEntity.invoiceEntity.dueDate.stringValue().contains(searchParameters.getFilter()),
                    QInvoiceEntity.invoiceEntity.paidDate.stringValue().contains(searchParameters.getFilter()),
                    QInvoiceEntity.invoiceEntity.totalValue.stringValue().contains(searchParameters.getFilter()),
                    QInvoiceEntity.invoiceEntity.totalValue.stringValue().contains(searchParameters.getFilter()),
                    QInvoiceEntity.invoiceEntity.fromDate.stringValue().contains(searchParameters.getFilter())
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
                entities = Utils.convertIterableToList(invoiceRepository.findAll(predicate, searchParameters.getPageRequest()));
            } else {
                entities = Utils.convertIterableToList(invoiceRepository.findAll(searchParameters.getPageRequest()));
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
    public ContentEntity findContentByParentEntityId(Integer id, ContentEntity.ContentEntityTable table) {
        throw new org.apache.commons.lang.NotImplementedException();
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

    @Transactional
    public Integer getNextCounter(){
        return invoiceRepository.getNextSequence();
    }

}
