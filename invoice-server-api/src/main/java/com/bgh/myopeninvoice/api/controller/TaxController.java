package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.service.TaxService;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.TaxEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TaxController {

    @Autowired
    private TaxService taxService;

    @GetMapping(value = "/tax", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DefaultResponse> findAllTaxEntities(@RequestParam Map<String, String> queryParameters) {
        List<TaxEntity> taxEntities = null;
        long taxEntitiesCount;

        try {
            taxEntitiesCount = taxService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
            taxEntities = taxService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));

        } catch (Exception e) {
            log.error(e.toString(), e);
            DefaultResponse<String> defaultResponse = new DefaultResponse<>();
            defaultResponse.setCount(0L);
            defaultResponse.setOperationMessage(e.toString());
            defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.ERROR);
            return new ResponseEntity<>(defaultResponse, HttpStatus.BAD_REQUEST);
        }

        DefaultResponse<TaxEntity> defaultResponse = new DefaultResponse<>();
        defaultResponse.setCount(taxEntitiesCount);
        defaultResponse.setDetails(taxEntities);
        defaultResponse.setObjectType(TaxEntity.class.getSimpleName());
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

}
