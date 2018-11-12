package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.service.CurrencyService;
import com.bgh.myopeninvoice.api.controller.spec.CurrencyAPI;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.CurrencyEntity;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class CurrencyController implements CurrencyAPI {

    @Autowired
    private CurrencyService currencyService;

    @Override
    public ResponseEntity<DefaultResponse<CurrencyEntity>> findAll(@RequestParam Map<String, String> queryParameters) {
        List<CurrencyEntity> result = new ArrayList<>();
        long count;

        try {
            count = currencyService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
            result = currencyService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));

        } catch (Exception e) {
            return Utils.getErrorResponse(CurrencyEntity.class, e);
        }

        DefaultResponse<CurrencyEntity> defaultResponse = new DefaultResponse<>(CurrencyEntity.class);
        defaultResponse.setCount(count);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<CurrencyEntity>> findById(@PathVariable("id") Integer id) {
        List<CurrencyEntity> result = new ArrayList<>();

        try {
            Assert.notNull(id, "Entity id cannot be null");
            result = currencyService.findById(id);

        } catch (Exception e) {
            return Utils.getErrorResponse(CurrencyEntity.class, e);
        }

        DefaultResponse<CurrencyEntity> defaultResponse = new DefaultResponse<>(CurrencyEntity.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<CurrencyEntity>> save(@Valid @NotNull @RequestBody CurrencyEntity currencyEntity,
                                                                BindingResult bindingResult) {
        List<CurrencyEntity> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }

            if (currencyEntity.getCcyId() != null) {
                throw new InvalidDataException("When saving, data entity cannot have ID");
            }

            result = currencyService.save(currencyEntity);

            if (result.size() == 0) {
                throw new Exception("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(CurrencyEntity.class, e);
        }

        DefaultResponse<CurrencyEntity> defaultResponse = new DefaultResponse<>(CurrencyEntity.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<CurrencyEntity>> update(@Valid @NotNull @RequestBody CurrencyEntity currencyEntity,
                                                                  BindingResult bindingResult) {

        List<CurrencyEntity> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }
            if (currencyEntity.getCcyId() == null) {
                throw new InvalidDataException("When updating, data entity must have ID");
            }

            result = currencyService.save(currencyEntity);

            if (result.size() == 0) {
                throw new Exception("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(CurrencyEntity.class, e);
        }

        DefaultResponse<CurrencyEntity> defaultResponse = new DefaultResponse<>(CurrencyEntity.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

        try {
            Assert.notNull(id, "Entity id cannot be null");
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

}
