package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.RoleDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.security.AccountCredentials;
import com.bgh.myopeninvoice.api.security.JwtAuthenticationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "References Controller")
@RequestMapping(value = "/api/v1",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface ReferencesAPI {

    @GetMapping(value = "/reference/type")
    ResponseEntity<DefaultResponse<String>> getReferenceTypes();

    @GetMapping(value = "/reference/type/{type}")
    ResponseEntity<DefaultResponse<String>> getReferenceType(@PathVariable("type") String type);

}
