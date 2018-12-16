package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.AttachmentAPI;
import com.bgh.myopeninvoice.api.domain.dto.AttachmentDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.api.service.AttachmentService;
import com.bgh.myopeninvoice.api.transformer.AttachmentTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.AttachmentEntity;
import com.bgh.myopeninvoice.db.domain.ContentEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
import java.util.stream.Collectors;

@Slf4j
@RestController
public class AttachmentController extends AbstractController implements AttachmentAPI {

    private static final String ENTITY_ID_CANNOT_BE_NULL = "entity.id-cannot-be-null";

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentTransformer attachmentTransformer;

    @Override
    public ResponseEntity<DefaultResponse<AttachmentDTO>> findAll(@RequestParam Map<String, String> queryParameters) {
        List<AttachmentDTO> result = new ArrayList<>();
        long count;

        try {
            count = attachmentService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
            List<AttachmentEntity> entities = attachmentService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));
            result = attachmentTransformer.transformEntityToDTO(entities, AttachmentDTO.class);

        } catch (Exception e) {
            return Utils.getErrorResponse(AttachmentDTO.class, e);
        }

        DefaultResponse<AttachmentDTO> defaultResponse = new DefaultResponse<>(AttachmentDTO.class);
        defaultResponse.setCount(count);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<AttachmentDTO>> findById(@PathVariable("id") Integer id) {
        List<AttachmentDTO> result = new ArrayList<>();

        try {
            Assert.notNull(id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
            List<AttachmentEntity> entities = attachmentService.findById(id);
            result = attachmentTransformer.transformEntityToDTO(entities, AttachmentDTO.class);

        } catch (Exception e) {
            return Utils.getErrorResponse(AttachmentDTO.class, e);
        }

        DefaultResponse<AttachmentDTO> defaultResponse = new DefaultResponse<>(AttachmentDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<AttachmentDTO>> save(@Valid @NotNull @RequestBody AttachmentDTO attachmentDTO,
                                                        BindingResult bindingResult) {
        List<AttachmentDTO> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }

            if (attachmentDTO.getAttachmentId() != null) {
                throw new InvalidDataException(
                        getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
            }

            List<AttachmentEntity> entities = attachmentService.save(
                    attachmentTransformer.transformDTOToEntity(attachmentDTO, AttachmentEntity.class));
            result = attachmentTransformer.transformEntityToDTO(entities, AttachmentDTO.class);


            if (CollectionUtils.isEmpty(result)) {
                throw new InvalidResultDataException("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(AttachmentDTO.class, e);
        }

        DefaultResponse<AttachmentDTO> defaultResponse = new DefaultResponse<>(AttachmentDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<AttachmentDTO>> update(@Valid @NotNull @RequestBody AttachmentDTO attachmentDTO,
                                                          BindingResult bindingResult) {

        List<AttachmentDTO> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }
            if (attachmentDTO.getAttachmentId() == null) {
                throw new InvalidDataException("When updating, data entity must have ID");
            }

            List<AttachmentEntity> entities = attachmentService.save(
                    attachmentTransformer.transformDTOToEntity(attachmentDTO, AttachmentEntity.class));
            result = attachmentTransformer.transformEntityToDTO(entities, AttachmentDTO.class);

            if (CollectionUtils.isEmpty(result)) {
                throw new InvalidResultDataException("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(AttachmentDTO.class, e);
        }

        DefaultResponse<AttachmentDTO> defaultResponse = new DefaultResponse<>(AttachmentDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

        try {
            Assert.notNull(id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
            attachmentService.delete(id);

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
    public ResponseEntity<byte[]> findContentByAttachmentId(@PathVariable("id") Integer id) {

        InputStreamResource result = null;
        byte[] source;
        String contentType = "image/png";

        try {
            Assert.notNull(id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
            ContentEntity content = attachmentService.findContentByParentEntityId(id, ContentEntity.ContentEntityTable.ATTACHMENT);
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
    public ResponseEntity<DefaultResponse<AttachmentDTO>> saveContentByAttachmentId(@PathVariable("id") Integer id,
                                                                                 @RequestParam("file") MultipartFile file) {
        List<AttachmentDTO> result = new ArrayList<>();

        try {
            Assert.notNull(id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));            
            ContentEntity content = new ContentEntity();
            content.setContent(file.getBytes());
            content.setFilename(file.getOriginalFilename());
            content.setDateCreated(new Date());
            content.setContentTable(ContentEntity.ContentEntityTable.ATTACHMENT.name());

            List<AttachmentEntity> entities = attachmentService.saveContent(id, content);
            result = attachmentTransformer.transformEntityToDTO(entities, AttachmentDTO.class);

        } catch (Exception e) {
            return Utils.getErrorResponse(AttachmentDTO.class, e);
        }

        DefaultResponse<AttachmentDTO> defaultResponse = new DefaultResponse<>(AttachmentDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

}
