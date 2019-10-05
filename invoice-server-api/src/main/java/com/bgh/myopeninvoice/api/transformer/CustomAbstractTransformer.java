package com.bgh.myopeninvoice.api.transformer;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class CustomAbstractTransformer<R, K> implements Transformer<R, K> {

  // thread safe instance
  protected MapperFactory mapperFactory;

  protected BoundMapperFacade<R, K> boundMapperFacade;

  protected MapperFacade mapperFacade;

  protected CustomAbstractTransformer() {
    mapperFactory = new DefaultMapperFactory.Builder().build();
  }

  @Override
  public K transformEntityToDTO(R entity, Class<K> dto) {
    if (boundMapperFacade != null) {
      return boundMapperFacade.map(entity);
    } else {
      return mapperFacade.map(entity, dto);
    }
  }

  @Override
  public R transformDTOToEntity(K dto, Class<R> entity) {
    if (boundMapperFacade != null) {
      return boundMapperFacade.mapReverse(dto);
    } else {
      return mapperFacade.map(dto, entity);
    }
  }

  @Override
  public List<K> transformEntityToDTO(List<R> entity, Class<K> dto) {
    return entity.stream().map(m -> transformEntityToDTO(m, dto)).collect(Collectors.toList());
  }

  @Override
  public List<R> transformDTOToEntity(List<K> dto, Class<R> entity) {
    return dto.stream().map(m -> transformDTOToEntity(m, entity)).collect(Collectors.toList());
  }

  /**
   * This is ued to map fields for current AND for other classes. This is why it needs to be public.
   *
   * @param mapperFactory
   * @return
   */
  public abstract MapperFactory mapFields(MapperFactory mapperFactory);

  /**
   * User to @PostConstruct and init mapper to save memory and speed
   */
  protected abstract void init();
}
