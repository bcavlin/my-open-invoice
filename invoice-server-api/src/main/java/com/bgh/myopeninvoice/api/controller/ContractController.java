package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.ContractAPI;
import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.domain.dto.ContractDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.api.service.ContractService;
import com.bgh.myopeninvoice.api.transformer.ContractTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.ContractEntity;
import com.bgh.myopeninvoice.db.domain.QContractEntity;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ContractController extends AbstractController implements ContractAPI {

  @Autowired private ContractService contractService;

  @Autowired private ContractTransformer contractTransformer;

  @Override
  public ResponseEntity<DefaultResponse<ContractDTO>> findAll(
      @RequestParam Map<String, String> queryParameters) {
    List<ContractDTO> result = new ArrayList<>();
    long count;

    try {
      SearchParameters searchParameters =
          Utils.mapQueryParametersToSearchParameters(queryParameters);
      validateSpecialFilter(queryParameters, searchParameters);
      count = contractService.count(searchParameters);
      List<ContractEntity> entities = contractService.findAll(searchParameters);
      result = contractTransformer.transformEntityToDTO(entities, ContractDTO.class);

    } catch (Exception e) {
      return Utils.getErrorResponse(ContractDTO.class, e);
    }

    DefaultResponse<ContractDTO> defaultResponse = new DefaultResponse<>(ContractDTO.class);
    defaultResponse.setCount(count);
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  protected void validateSpecialFilter(
      @RequestParam Map<String, String> queryParameters, SearchParameters searchParameters) {
    if (StringUtils.isNotEmpty(queryParameters.get(FILTER))) {
      Matcher matcher = patternFields.matcher(queryParameters.get(FILTER));
      BooleanBuilder builder = searchParameters.getBuilder();

      while (matcher.find()) {
        String[] split = matcher.group(1).split(":");
        if ("companyId".equalsIgnoreCase(split[0])) {
          searchParameters
              .getBuilder()
              .and(QContractEntity.contractEntity.companyId.eq(NumberUtils.toInt(split[1])));
        }
      }

      if (searchParameters.getBuilder().hasValue()) {
        // reset the filter
        searchParameters.setFilter(null);
      }
    }
  }

  @Override
  public ResponseEntity<DefaultResponse<ContractDTO>> findById(@PathVariable("id") Integer id) {
    List<ContractDTO> result = new ArrayList<>();

    try {
      Assert.notNull(
          id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
      List<ContractEntity> entities = contractService.findById(id);
      result = contractTransformer.transformEntityToDTO(entities, ContractDTO.class);

    } catch (Exception e) {
      return Utils.getErrorResponse(ContractDTO.class, e);
    }

    DefaultResponse<ContractDTO> defaultResponse = new DefaultResponse<>(ContractDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<ContractDTO>> save(
      @Valid @NotNull @RequestBody ContractDTO contractDTO, BindingResult bindingResult) {
    List<ContractDTO> result = new ArrayList<>();

    try {

      if (bindingResult.hasErrors()) {
        String collect =
            bindingResult.getAllErrors().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        throw new InvalidDataException(collect);
      }

      if (contractDTO.getContractId() != null) {
        throw new InvalidDataException(
            getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
      }

      List<ContractEntity> entities =
          contractService.save(
              contractTransformer.transformDTOToEntity(contractDTO, ContractEntity.class));
      result = contractTransformer.transformEntityToDTO(entities, ContractDTO.class);

      if (CollectionUtils.isEmpty(result)) {
        throw new InvalidResultDataException("Data not saved");
      }

    } catch (Exception e) {
      return Utils.getErrorResponse(ContractDTO.class, e);
    }

    DefaultResponse<ContractDTO> defaultResponse = new DefaultResponse<>(ContractDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<ContractDTO>> update(
      @Valid @NotNull @RequestBody ContractDTO contractDTO, BindingResult bindingResult) {

    List<ContractDTO> result = new ArrayList<>();

    try {

      if (bindingResult.hasErrors()) {
        String collect =
            bindingResult.getAllErrors().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        throw new InvalidDataException(collect);
      }
      if (contractDTO.getContractId() == null) {
        throw new InvalidDataException("When updating, data entity must have ID");
      }

      List<ContractEntity> entities =
          contractService.save(
              contractTransformer.transformDTOToEntity(contractDTO, ContractEntity.class));
      result = contractTransformer.transformEntityToDTO(entities, ContractDTO.class);

      if (CollectionUtils.isEmpty(result)) {
        throw new InvalidResultDataException("Data not saved");
      }

    } catch (Exception e) {
      return Utils.getErrorResponse(ContractDTO.class, e);
    }

    DefaultResponse<ContractDTO> defaultResponse = new DefaultResponse<>(ContractDTO.class);
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
      contractService.delete(id);

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
  public ResponseEntity<byte[]> findContentByContractId(@PathVariable("id") Integer id) {
    byte[] source;
    String contentType = "image/png";

    try {
      Assert.notNull(
          id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
      ContentEntity content =
          contractService.findContentByParentEntityId(
              id, ContentEntity.ContentEntityTable.CONTRACT);
      if (content != null) {
        source = content.getContent();
        if (source.length > 0) {
          contentType = new Tika().detect(source);
        }
      } else {
        throw new InvalidDataException("Content not found for the entity " + id);
      }

    } catch (Exception e) {
      log.error(e.toString(), e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return ResponseEntity.ok()
        .contentLength(source.length)
        .contentType(MediaType.parseMediaType(contentType))
        .body(source);
  }

  @Override
  public ResponseEntity<DefaultResponse<ContractDTO>> saveContentByContractId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
    List<ContractDTO> result = new ArrayList<>();

    try {
      Assert.notNull(
          id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
      ContentEntity content = new ContentEntity();
      content.setContent(file.getBytes());
      content.setFilename(file.getOriginalFilename());
      content.setDateCreated(new Date());
      content.setContentTable(ContentEntity.ContentEntityTable.CONTRACT.name());

      List<ContractEntity> entities = contractService.saveContent(id, content);
      result = contractTransformer.transformEntityToDTO(entities, ContractDTO.class);

    } catch (Exception e) {
      return Utils.getErrorResponse(ContractDTO.class, e);
    }

    DefaultResponse<ContractDTO> defaultResponse = new DefaultResponse<>(ContractDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }
}
