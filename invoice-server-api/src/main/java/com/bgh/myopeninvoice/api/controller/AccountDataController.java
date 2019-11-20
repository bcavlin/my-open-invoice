package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.AccountDataAPI;
import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.domain.dto.AccountDataDTO;
import com.bgh.myopeninvoice.api.service.AccountDataCRUDService;
import com.bgh.myopeninvoice.api.transformer.AccountDataTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.common.domain.DefaultResponse;
import com.bgh.myopeninvoice.common.domain.OperationResponse;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.common.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.db.domain.AccountDataEntity;
import com.bgh.myopeninvoice.db.domain.QAccountDataEntity;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class AccountDataController extends AbstractController implements AccountDataAPI {

  @Autowired
  private AccountDataCRUDService accountDataCRUDService;

  @Autowired
  private AccountDataTransformer accountDataTransformer;

  @Override
  public ResponseEntity<DefaultResponse<AccountDataDTO>> findAll(
          @RequestParam Map<String, String> queryParameters) {
    List<AccountDataDTO> result = new ArrayList<>();
    long count;

    SearchParameters searchParameters = Utils.mapQueryParametersToSearchParameters(queryParameters);
    validateSpecialFilter(queryParameters, searchParameters);
    count = accountDataCRUDService.count(searchParameters);
    List<AccountDataEntity> entities = accountDataCRUDService.findAll(searchParameters);
    result = accountDataTransformer.transformEntityToDTO(entities, AccountDataDTO.class);

    DefaultResponse<AccountDataDTO> defaultResponse = new DefaultResponse<>(AccountDataDTO.class);
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
      boolean foundGroup2 = false;

      while (matcher.find()) {
        String[] split = matcher.group(1).split(":");
        if ("accountId".equalsIgnoreCase(split[0])) {
          searchParameters
                  .getBuilder()
                  .and(QAccountDataEntity.accountDataEntity.accountId.eq(Integer.valueOf(split[1])));
        } else {
          log.info("Skipping search parameter: " + matcher.group(1));
        }

        if (matcher.group(2) != null) {
          foundGroup2 = true;
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
  public ResponseEntity<DefaultResponse<AccountDataDTO>> findByAccountId(
          @PathVariable("id") Integer id, @RequestParam Map<String, String> queryParameters) {
    List<AccountDataDTO> result = new ArrayList<>();

    Assert.notNull(
            id, getMessageSource().getMessage("account-id.cannot-be-null", null, getContextLocale()));
    List<AccountDataEntity> entities = accountDataCRUDService.findByAccountId(id);
    result = accountDataTransformer.transformEntityToDTO(entities, AccountDataDTO.class);

    DefaultResponse<AccountDataDTO> defaultResponse = new DefaultResponse<>(AccountDataDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<AccountDataDTO>> findById(@PathVariable("id") Integer id) {
    List<AccountDataDTO> result = new ArrayList<>();

    Assert.notNull(
            id,
            getMessageSource().getMessage("account-item-id.cannot-be-null", null, getContextLocale()));
    List<AccountDataEntity> entities = accountDataCRUDService.findById(id);
    result = accountDataTransformer.transformEntityToDTO(entities, AccountDataDTO.class);

    DefaultResponse<AccountDataDTO> defaultResponse = new DefaultResponse<>(AccountDataDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<AccountDataDTO>> save(
          @Valid @NotNull @RequestBody AccountDataDTO accountDataDTO, BindingResult bindingResult) {
    List<AccountDataDTO> result = new ArrayList<>();

    if (bindingResult.hasErrors()) {
      String collect =
              bindingResult.getAllErrors().stream()
                      .map(Object::toString)
                      .collect(Collectors.joining(", "));
      throw new InvalidDataException(collect);
    }

    if (accountDataDTO.getAccountId() != null) {
      throw new InvalidDataException(
              getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
    }

    List<AccountDataEntity> entities =
            accountDataCRUDService.save(
                    accountDataTransformer.transformDTOToEntity(accountDataDTO, AccountDataEntity.class));
    result = accountDataTransformer.transformEntityToDTO(entities, AccountDataDTO.class);

    if (CollectionUtils.isEmpty(result)) {
      throw new InvalidResultDataException("Data not saved");
    }

    DefaultResponse<AccountDataDTO> defaultResponse = new DefaultResponse<>(AccountDataDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<String>> parse(
          @Valid @NotNull @RequestBody String data,
          @RequestParam(value = "firstRowHeader", required = false, defaultValue = "false")
                  Boolean firstRowHeader,
          @RequestParam(value = "separator", required = false, defaultValue = "CSV") String separator,
          @RequestParam(value = "lineSeparator", required = false, defaultValue = "CRLF") String lineSeparator,
          @RequestParam(value = "provider", required = false) Integer provider,
          BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      String collect =
              bindingResult.getAllErrors().stream()
                      .map(Object::toString)
                      .collect(Collectors.joining(", "));
      throw new InvalidDataException(collect);
    }

    Integer parse = null;
    try {
      parse = accountDataCRUDService.parse(data, firstRowHeader, separator, lineSeparator, provider);
    } catch (Exception e) {
      log.error(e.toString(), e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    DefaultResponse<String> defaultResponse = new DefaultResponse<>(String.class);
    defaultResponse.setCount((long) 0);
    defaultResponse.setDetails(
            Collections.singletonList(String.format("Processed %d line(s) of code", parse)));
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }
}
