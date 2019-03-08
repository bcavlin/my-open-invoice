package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.common.domain.DefaultResponse;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Api(value = "Statistics Controller")
@RequestMapping(value = "/api/v1")
public interface StatisticsAPI {

    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<DefaultResponse<Map>> allStatistics();

}
