package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.CompanyAPI;
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
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class CompanyController extends AbstractController implements CompanyAPI {

    private static final String ENTITY_ID_CANNOT_BE_NULL = "entity.id-cannot-be-null";

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyTransformer companyTransformer;

    @Override
    public ResponseEntity<DefaultResponse<CompanyDTO>> findAll(@RequestParam Map<String, String> queryParameters) {
        List<CompanyDTO> result = new ArrayList<>();
        long count;

        try {
            count = companyService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
            List<CompanyEntity> entities = companyService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));
            result = companyTransformer.transformEntityToDTO(entities);

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
    public ResponseEntity<DefaultResponse<CompanyDTO>> findById(@PathVariable("id") Integer id) {
        List<CompanyDTO> result = new ArrayList<>();

        try {
            Assert.notNull(id, getMessageSource().getMessage("entity.id-cannot-be-null", null, getContextLocale()));
            List<CompanyEntity> entities = companyService.findById(id);
            result = companyTransformer.transformEntityToDTO(entities);

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
    public ResponseEntity<DefaultResponse<CompanyDTO>> save(@Valid @NotNull @RequestBody CompanyDTO companyDTO,
                                                            BindingResult bindingResult) {
        List<CompanyDTO> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }

            if (companyDTO.getCompanyId() != null) {
                throw new InvalidDataException(
                        getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
            }

            List<CompanyEntity> entities = companyService.save(companyTransformer.transformDTOToEntity(companyDTO));
            result = companyTransformer.transformEntityToDTO(entities);


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
    public ResponseEntity<DefaultResponse<CompanyDTO>> update(@Valid @NotNull @RequestBody CompanyDTO companyDTO,
                                                              BindingResult bindingResult) {

        List<CompanyDTO> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }
            if (companyDTO.getCompanyId() == null) {
                throw new InvalidDataException("When updating, data entity must have ID");
            }

            List<CompanyEntity> entities = companyService.save(companyTransformer.transformDTOToEntity(companyDTO));
            result = companyTransformer.transformEntityToDTO(entities);

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
            Assert.notNull(id, getMessageSource().getMessage("entity.id-cannot-be-null", null, getContextLocale()));
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
    public ResponseEntity<InputStreamResource> findContentByCompanyId(@PathVariable("id") Integer id) {

        InputStreamResource result = null;
        byte[] source;
        String contentType = "image/png";

        try {
            Assert.notNull(id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
            ContentEntity content = companyService.findContentByParentEntityId(id, ContentEntity.ContentEntityTable.COMPANY);
            if (content != null) {
                source = content.getContent();
                if (source.length > 0) {
                    contentType = new Tika().detect(source);
                    result = new InputStreamResource(new ByteArrayInputStream(source));
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
                .body(result);
    }

    @Override
    public ResponseEntity<DefaultResponse<CompanyDTO>> saveContentByCompanyId(@PathVariable("id") Integer id,
                                                                              @RequestParam("file") MultipartFile file) {
        List<CompanyDTO> result = new ArrayList<>();

        try {
            Assert.notNull(id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
            ContentEntity content = companyService.findContentByParentEntityId(id, ContentEntity.ContentEntityTable.COMPANY);
            if (content == null) {
                content = new ContentEntity();
            }
            content.setContent(file.getBytes());
            content.setFilename(file.getOriginalFilename());
            content.setContentTable(ContentEntity.ContentEntityTable.COMPANY.name());

            List<CompanyEntity> entities = companyService.saveContent(id, content);
            result = companyTransformer.transformEntityToDTO(entities);

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
