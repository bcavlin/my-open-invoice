package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "References Controller")
@RequestMapping(value = "/api/v1",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface ReferencesAPI {

    @GetMapping(value = "/reference/type")
    ResponseEntity<DefaultResponse<String>> getReferenceTypes();

    @GetMapping(value = "/reference/type/{type}")
    ResponseEntity<DefaultResponse<String>> getReferenceType(@PathVariable("type") String type);

}
