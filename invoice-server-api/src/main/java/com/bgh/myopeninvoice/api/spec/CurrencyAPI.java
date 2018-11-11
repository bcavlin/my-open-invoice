package com.bgh.myopeninvoice.api.spec;

import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.db.domain.CurrencyEntity;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Api(value = "Currency Controller")
@RequestMapping(value = "/api/v1",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface CurrencyAPI {

    class DefaultResponseCurrencyEntity extends DefaultResponse<CurrencyEntity> {

        public DefaultResponseCurrencyEntity() {
            super(CurrencyEntity.class);
        }

    }

    @ApiOperation(value = "Find all currency data",
            notes = "Returns a list of CurrencyEntity",
            response = DefaultResponseCurrencyEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseCurrencyEntity.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryParameters", value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
    })
    @GetMapping(value = "/currency")
    ResponseEntity<DefaultResponse<CurrencyEntity>> findAll(@RequestParam Map<String, String> queryParameters);

    @ApiOperation(value = "Find currency by ID",
            notes = "Returns a list of CurrencyEntity",
            response = DefaultResponseCurrencyEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseCurrencyEntity.class)
    })
    @GetMapping(value = "/currency/{id}")
    ResponseEntity<DefaultResponse<CurrencyEntity>> findById(@PathVariable("id") Integer id);

    @ApiOperation(value = "Save currency",
            notes = "Saves CurrencyEntity",
            response = DefaultResponseCurrencyEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseCurrencyEntity.class)
    })
    @PostMapping(value = "/currency")
    ResponseEntity<DefaultResponse<CurrencyEntity>> save(@Valid @NotNull @RequestBody CurrencyEntity currencyEntity,
                                                         BindingResult bindingResult);

    @ApiOperation(value = "Update currency",
            notes = "Updates CurrencyEntity",
            response = DefaultResponseCurrencyEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseCurrencyEntity.class)
    })
    @PutMapping(value = "/currency")
    ResponseEntity<DefaultResponse<CurrencyEntity>> update(@Valid @NotNull @RequestBody CurrencyEntity currencyEntity,
                                                           BindingResult bindingResult);

    @ApiOperation(value = "Delete currency by ID",
            notes = "Deletes CurrencyEntity",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @DeleteMapping(value = "/currency/{id}")
    ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);

}