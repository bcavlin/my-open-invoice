package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.QTimeSheetEntity;
import com.bgh.myopeninvoice.db.domain.TimeSheetEntity;
import com.bgh.myopeninvoice.db.repository.TimeSheetRepository;
import com.querydsl.core.BooleanBuilder;
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
public class TimeSheetService implements CommonService<TimeSheetEntity> {

    @Autowired
    private TimeSheetRepository timesheetRepository;

    @Override
    public Predicate getPredicate(SearchParameters searchParameters) {

        BooleanBuilder builder = new BooleanBuilder();

        if (searchParameters.getFilter() != null) {
            builder.andAnyOf(
                    QTimeSheetEntity.timeSheetEntity.hoursWorked.stringValue().contains(searchParameters.getFilter()),
                    QTimeSheetEntity.timeSheetEntity.itemDate.stringValue().contains(searchParameters.getFilter())
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
            count = timesheetRepository.count(predicate);
        } else {
            count = timesheetRepository.count();
        }

        return count;
    }

    @Override
    public List<TimeSheetEntity> findAll(SearchParameters searchParameters) {
        log.info("findAll");

        List<TimeSheetEntity> entities;

        Predicate predicate = getPredicate(searchParameters);

        if (searchParameters.getPageRequest() != null) {
            if (predicate != null) {
                entities = Utils.convertIterableToList(timesheetRepository.findAll(predicate, searchParameters.getPageRequest()));
            } else {
                entities = Utils.convertIterableToList(timesheetRepository.findAll(searchParameters.getPageRequest()));
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
    public List<TimeSheetEntity> findById(Integer id) {
        log.info("findById: {}", id);
        List<TimeSheetEntity> entities = new ArrayList<>();
        Optional<TimeSheetEntity> byId = timesheetRepository.findById(id);
        byId.ifPresent(entities::add);
        return entities;
    }

    @Override
    public ContentEntity findContentByParentEntityId(Integer id, ContentEntity.ContentEntityTable table) {
        throw new NotImplementedException();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<TimeSheetEntity> saveContent(Integer id, ContentEntity content) {
        throw new NotImplementedException();
    }

    @Transactional
    @Override
    public List<TimeSheetEntity> save(TimeSheetEntity entity) {
        log.info("Saving entity");
        List<TimeSheetEntity> entities = new ArrayList<>();
        TimeSheetEntity saved = timesheetRepository.save(entity);
        log.info("Saved entity: {}", entity);
        entities.add(saved);
        return entities;
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        log.info("Deleting TimeSheetDTO with id [{}]", id);
        Assert.notNull(id, "ID cannot be empty when deleting data");
        timesheetRepository.deleteById(id);
    }

}
