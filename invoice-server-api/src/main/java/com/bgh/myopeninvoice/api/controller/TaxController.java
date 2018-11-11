package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.service.TaxService;
import com.bgh.myopeninvoice.api.spec.TaxAPI;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.TaxEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class TaxController implements TaxAPI {

    @Autowired
    private TaxService taxService;

    @Override
    public ResponseEntity<DefaultResponse<TaxEntity>> findAll(@RequestParam Map<String, String> queryParameters) {
        List<TaxEntity> result;
        long count;

        try {
            count = taxService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
            result = taxService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));

        } catch (Exception e) {
            return Utils.getErrorResponse(TaxEntity.class, e);
        }

        DefaultResponse<TaxEntity> defaultResponse = new DefaultResponse<>(TaxEntity.class);
        defaultResponse.setCount(count);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<TaxEntity>> findById(@PathVariable("id") Integer id) {
        List<TaxEntity> result;

        try {
            Assert.notNull(id, "Entity id cannot be null");
            result = taxService.findById(id);

        } catch (Exception e) {
            return Utils.getErrorResponse(TaxEntity.class, e);
        }

        DefaultResponse<TaxEntity> defaultResponse = new DefaultResponse<>(TaxEntity.class);
        defaultResponse.setCount(1L);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<TaxEntity>> save(@Valid @NotNull @RequestBody TaxEntity taxEntity,
                                                           BindingResult bindingResult) {
        List<TaxEntity> result;

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }

            if (taxEntity.getTaxId() != null) {
                throw new InvalidDataException("When saving, data entity cannot have ID");
            }

            result = taxService.save(taxEntity);

            if (result.size() == 0) {
                throw new Exception("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(TaxEntity.class, e);
        }

        DefaultResponse<TaxEntity> defaultResponse = new DefaultResponse<>(TaxEntity.class);
        defaultResponse.setCount(1L);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<TaxEntity>> update(@Valid @NotNull @RequestBody TaxEntity taxEntity,
                                                             BindingResult bindingResult) {

        List<TaxEntity> result;

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }
            if (taxEntity.getTaxId() == null) {
                throw new InvalidDataException("When updating, data entity must have ID");
            }

            result = taxService.save(taxEntity);

            if (result.size() == 0) {
                throw new Exception("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(TaxEntity.class, e);
        }

        DefaultResponse<TaxEntity> defaultResponse = new DefaultResponse<>(TaxEntity.class);
        defaultResponse.setCount(1L);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

        try {
            Assert.notNull(id, "Entity id cannot be null");
            taxService.delete(id);

        } catch (Exception e) {
            return Utils.getErrorResponse(Boolean.class, e, false);
        }

        DefaultResponse<Boolean> defaultResponse = new DefaultResponse<>(Boolean.class);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        defaultResponse.setOperationMessage("Deleted entity with id: " + id);
        defaultResponse.setDetails(Collections.singletonList(true));
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

}
