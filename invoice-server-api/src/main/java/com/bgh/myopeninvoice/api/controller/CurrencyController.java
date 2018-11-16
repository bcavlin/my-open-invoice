package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.CurrencyAPI;
import com.bgh.myopeninvoice.api.domain.dto.CurrencyDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.api.service.CurrencyService;
import com.bgh.myopeninvoice.api.transformer.CurrencyTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.CurrencyEntity;
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
public class CurrencyController extends AbstractController implements CurrencyAPI {

    private static final String ENTITY_ID_CANNOT_BE_NULL = "entity.id-cannot-be-null";

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyTransformer currencyTransformer;

    @Override
    public ResponseEntity<DefaultResponse<CurrencyDTO>> findAll(@RequestParam Map<String, String> queryParameters) {
        List<CurrencyDTO> result = new ArrayList<>();
        long count;

        try {
            count = currencyService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
            List<CurrencyEntity> entities = currencyService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));
            result = currencyTransformer.transformEntityToDTO(entities);

        } catch (Exception e) {
            return Utils.getErrorResponse(CurrencyDTO.class, e);
        }

        DefaultResponse<CurrencyDTO> defaultResponse = new DefaultResponse<>(CurrencyDTO.class);
        defaultResponse.setCount(count);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<CurrencyDTO>> findById(@PathVariable("id") Integer id) {
        List<CurrencyDTO> result = new ArrayList<>();

        try {
            Assert.notNull(id, getMessageSource().getMessage("entity.id-cannot-be-null", null, getContextLocale()));
            List<CurrencyEntity> entities = currencyService.findById(id);
            result = currencyTransformer.transformEntityToDTO(entities);

        } catch (Exception e) {
            return Utils.getErrorResponse(CurrencyDTO.class, e);
        }

        DefaultResponse<CurrencyDTO> defaultResponse = new DefaultResponse<>(CurrencyDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<CurrencyDTO>> save(@Valid @NotNull @RequestBody CurrencyDTO currencyDTO,
                                                             BindingResult bindingResult) {
        List<CurrencyDTO> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }

            if (currencyDTO.getCcyId() != null) {
                throw new InvalidDataException(
                        getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
            }

            List<CurrencyEntity> entities = currencyService.save(currencyTransformer.transformDTOToEntity(currencyDTO));
            result = currencyTransformer.transformEntityToDTO(entities);


            if (CollectionUtils.isEmpty(result)) {
                throw new InvalidResultDataException("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(CurrencyDTO.class, e);
        }

        DefaultResponse<CurrencyDTO> defaultResponse = new DefaultResponse<>(CurrencyDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<CurrencyDTO>> update(@Valid @NotNull @RequestBody CurrencyDTO currencyDTO,
                                                               BindingResult bindingResult) {

        List<CurrencyDTO> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }
            if (currencyDTO.getCcyId() == null) {
                throw new InvalidDataException("When updating, data entity must have ID");
            }

            List<CurrencyEntity> entities = currencyService.save(currencyTransformer.transformDTOToEntity(currencyDTO));
            result = currencyTransformer.transformEntityToDTO(entities);

            if (CollectionUtils.isEmpty(result)) {
                throw new InvalidResultDataException("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(CurrencyDTO.class, e);
        }

        DefaultResponse<CurrencyDTO> defaultResponse = new DefaultResponse<>(CurrencyDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

        try {
            Assert.notNull(id, getMessageSource().getMessage("entity.id-cannot-be-null", null, getContextLocale()));
            currencyService.delete(id);

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
    public ResponseEntity<InputStreamResource> findContentByCurrencyId(Integer id) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<DefaultResponse<CurrencyDTO>> saveContentByCurrencyId(Integer id, MultipartFile file) {
        throw new NotImplementedException();
    }

}
