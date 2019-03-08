package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.CompanyContactAPI;
import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.domain.dto.CompanyContactDTO;
import com.bgh.myopeninvoice.api.service.CompanyContactCRUDService;
import com.bgh.myopeninvoice.api.transformer.CompanyContactTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.common.domain.DefaultResponse;
import com.bgh.myopeninvoice.common.domain.OperationResponse;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.common.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.db.domain.CompanyContactEntity;
import com.bgh.myopeninvoice.db.domain.QCompanyContactEntity;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class CompanyContactController extends AbstractController implements CompanyContactAPI {

  @Autowired private CompanyContactCRUDService companycontactService;

  @Autowired private CompanyContactTransformer companycontactTransformer;

  @Override
  public ResponseEntity<DefaultResponse<CompanyContactDTO>> findAll(
      @RequestParam Map<String, String> queryParameters) {
    List<CompanyContactDTO> result = new ArrayList<>();
    long count;

    SearchParameters searchParameters = Utils.mapQueryParametersToSearchParameters(queryParameters);
    validateSpecialFilter(queryParameters, searchParameters);
    count = companycontactService.count(searchParameters);
    List<CompanyContactEntity> entities = companycontactService.findAll(searchParameters);
    result = companycontactTransformer.transformEntityToDTO(entities, CompanyContactDTO.class);

    DefaultResponse<CompanyContactDTO> defaultResponse =
        new DefaultResponse<>(CompanyContactDTO.class);
    defaultResponse.setCount(count);
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  protected void validateSpecialFilter(
      Map<String, String> queryParameters, SearchParameters searchParameters) {
    if (StringUtils.isNotEmpty(queryParameters.get(FILTER_FIELD))) {
      Matcher matcher = filterPattern.matcher(queryParameters.get(FILTER_FIELD));
      BooleanBuilder builder = searchParameters.getBuilder();

      while (matcher.find()) {
        String[] split = matcher.group(1).split(":");
        if ("owned".equalsIgnoreCase(split[0])) {
          searchParameters
              .getBuilder()
              .and(
                  QCompanyContactEntity.companyContactEntity.companyByCompanyId.ownedByMe.eq(
                      Boolean.valueOf(split[1])));
        } else if ("companyId".equalsIgnoreCase(split[0])) {
          searchParameters
              .getBuilder()
              .and(
                  QCompanyContactEntity.companyContactEntity.companyId.eq(
                      NumberUtils.toInt(split[1])));
        }
      }

      if (searchParameters.getBuilder().hasValue()) {
        // reset the filter
        searchParameters.setFilter(null);
      }
    }
  }

  @Override
  public ResponseEntity<DefaultResponse<CompanyContactDTO>> findById(
      @PathVariable("id") Integer id) {
    List<CompanyContactDTO> result = new ArrayList<>();

    Assert.notNull(
        id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
    List<CompanyContactEntity> entities = companycontactService.findById(id);
    result = companycontactTransformer.transformEntityToDTO(entities, CompanyContactDTO.class);

    DefaultResponse<CompanyContactDTO> defaultResponse =
        new DefaultResponse<>(CompanyContactDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<CompanyContactDTO>> save(
      @Valid @NotNull @RequestBody CompanyContactDTO companycontactDTO,
      BindingResult bindingResult) {
    List<CompanyContactDTO> result = new ArrayList<>();

    if (bindingResult.hasErrors()) {
      String collect =
          bindingResult.getAllErrors().stream()
              .map(Object::toString)
              .collect(Collectors.joining(", "));
      throw new InvalidDataException(collect);
    }

    if (companycontactDTO.getCompanyContactId() != null) {
      throw new InvalidDataException(
          getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
    }

    List<CompanyContactEntity> entities =
        companycontactService.save(
            companycontactTransformer.transformDTOToEntity(
                companycontactDTO, CompanyContactEntity.class));
    result = companycontactTransformer.transformEntityToDTO(entities, CompanyContactDTO.class);

    if (CollectionUtils.isEmpty(result)) {
      throw new InvalidResultDataException("Data not saved");
    }

    DefaultResponse<CompanyContactDTO> defaultResponse =
        new DefaultResponse<>(CompanyContactDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<CompanyContactDTO>> update(
      @Valid @NotNull @RequestBody CompanyContactDTO companycontactDTO,
      BindingResult bindingResult) {

    List<CompanyContactDTO> result = new ArrayList<>();

    if (bindingResult.hasErrors()) {
      String collect =
          bindingResult.getAllErrors().stream()
              .map(Object::toString)
              .collect(Collectors.joining(", "));
      throw new InvalidDataException(collect);
    }
    if (companycontactDTO.getCompanyContactId() == null) {
      throw new InvalidDataException("When updating, data entity must have ID");
    }

    List<CompanyContactEntity> entities =
        companycontactService.save(
            companycontactTransformer.transformDTOToEntity(
                companycontactDTO, CompanyContactEntity.class));
    result = companycontactTransformer.transformEntityToDTO(entities, CompanyContactDTO.class);

    if (CollectionUtils.isEmpty(result)) {
      throw new InvalidResultDataException("Data not saved");
    }

    DefaultResponse<CompanyContactDTO> defaultResponse =
        new DefaultResponse<>(CompanyContactDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

    Assert.notNull(
        id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
    companycontactService.delete(id);

    DefaultResponse<Boolean> defaultResponse = new DefaultResponse<>(Boolean.class);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    defaultResponse.setMessage("Deleted entity with id: " + id);
    defaultResponse.setDetails(Collections.singletonList(true));
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<byte[]> findContentByCompanyContactId(@PathVariable("id") Integer id) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Override
  public ResponseEntity<DefaultResponse<CompanyContactDTO>> saveContentByCompanyContactId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Override
  public ResponseEntity<DefaultResponse<CompanyContactDTO>> updateBulk(
      @Valid @NotNull @RequestBody CompanyContactDTO[] companycontactDTO,
      BindingResult bindingResult) {
    List<CompanyContactDTO> result = new ArrayList<>();

    Assert.isTrue(companycontactDTO.length > 0, "There has to be at least one change listed");
    if (bindingResult.hasErrors()) {
      String collect =
          bindingResult.getAllErrors().stream()
              .map(Object::toString)
              .collect(Collectors.joining(", "));
      throw new InvalidDataException(collect);
    }

    List<CompanyContactEntity> companyContactEntities =
        companycontactTransformer.transformDTOToEntity(
            Arrays.asList(companycontactDTO), CompanyContactEntity.class);
    List<CompanyContactEntity> companyContactEntities1 =
        companycontactService.bulkAddDelete(companyContactEntities);
    result =
        companycontactTransformer.transformEntityToDTO(
            companyContactEntities1, CompanyContactDTO.class);

    DefaultResponse<CompanyContactDTO> defaultResponse =
        new DefaultResponse<>(CompanyContactDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }
}
