package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.ContactAPI;
import com.bgh.myopeninvoice.api.domain.dto.ContactDTO;
import com.bgh.myopeninvoice.api.service.ContactCRUDService;
import com.bgh.myopeninvoice.api.transformer.ContactTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.common.domain.DefaultResponse;
import com.bgh.myopeninvoice.common.domain.OperationResponse;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.common.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.db.domain.ContactEntity;
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
public class ContactController extends AbstractController implements ContactAPI {

  private static final String ENTITY_ID_CANNOT_BE_NULL = "entity.id-cannot-be-null";

  @Autowired private ContactCRUDService contactService;

  @Autowired private ContactTransformer contactTransformer;

  @Override
  public ResponseEntity<DefaultResponse<ContactDTO>> findAll(
      @RequestParam Map<String, String> queryParameters) {
    List<ContactDTO> result = new ArrayList<>();
    long count;

    count = contactService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
    List<ContactEntity> entities =
        contactService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));
    result = contactTransformer.transformEntityToDTO(entities, ContactDTO.class);

    DefaultResponse<ContactDTO> defaultResponse = new DefaultResponse<>(ContactDTO.class);
    defaultResponse.setCount(count);
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<ContactDTO>> findById(@PathVariable("id") Integer id) {
    List<ContactDTO> result = new ArrayList<>();

    Assert.notNull(
        id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
    List<ContactEntity> entities = contactService.findById(id);
    result = contactTransformer.transformEntityToDTO(entities, ContactDTO.class);

    DefaultResponse<ContactDTO> defaultResponse = new DefaultResponse<>(ContactDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<ContactDTO>> save(
      @Valid @NotNull @RequestBody ContactDTO contactDTO, BindingResult bindingResult) {
    List<ContactDTO> result = new ArrayList<>();

    if (bindingResult.hasErrors()) {
      String collect =
          bindingResult.getAllErrors().stream()
              .map(Object::toString)
              .collect(Collectors.joining(", "));
      throw new InvalidDataException(collect);
    }

    if (contactDTO.getContactId() != null) {
      throw new InvalidDataException(
          getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
    }

    List<ContactEntity> entities =
        contactService.save(
            contactTransformer.transformDTOToEntity(contactDTO, ContactEntity.class));
    result = contactTransformer.transformEntityToDTO(entities, ContactDTO.class);

    if (CollectionUtils.isEmpty(result)) {
      throw new InvalidResultDataException("Data not saved");
    }

    DefaultResponse<ContactDTO> defaultResponse = new DefaultResponse<>(ContactDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<ContactDTO>> update(
      @Valid @NotNull @RequestBody ContactDTO contactDTO, BindingResult bindingResult) {

    List<ContactDTO> result = new ArrayList<>();

    if (bindingResult.hasErrors()) {
      String collect =
          bindingResult.getAllErrors().stream()
              .map(Object::toString)
              .collect(Collectors.joining(", "));
      throw new InvalidDataException(collect);
    }
    if (contactDTO.getContactId() == null) {
      throw new InvalidDataException("When updating, data entity must have ID");
    }

    List<ContactEntity> entities =
        contactService.save(
            contactTransformer.transformDTOToEntity(contactDTO, ContactEntity.class));
    result = contactTransformer.transformEntityToDTO(entities, ContactDTO.class);

    if (CollectionUtils.isEmpty(result)) {
      throw new InvalidResultDataException("Data not saved");
    }

    DefaultResponse<ContactDTO> defaultResponse = new DefaultResponse<>(ContactDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

    Assert.notNull(
        id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
    contactService.delete(id);

    DefaultResponse<Boolean> defaultResponse = new DefaultResponse<>(Boolean.class);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    defaultResponse.setMessage("Deleted entity with id: " + id);
    defaultResponse.setDetails(Collections.singletonList(true));
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<byte[]> findContentByContactId(@PathVariable("id") Integer id) {
    throw new org.apache.commons.lang.NotImplementedException();
  }

  @Override
  public ResponseEntity<DefaultResponse<ContactDTO>> saveContentByContactId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
    throw new org.apache.commons.lang.NotImplementedException();
  }
}
