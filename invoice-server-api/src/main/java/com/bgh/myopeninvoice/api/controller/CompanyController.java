package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.CompanyAPI;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.service.CompanyService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class CompanyController implements CompanyAPI {

    @Autowired
    private CompanyService companyService;

    @Override
    public ResponseEntity<DefaultResponse<CompanyEntity>> findAll(@RequestParam Map<String, String> queryParameters) {
        List<CompanyEntity> result;
        long count;

        try {
            count = companyService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
            result = companyService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));

        } catch (Exception e) {
            return Utils.getErrorResponse(CompanyEntity.class, e);
        }

        DefaultResponse<CompanyEntity> defaultResponse = new DefaultResponse<>(CompanyEntity.class);
        defaultResponse.setCount(count);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<CompanyEntity>> findById(@PathVariable("id") Integer id) {
        List<CompanyEntity> result;

        try {
            Assert.notNull(id, "Entity id cannot be null");
            result = companyService.findById(id);

        } catch (Exception e) {
            return Utils.getErrorResponse(CompanyEntity.class, e);
        }

        DefaultResponse<CompanyEntity> defaultResponse = new DefaultResponse<>(CompanyEntity.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<CompanyEntity>> save(@Valid @NotNull @RequestBody CompanyEntity companyEntity,
                                                               BindingResult bindingResult) {
        List<CompanyEntity> result;

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }

            if (companyEntity.getCompanyId() != null) {
                throw new InvalidDataException("When saving, data entity cannot have ID");
            }

            result = companyService.save(companyEntity);

            if (result.size() == 0) {
                throw new Exception("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(CompanyEntity.class, e);
        }

        DefaultResponse<CompanyEntity> defaultResponse = new DefaultResponse<>(CompanyEntity.class);
        defaultResponse.setCount(1L);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<CompanyEntity>> update(@Valid @NotNull @RequestBody CompanyEntity companyEntity,
                                                                 BindingResult bindingResult) {

        List<CompanyEntity> result;

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }
            if (companyEntity.getCompanyId() == null) {
                throw new InvalidDataException("When updating, data entity must have ID");
            }

            result = companyService.save(companyEntity);

            if (result.size() == 0) {
                throw new Exception("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(CompanyEntity.class, e);
        }

        DefaultResponse<CompanyEntity> defaultResponse = new DefaultResponse<>(CompanyEntity.class);
        defaultResponse.setCount(1L);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

        try {
            Assert.notNull(id, "Entity id cannot be null");
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
            Assert.notNull(id, "Entity id cannot be null");
            ContentEntity content = companyService.findContentByCompanyId(id);
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

//    @Override
//    public ResponseEntity<InputStreamResource> saveContentById(@PathVariable("id") Integer id,
//                                                               @RequestParam("file") MultipartFile file) {
//        //TODO write bytes to content
//        return null;
//    }

}
