package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.AttachmentAPI;
import com.bgh.myopeninvoice.api.domain.SearchParameters;
import com.bgh.myopeninvoice.api.domain.dto.AttachmentDTO;
import com.bgh.myopeninvoice.api.service.AttachmentCRUDService;
import com.bgh.myopeninvoice.api.transformer.AttachmentTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.common.domain.DefaultResponse;
import com.bgh.myopeninvoice.common.domain.OperationResponse;
import com.bgh.myopeninvoice.common.exception.InvalidDataException;
import com.bgh.myopeninvoice.common.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.db.domain.AttachmentEntity;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.QAttachmentEntity;
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
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class AttachmentController extends AbstractController implements AttachmentAPI {

  private static final String ENTITY_ID_CANNOT_BE_NULL = "entity.id-cannot-be-null";

  @Autowired private AttachmentCRUDService attachmentService;

  @Autowired private AttachmentTransformer attachmentTransformer;

  @Override
  public ResponseEntity<DefaultResponse<AttachmentDTO>> findAll(
      @RequestParam Map<String, String> queryParameters) {
    List<AttachmentDTO> result = new ArrayList<>();
    long count;

    SearchParameters searchParameters = Utils.mapQueryParametersToSearchParameters(queryParameters);
    validateSpecialFilter(queryParameters, searchParameters);
    count = attachmentService.count(searchParameters);
    List<AttachmentEntity> entities = attachmentService.findAll(searchParameters);
    result = attachmentTransformer.transformEntityToDTO(entities, AttachmentDTO.class);

    DefaultResponse<AttachmentDTO> defaultResponse = new DefaultResponse<>(AttachmentDTO.class);
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
              .and(QAttachmentEntity.attachmentEntity.invoiceId.eq(Integer.valueOf(split[1])));
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
  public ResponseEntity<DefaultResponse<AttachmentDTO>> findById(@PathVariable("id") Integer id) {
    List<AttachmentDTO> result = new ArrayList<>();

    Assert.notNull(
        id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
    List<AttachmentEntity> entities = attachmentService.findById(id);
    result = attachmentTransformer.transformEntityToDTO(entities, AttachmentDTO.class);

    DefaultResponse<AttachmentDTO> defaultResponse = new DefaultResponse<>(AttachmentDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<AttachmentDTO>> save(
      @Valid @NotNull @RequestBody AttachmentDTO attachmentDTO, BindingResult bindingResult) {
    List<AttachmentDTO> result = new ArrayList<>();

    if (bindingResult.hasErrors()) {
      String collect =
          bindingResult.getAllErrors().stream()
              .map(Object::toString)
              .collect(Collectors.joining(", "));
      throw new InvalidDataException(collect);
    }

    if (attachmentDTO.getAttachmentId() != null) {
      throw new InvalidDataException(
          getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
    }

    List<AttachmentEntity> entities =
        attachmentService.save(
            attachmentTransformer.transformDTOToEntity(attachmentDTO, AttachmentEntity.class));
    result = attachmentTransformer.transformEntityToDTO(entities, AttachmentDTO.class);

    if (CollectionUtils.isEmpty(result)) {
      throw new InvalidResultDataException("Data not saved");
    }

    DefaultResponse<AttachmentDTO> defaultResponse = new DefaultResponse<>(AttachmentDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<AttachmentDTO>> update(
      @Valid @NotNull @RequestBody AttachmentDTO attachmentDTO, BindingResult bindingResult) {

    List<AttachmentDTO> result = new ArrayList<>();

    if (bindingResult.hasErrors()) {
      String collect =
          bindingResult.getAllErrors().stream()
              .map(Object::toString)
              .collect(Collectors.joining(", "));
      throw new InvalidDataException(collect);
    }
    if (attachmentDTO.getAttachmentId() == null) {
      throw new InvalidDataException("When updating, data entity must have ID");
    }

    List<AttachmentEntity> entities =
        attachmentService.save(
            attachmentTransformer.transformDTOToEntity(attachmentDTO, AttachmentEntity.class));
    result = attachmentTransformer.transformEntityToDTO(entities, AttachmentDTO.class);

    if (CollectionUtils.isEmpty(result)) {
      throw new InvalidResultDataException("Data not saved");
    }

    DefaultResponse<AttachmentDTO> defaultResponse = new DefaultResponse<>(AttachmentDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

    Assert.notNull(
        id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
    attachmentService.delete(id);

    DefaultResponse<Boolean> defaultResponse = new DefaultResponse<>(Boolean.class);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    defaultResponse.setMessage("Deleted entity with id: " + id);
    defaultResponse.setDetails(Collections.singletonList(true));
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<byte[]> findContentByAttachmentId(@PathVariable("id") Integer id) {

    byte[] source;
    String contentType = "image/png";

    try {
      Assert.notNull(
          id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
      ContentEntity content =
          attachmentService.findContentByParentEntityId(
              id, ContentEntity.ContentEntityTable.ATTACHMENT);
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
  public ResponseEntity<DefaultResponse<AttachmentDTO>> saveContentByAttachmentId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) throws IOException {
    List<AttachmentDTO> result = new ArrayList<>();

    Assert.notNull(
        id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
    ContentEntity content = new ContentEntity();
    content.setContent(file.getBytes());
    content.setFilename(file.getOriginalFilename());
    content.setCreatedAt(ZonedDateTime.now());
    content.setContentTable(ContentEntity.ContentEntityTable.ATTACHMENT.name());

    List<AttachmentEntity> entities = attachmentService.saveContent(id, content);
    result = attachmentTransformer.transformEntityToDTO(entities, AttachmentDTO.class);

    DefaultResponse<AttachmentDTO> defaultResponse = new DefaultResponse<>(AttachmentDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }
}
