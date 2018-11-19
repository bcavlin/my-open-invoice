package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.CompanyContactAPI;
import com.bgh.myopeninvoice.api.domain.dto.CompanyContactDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.api.service.CompanyContactService;
import com.bgh.myopeninvoice.api.transformer.CompanyContactTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.CompanyContactEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
public class CompanyContactController extends AbstractController implements CompanyContactAPI {

    private static final String ENTITY_ID_CANNOT_BE_NULL = "entity.id-cannot-be-null";

    @Autowired
    private CompanyContactService companycontactService;

    @Autowired
    private CompanyContactTransformer companycontactTransformer;

    @Override
    public ResponseEntity<DefaultResponse<CompanyContactDTO>> findAll(@RequestParam Map<String, String> queryParameters) {
        List<CompanyContactDTO> result = new ArrayList<>();
        long count;

        try {
            count = companycontactService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
            List<CompanyContactEntity> entities = companycontactService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));
            result = companycontactTransformer.transformEntityToDTO(entities);

        } catch (Exception e) {
            return Utils.getErrorResponse(CompanyContactDTO.class, e);
        }

        DefaultResponse<CompanyContactDTO> defaultResponse = new DefaultResponse<>(CompanyContactDTO.class);
        defaultResponse.setCount(count);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<CompanyContactDTO>> findById(@PathVariable("id") Integer id) {
        List<CompanyContactDTO> result = new ArrayList<>();

        try {
            Assert.notNull(id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
            List<CompanyContactEntity> entities = companycontactService.findById(id);
            result = companycontactTransformer.transformEntityToDTO(entities);

        } catch (Exception e) {
            return Utils.getErrorResponse(CompanyContactDTO.class, e);
        }

        DefaultResponse<CompanyContactDTO> defaultResponse = new DefaultResponse<>(CompanyContactDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<CompanyContactDTO>> save(@Valid @NotNull @RequestBody CompanyContactDTO companycontactDTO,
                                                                   BindingResult bindingResult) {
        List<CompanyContactDTO> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }

            if (companycontactDTO.getCompanyContactId() != null) {
                throw new InvalidDataException(
                        getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
            }

            List<CompanyContactEntity> entities = companycontactService.save(companycontactTransformer.transformDTOToEntity(companycontactDTO));
            result = companycontactTransformer.transformEntityToDTO(entities);


            if (CollectionUtils.isEmpty(result)) {
                throw new InvalidResultDataException("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(CompanyContactDTO.class, e);
        }

        DefaultResponse<CompanyContactDTO> defaultResponse = new DefaultResponse<>(CompanyContactDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<CompanyContactDTO>> update(@Valid @NotNull @RequestBody CompanyContactDTO companycontactDTO,
                                                                     BindingResult bindingResult) {

        List<CompanyContactDTO> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }
            if (companycontactDTO.getCompanyContactId() == null) {
                throw new InvalidDataException("When updating, data entity must have ID");
            }

            List<CompanyContactEntity> entities = companycontactService.save(companycontactTransformer.transformDTOToEntity(companycontactDTO));
            result = companycontactTransformer.transformEntityToDTO(entities);

            if (CollectionUtils.isEmpty(result)) {
                throw new InvalidResultDataException("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(CompanyContactDTO.class, e);
        }

        DefaultResponse<CompanyContactDTO> defaultResponse = new DefaultResponse<>(CompanyContactDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

        try {
            Assert.notNull(id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
            companycontactService.delete(id);

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
    public ResponseEntity<InputStreamResource> findContentByCompanyContactId(Integer id) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<DefaultResponse<CompanyContactDTO>> saveContentByCompanyContactId(Integer id, MultipartFile file) {
        throw new NotImplementedException();
    }

}
