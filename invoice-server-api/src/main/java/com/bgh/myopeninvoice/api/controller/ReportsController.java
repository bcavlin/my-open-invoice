package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.ReportsAPI;
import com.bgh.myopeninvoice.api.domain.dto.ReportsDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.api.service.ReportsService;
import com.bgh.myopeninvoice.api.transformer.ReportsTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import com.bgh.myopeninvoice.db.domain.ReportsEntity;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ReportsController extends AbstractController implements ReportsAPI {

  private static final String ENTITY_ID_CANNOT_BE_NULL = "entity.id-cannot-be-null";

  @Autowired private ReportsService reportsService;

  @Autowired private ReportsTransformer reportsTransformer;

  @Override
  public ResponseEntity<DefaultResponse<ReportsDTO>> findAll(
      @RequestParam Map<String, String> queryParameters) {
    List<ReportsDTO> result = new ArrayList<>();
    long count;

    try {
      count = reportsService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
      List<ReportsEntity> entities =
          reportsService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));
      result = reportsTransformer.transformEntityToDTO(entities, ReportsDTO.class);

    } catch (Exception e) {
      return Utils.getErrorResponse(ReportsDTO.class, e);
    }

    DefaultResponse<ReportsDTO> defaultResponse = new DefaultResponse<>(ReportsDTO.class);
    defaultResponse.setCount(count);
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<ReportsDTO>> findById(@PathVariable("id") Integer id) {
    List<ReportsDTO> result = new ArrayList<>();

    try {
      Assert.notNull(
          id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
      List<ReportsEntity> entities = reportsService.findById(id);
      result = reportsTransformer.transformEntityToDTO(entities, ReportsDTO.class);

    } catch (Exception e) {
      return Utils.getErrorResponse(ReportsDTO.class, e);
    }

    DefaultResponse<ReportsDTO> defaultResponse = new DefaultResponse<>(ReportsDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<ReportsDTO>> save(
      @Valid @NotNull @RequestBody ReportsDTO reportsDTO, BindingResult bindingResult) {
    List<ReportsDTO> result = new ArrayList<>();

    try {

      if (bindingResult.hasErrors()) {
        String collect =
            bindingResult.getAllErrors().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        throw new InvalidDataException(collect);
      }

      if (reportsDTO.getReportId() != null) {
        throw new InvalidDataException(
            getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
      }

      List<ReportsEntity> entities =
          reportsService.save(
              reportsTransformer.transformDTOToEntity(reportsDTO, ReportsEntity.class));
      result = reportsTransformer.transformEntityToDTO(entities, ReportsDTO.class);

      if (CollectionUtils.isEmpty(result)) {
        throw new InvalidResultDataException("Data not saved");
      }

    } catch (Exception e) {
      return Utils.getErrorResponse(ReportsDTO.class, e);
    }

    DefaultResponse<ReportsDTO> defaultResponse = new DefaultResponse<>(ReportsDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DefaultResponse<ReportsDTO>> update(
      @Valid @NotNull @RequestBody ReportsDTO reportsDTO, BindingResult bindingResult) {

    List<ReportsDTO> result = new ArrayList<>();

    try {

      if (bindingResult.hasErrors()) {
        String collect =
            bindingResult.getAllErrors().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        throw new InvalidDataException(collect);
      }
      if (reportsDTO.getReportId() == null) {
        throw new InvalidDataException("When updating, data entity must have ID");
      }

      List<ReportsEntity> entities =
          reportsService.save(
              reportsTransformer.transformDTOToEntity(reportsDTO, ReportsEntity.class));
      result = reportsTransformer.transformEntityToDTO(entities, ReportsDTO.class);

      if (CollectionUtils.isEmpty(result)) {
        throw new InvalidResultDataException("Data not saved");
      }

    } catch (Exception e) {
      return Utils.getErrorResponse(ReportsDTO.class, e);
    }

    DefaultResponse<ReportsDTO> defaultResponse = new DefaultResponse<>(ReportsDTO.class);
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
      reportsService.delete(id);

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
  public ResponseEntity<byte[]> findContentByReportsId(@PathVariable("id") Integer id) {
    byte[] source;
    String contentType = "image/png";

    try {
      Assert.notNull(
          id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
      ContentEntity content =
          reportsService.findContentByParentEntityId(id, ContentEntity.ContentEntityTable.REPORTS);
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
  public ResponseEntity<DefaultResponse<ReportsDTO>> saveContentByReportsId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
    List<ReportsDTO> result = new ArrayList<>();

    try {
      Assert.notNull(
          id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
      ContentEntity content = new ContentEntity();
      content.setContent(file.getBytes());
      content.setFilename(file.getOriginalFilename());
      content.setDateCreated(LocalDateTime.now());
      content.setContentTable(ContentEntity.ContentEntityTable.REPORTS.name());

      List<ReportsEntity> entities = reportsService.saveContent(id, content);
      result = reportsTransformer.transformEntityToDTO(entities, ReportsDTO.class);

    } catch (Exception e) {
      return Utils.getErrorResponse(ReportsDTO.class, e);
    }

    DefaultResponse<ReportsDTO> defaultResponse = new DefaultResponse<>(ReportsDTO.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }
}
