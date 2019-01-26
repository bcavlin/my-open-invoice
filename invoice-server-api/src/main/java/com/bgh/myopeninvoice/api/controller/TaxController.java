package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.TaxAPI;
import com.bgh.myopeninvoice.api.domain.dto.TaxDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.api.service.TaxService;
import com.bgh.myopeninvoice.api.transformer.TaxTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.TaxEntity;
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
public class TaxController extends AbstractController implements TaxAPI {

  private static final String ENTITY_ID_CANNOT_BE_NULL = "entity.id-cannot-be-null";

  @Autowired private TaxService taxService;

  @Autowired private TaxTransformer taxTransformer;

  @Override
  public ResponseEntity<DefaultResponse<TaxDTO>> findAll(
      @RequestParam Map<String, String> queryParameters) {
    List<TaxDTO> result = new ArrayList<>();
    long count;

    try {
      count = taxService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
      List<TaxEntity> entities =
          taxService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));
      result = taxTransformer.transformEntityToDTO(entities, TaxDTO.class);

    } catch (Exception e) {
      return Utils.getErrorResponse(TaxDTO.class, e);
    }

    DefaultResponse<TaxDTO> defaultResponse = new DefaultResponse<>(TaxDTO.class);
    defaultResponse.setCount(count);
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<TaxDTO>> findById(@PathVariable("id") Integer id) {
    List<TaxDTO> result = new ArrayList<>();

    try {
      Assert.notNull(
          id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
      List<TaxEntity> entities = taxService.findById(id);
      result = taxTransformer.transformEntityToDTO(entities, TaxDTO.class);

    } catch (Exception e) {
      return Utils.getErrorResponse(TaxDTO.class, e);
    }

    DefaultResponse<TaxDTO> defaultResponse = new DefaultResponse<>(TaxDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<TaxDTO>> save(
      @Valid @NotNull @RequestBody TaxDTO taxDTO, BindingResult bindingResult) {
    List<TaxDTO> result = new ArrayList<>();

    try {

      if (bindingResult.hasErrors()) {
        String collect =
            bindingResult.getAllErrors().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        throw new InvalidDataException(collect);
      }

      if (taxDTO.getTaxId() != null) {
        throw new InvalidDataException(
            getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
      }

      List<TaxEntity> entities =
          taxService.save(taxTransformer.transformDTOToEntity(taxDTO, TaxEntity.class));
      result = taxTransformer.transformEntityToDTO(entities, TaxDTO.class);

      if (CollectionUtils.isEmpty(result)) {
        throw new InvalidResultDataException("Data not saved");
      }

    } catch (Exception e) {
      return Utils.getErrorResponse(TaxDTO.class, e);
    }

    DefaultResponse<TaxDTO> defaultResponse = new DefaultResponse<>(TaxDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<TaxDTO>> update(
      @Valid @NotNull @RequestBody TaxDTO taxDTO, BindingResult bindingResult) {

    List<TaxDTO> result = new ArrayList<>();

    try {

      if (bindingResult.hasErrors()) {
        String collect =
            bindingResult.getAllErrors().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        throw new InvalidDataException(collect);
      }
      if (taxDTO.getTaxId() == null) {
        throw new InvalidDataException("When updating, data entity must have ID");
      }

      List<TaxEntity> entities =
          taxService.save(taxTransformer.transformDTOToEntity(taxDTO, TaxEntity.class));
      result = taxTransformer.transformEntityToDTO(entities, TaxDTO.class);

      if (CollectionUtils.isEmpty(result)) {
        throw new InvalidResultDataException("Data not saved");
      }

    } catch (Exception e) {
      return Utils.getErrorResponse(TaxDTO.class, e);
    }

    DefaultResponse<TaxDTO> defaultResponse = new DefaultResponse<>(TaxDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

    try {
      Assert.notNull(
          id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
      taxService.delete(id);

    } catch (Exception e) {
      return Utils.getErrorResponse(Boolean.class, e, false);
    }

    DefaultResponse<Boolean> defaultResponse = new DefaultResponse<>(Boolean.class);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    defaultResponse.setOperationMessage("Deleted entity with id: " + id);
    defaultResponse.setDetails(Collections.singletonList(true));
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<byte[]> findContentByTaxId(@PathVariable("id") Integer id) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Override
  public ResponseEntity<DefaultResponse<TaxDTO>> saveContentByTaxId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
    throw new org.apache.commons.lang.NotImplementedException();
  }
}
