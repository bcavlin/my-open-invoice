package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.ReferencesAPI;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidParameterException;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.common.util.RateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ReferenceController extends AbstractController implements ReferencesAPI {

    private List<String> types = Arrays.asList("RATE");

    @Override
    public ResponseEntity<DefaultResponse<String>> getReferenceTypes() {
        DefaultResponse<String> defaultResponse = new DefaultResponse<>(String.class);
        defaultResponse.setCount(1L);
        defaultResponse.setDetails(types);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseEntity<DefaultResponse<String>> getReferenceType(@PathVariable("type") String type) {

        List result = new ArrayList<>();

        try {
            Assert.notNull(type, getMessageSource().getMessage("type.cannot.be.null", null, getContextLocale()));
            Assert.isTrue(!types.contains(type), getMessageSource().getMessage("type.does.not.exist", null, getContextLocale()));

            if ("RATE".equalsIgnoreCase(type)) {
                result = Arrays.stream(RateType.values())
                        .map(v -> v.name().toUpperCase()).collect(Collectors.toList());

            } else {
                throw new InvalidParameterException("Type does not exist");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(String.class, e);
        }

        DefaultResponse<String> defaultResponse = new DefaultResponse<>(String.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

}
