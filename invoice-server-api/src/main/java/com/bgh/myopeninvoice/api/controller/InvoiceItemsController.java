package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.InvoiceItemsAPI;
import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.domain.dto.InvoiceItemsDTO;
import com.bgh.myopeninvoice.api.service.InvoiceItemsCRUDService;
import com.bgh.myopeninvoice.api.transformer.InvoiceItemsTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.common.domain.DefaultResponse;
import com.bgh.myopeninvoice.common.domain.OperationResponse;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.common.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.db.domain.InvoiceItemsEntity;
import com.bgh.myopeninvoice.db.domain.QInvoiceItemsEntity;
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
import org.springframework.web.multipart.MultipartFile;

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
public class InvoiceItemsController extends AbstractController implements InvoiceItemsAPI {

  private static final String ENTITY_ID_CANNOT_BE_NULL = "entity.id-cannot-be-null";

  @Autowired private InvoiceItemsCRUDService invoiceitemsService;

  @Autowired private InvoiceItemsTransformer invoiceitemsTransformer;

  @Override
  public ResponseEntity<DefaultResponse<InvoiceItemsDTO>> findAll(
      @RequestParam Map<String, String> queryParameters) {
    List<InvoiceItemsDTO> result = new ArrayList<>();
    long count;

    SearchParameters searchParameters = Utils.mapQueryParametersToSearchParameters(queryParameters);
    validateSpecialFilter(queryParameters, searchParameters);
    count = invoiceitemsService.count(searchParameters);
    List<InvoiceItemsEntity> entities = invoiceitemsService.findAll(searchParameters);
    result = invoiceitemsTransformer.transformEntityToDTO(entities, InvoiceItemsDTO.class);

    DefaultResponse<InvoiceItemsDTO> defaultResponse = new DefaultResponse<>(InvoiceItemsDTO.class);
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
        if ("invoiceId".equalsIgnoreCase(split[0])) {
          searchParameters
              .getBuilder()
              .and(QInvoiceItemsEntity.invoiceItemsEntity.invoiceId.eq(Integer.valueOf(split[1])));
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
        searchParameters.setFilter(null);
      }
    }
  }

  @Override
  public ResponseEntity<DefaultResponse<InvoiceItemsDTO>> findById(@PathVariable("id") Integer id) {
    List<InvoiceItemsDTO> result = new ArrayList<>();

    Assert.notNull(
        id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
    List<InvoiceItemsEntity> entities = invoiceitemsService.findById(id);
    result = invoiceitemsTransformer.transformEntityToDTO(entities, InvoiceItemsDTO.class);

    DefaultResponse<InvoiceItemsDTO> defaultResponse = new DefaultResponse<>(InvoiceItemsDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<InvoiceItemsDTO>> save(
      @Valid @NotNull @RequestBody InvoiceItemsDTO invoiceitemsDTO, BindingResult bindingResult) {
    List<InvoiceItemsDTO> result = new ArrayList<>();

    if (bindingResult.hasErrors()) {
      String collect =
          bindingResult.getAllErrors().stream()
              .map(Object::toString)
              .collect(Collectors.joining(", "));
      throw new InvalidDataException(collect);
    }

    if (invoiceitemsDTO.getInvoiceItemId() != null) {
      throw new InvalidDataException(
          getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
    }

    List<InvoiceItemsEntity> entities =
        invoiceitemsService.save(
            invoiceitemsTransformer.transformDTOToEntity(
                invoiceitemsDTO, InvoiceItemsEntity.class));
    result = invoiceitemsTransformer.transformEntityToDTO(entities, InvoiceItemsDTO.class);

    if (CollectionUtils.isEmpty(result)) {
      throw new InvalidResultDataException("Data not saved");
    }

    DefaultResponse<InvoiceItemsDTO> defaultResponse = new DefaultResponse<>(InvoiceItemsDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<InvoiceItemsDTO>> update(
      @Valid @NotNull @RequestBody InvoiceItemsDTO invoiceitemsDTO, BindingResult bindingResult) {

    List<InvoiceItemsDTO> result = new ArrayList<>();

    if (bindingResult.hasErrors()) {
      String collect =
          bindingResult.getAllErrors().stream()
              .map(Object::toString)
              .collect(Collectors.joining(", "));
      throw new InvalidDataException(collect);
    }
    if (invoiceitemsDTO.getInvoiceItemId() == null) {
      throw new InvalidDataException("When updating, data entity must have ID");
    }

    List<InvoiceItemsEntity> entities =
        invoiceitemsService.save(
            invoiceitemsTransformer.transformDTOToEntity(
                invoiceitemsDTO, InvoiceItemsEntity.class));
    result = invoiceitemsTransformer.transformEntityToDTO(entities, InvoiceItemsDTO.class);

    if (CollectionUtils.isEmpty(result)) {
      throw new InvalidResultDataException("Data not saved");
    }

    DefaultResponse<InvoiceItemsDTO> defaultResponse = new DefaultResponse<>(InvoiceItemsDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

    Assert.notNull(
        id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
    invoiceitemsService.delete(id);

    DefaultResponse<Boolean> defaultResponse = new DefaultResponse<>(Boolean.class);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    defaultResponse.setMessage("Deleted entity with id: " + id);
    defaultResponse.setDetails(Collections.singletonList(true));
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<byte[]> findContentByInvoiceItemsId(@PathVariable("id") Integer id) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Override
  public ResponseEntity<DefaultResponse<InvoiceItemsDTO>> saveContentByInvoiceItemsId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
    throw new org.apache.commons.lang.NotImplementedException();
  }
}
