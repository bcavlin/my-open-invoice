package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.querydsl.core.types.Predicate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommonCRUDService<T> {

  List<T> findAll(SearchParameters searchParameters);

  Predicate getPredicate(SearchParameters searchParameters);

  long count(SearchParameters searchParameters);

  List<T> findById(Integer id);

  @Transactional
  List<T> save(T entity) throws InvalidDataException;

  @Transactional
  void delete(Integer id);

  ContentEntity findContentByParentEntityId(Integer id, ContentEntity.ContentEntityTable table);

  @Transactional
  <T> List<T> saveContent(Integer id, ContentEntity content) throws InvalidDataException;

  void validate(T entity, Action action) throws InvalidDataException;

  /**
   * Action indicates validation path as to what we need to do / validate
   */
  enum Action { C,R,U,D }
}
