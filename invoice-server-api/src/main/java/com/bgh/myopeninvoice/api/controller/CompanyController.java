package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.CompanyAPI;
import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.domain.dto.CompanyDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.api.service.CompanyService;
import com.bgh.myopeninvoice.api.transformer.CompanyTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.CompanyEntity;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.QCompanyEntity;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class CompanyController extends AbstractController implements CompanyAPI {

  @Autowired private CompanyService companyService;

  @Autowired private CompanyTransformer companyTransformer;

  @Override
  public ResponseEntity<DefaultResponse<CompanyDTO>> findAll(
      @RequestParam Map<String, String> queryParameters) {
    List<CompanyDTO> result = new ArrayList<>();
    long count;

    try {
      SearchParameters searchParameters =
          Utils.mapQueryParametersToSearchParameters(queryParameters);
      validateSpecialFilter(queryParameters, searchParameters);
      count = companyService.count(searchParameters);
      List<CompanyEntity> entities = companyService.findAll(searchParameters);
      result = companyTransformer.transformEntityToDTO(entities, CompanyDTO.class);

    } catch (Exception e) {
      return Utils.getErrorResponse(CompanyDTO.class, e);
    }

    DefaultResponse<CompanyDTO> defaultResponse = new DefaultResponse<>(CompanyDTO.class);
    defaultResponse.setCount(count);
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  protected void validateSpecialFilter(
      Map<String, String> queryParameters, SearchParameters searchParameters) {
    if (StringUtils.isNotEmpty(queryParameters.get(FILTER_FIELD))) {
      Matcher matcher = filterPattern.matcher(queryParameters.get(FILTER_FIELD));
      BooleanBuilder builder = searchParameters.getBuilder();
      boolean foundGroup2 = false;

      while (matcher.find()) {
        String[] split = matcher.group(1).split(":");
        if ("owned".equalsIgnoreCase(split[0])) {
          searchParameters
              .getBuilder()
              .and(QCompanyEntity.companyEntity.ownedByMe.eq(Boolean.valueOf(split[1])));
        } else {
          log.info("Skipping search parameter: " + matcher.group(1));
        }

        if(matcher.group(2)!=null){
          foundGroup2=true;
          /** Set additional search properties one # filters have been removed */
          searchParameters.setFilter(matcher.group(2));
        }
      }

      if (searchParameters.getBuilder().hasValue() && !foundGroup2) {
        // reset the filter
        log.info("Resetting the filter to null");
        searchParameters.setFilter(null);
      }
    }
  }

  @Override
  public ResponseEntity<DefaultResponse<CompanyDTO>> findById(@PathVariable("id") Integer id) {
    List<CompanyDTO> result = new ArrayList<>();

    try {
      Assert.notNull(
          id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
      List<CompanyEntity> entities = companyService.findById(id);
      result = companyTransformer.transformEntityToDTO(entities, CompanyDTO.class);

    } catch (Exception e) {
      return Utils.getErrorResponse(CompanyDTO.class, e);
    }

    DefaultResponse<CompanyDTO> defaultResponse = new DefaultResponse<>(CompanyDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<CompanyDTO>> save(
      @Valid @NotNull @RequestBody CompanyDTO companyDTO, BindingResult bindingResult) {
    List<CompanyDTO> result = new ArrayList<>();

    try {

      if (bindingResult.hasErrors()) {
        String collect =
            bindingResult.getAllErrors().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        throw new InvalidDataException(collect);
      }

      if (companyDTO.getCompanyId() != null) {
        throw new InvalidDataException(
            getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
      }

      List<CompanyEntity> entities =
          companyService.save(
              companyTransformer.transformDTOToEntity(companyDTO, CompanyEntity.class));
      result = companyTransformer.transformEntityToDTO(entities, CompanyDTO.class);

      if (CollectionUtils.isEmpty(result)) {
        throw new InvalidResultDataException("Data not saved");
      }

    } catch (Exception e) {
      return Utils.getErrorResponse(CompanyDTO.class, e);
    }

    DefaultResponse<CompanyDTO> defaultResponse = new DefaultResponse<>(CompanyDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<CompanyDTO>> update(
      @Valid @NotNull @RequestBody CompanyDTO companyDTO, BindingResult bindingResult) {

    List<CompanyDTO> result = new ArrayList<>();

    try {

      if (bindingResult.hasErrors()) {
        String collect =
            bindingResult.getAllErrors().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        throw new InvalidDataException(collect);
      }
      if (companyDTO.getCompanyId() == null) {
        throw new InvalidDataException("When updating, data entity must have ID");
      }

      CompanyEntity entity =
          companyTransformer.transformDTOToEntity(companyDTO, CompanyEntity.class);
      List<CompanyEntity> entities = companyService.save(entity);
      result = companyTransformer.transformEntityToDTO(entities, CompanyDTO.class);

      if (CollectionUtils.isEmpty(result)) {
        throw new InvalidResultDataException("Data not saved");
      }

    } catch (Exception e) {
      return Utils.getErrorResponse(CompanyDTO.class, e);
    }

    DefaultResponse<CompanyDTO> defaultResponse = new DefaultResponse<>(CompanyDTO.class);
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
      companyService.delete(id);

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
  public ResponseEntity<byte[]> findContentByCompanyId(@PathVariable("id") Integer id) {

    byte[] source;
    String contentType = "image/png";

    try {
      Assert.notNull(
          id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
      ContentEntity content =
          companyService.findContentByParentEntityId(id, ContentEntity.ContentEntityTable.COMPANY);
      if (content != null) {
        source = content.getContent();
        if (source != null && source.length > 0) {
          contentType = new Tika().detect(source);
        } else {
          throw new InvalidDataException("Content source not found for the entity " + id);
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
  public ResponseEntity<DefaultResponse<CompanyDTO>> saveContentByCompanyId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
    List<CompanyDTO> result = new ArrayList<>();

    try {
      Assert.notNull(
          id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
      ContentEntity content = new ContentEntity();
      content.setContent(file.getBytes());
      content.setFilename(file.getOriginalFilename());
      content.setDateCreated(LocalDateTime.now());
      content.setContentTable(ContentEntity.ContentEntityTable.COMPANY.name());

      List<CompanyEntity> entities = companyService.saveContent(id, content);
      result = companyTransformer.transformEntityToDTO(entities, CompanyDTO.class);

    } catch (Exception e) {
      return Utils.getErrorResponse(CompanyDTO.class, e);
    }

    DefaultResponse<CompanyDTO> defaultResponse = new DefaultResponse<>(CompanyDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }
}
