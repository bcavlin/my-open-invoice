package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.CurrencyAPI;
import com.bgh.myopeninvoice.api.domain.dto.CurrencyDTO;
import com.bgh.myopeninvoice.api.service.CurrencyCRUDService;
import com.bgh.myopeninvoice.api.transformer.CurrencyTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.common.domain.DefaultResponse;
import com.bgh.myopeninvoice.common.domain.OperationResponse;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.common.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.db.domain.CurrencyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class CurrencyController extends AbstractController implements CurrencyAPI {

  private static final String ENTITY_ID_CANNOT_BE_NULL = "entity.id-cannot-be-null";

  @Autowired private CurrencyCRUDService currencyService;

  @Autowired private CurrencyTransformer currencyTransformer;

  @Override
  public ResponseEntity<DefaultResponse<CurrencyDTO>> findAll(
      @RequestParam Map<String, String> queryParameters) {
    List<CurrencyDTO> result = new ArrayList<>();
    long count;

    count = currencyService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
    List<CurrencyEntity> entities =
        currencyService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));
    result = currencyTransformer.transformEntityToDTO(entities, CurrencyDTO.class);

    DefaultResponse<CurrencyDTO> defaultResponse = new DefaultResponse<>(CurrencyDTO.class);
    defaultResponse.setCount(count);
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<CurrencyDTO>> findById(@PathVariable("id") Integer id) {
    List<CurrencyDTO> result = new ArrayList<>();

    Assert.notNull(
        id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
    List<CurrencyEntity> entities = currencyService.findById(id);
    result = currencyTransformer.transformEntityToDTO(entities, CurrencyDTO.class);

    DefaultResponse<CurrencyDTO> defaultResponse = new DefaultResponse<>(CurrencyDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<CurrencyDTO>> save(
      @Valid @NotNull @RequestBody CurrencyDTO currencyDTO, BindingResult bindingResult) {
    List<CurrencyDTO> result = new ArrayList<>();

    if (bindingResult.hasErrors()) {
      String collect =
          bindingResult.getAllErrors().stream()
              .map(Object::toString)
              .collect(Collectors.joining(", "));
      throw new InvalidDataException(collect);
    }

    if (currencyDTO.getCcyId() != null) {
      throw new InvalidDataException(
          getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
    }

    List<CurrencyEntity> entities =
        currencyService.save(
            currencyTransformer.transformDTOToEntity(currencyDTO, CurrencyEntity.class));
    result = currencyTransformer.transformEntityToDTO(entities, CurrencyDTO.class);

    if (CollectionUtils.isEmpty(result)) {
      throw new InvalidResultDataException("Data not saved");
    }

    DefaultResponse<CurrencyDTO> defaultResponse = new DefaultResponse<>(CurrencyDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<CurrencyDTO>> update(
      @Valid @NotNull @RequestBody CurrencyDTO currencyDTO, BindingResult bindingResult) {

    List<CurrencyDTO> result = new ArrayList<>();

    if (bindingResult.hasErrors()) {
      String collect =
          bindingResult.getAllErrors().stream()
              .map(Object::toString)
              .collect(Collectors.joining(", "));
      throw new InvalidDataException(collect);
    }
    if (currencyDTO.getCcyId() == null) {
      throw new InvalidDataException("When updating, data entity must have ID");
    }

    List<CurrencyEntity> entities =
        currencyService.save(
            currencyTransformer.transformDTOToEntity(currencyDTO, CurrencyEntity.class));
    result = currencyTransformer.transformEntityToDTO(entities, CurrencyDTO.class);

    if (CollectionUtils.isEmpty(result)) {
      throw new InvalidResultDataException("Data not saved");
    }

    DefaultResponse<CurrencyDTO> defaultResponse = new DefaultResponse<>(CurrencyDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

    Assert.notNull(
        id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
    currencyService.delete(id);

    DefaultResponse<Boolean> defaultResponse = new DefaultResponse<>(Boolean.class);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    defaultResponse.setMessage("Deleted entity with id: " + id);
    defaultResponse.setDetails(Collections.singletonList(true));
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<byte[]> findContentByCurrencyId(@PathVariable("id") Integer id) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Override
  public ResponseEntity<DefaultResponse<CurrencyDTO>> saveContentByCurrencyId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
    throw new org.apache.commons.lang.NotImplementedException();
  }
}
