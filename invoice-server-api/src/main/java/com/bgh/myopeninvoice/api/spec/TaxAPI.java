package com.bgh.myopeninvoice.api.spec;

import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.db.domain.TaxEntity;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Api(value = "Tax Controller")
@RequestMapping(value = "/api/v1",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface TaxAPI {

    class DefaultResponseTaxEntity extends DefaultResponse<TaxEntity> {

        public DefaultResponseTaxEntity() {
            super(TaxEntity.class);
        }

    }

    @ApiOperation(value = "Find all tax data",
            notes = "Returns a list of TaxEntity",
            response = DefaultResponseTaxEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseTaxEntity.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryParameters", value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
    })
    @GetMapping(value = "/tax")
    ResponseEntity<DefaultResponse<TaxEntity>> findAll(@RequestParam Map<String, String> queryParameters);

    @ApiOperation(value = "Find tax by ID",
            notes = "Returns a list of TaxEntity",
            response = DefaultResponseTaxEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseTaxEntity.class)
    })
    @GetMapping(value = "/tax/{id}")
    ResponseEntity<DefaultResponse<TaxEntity>> findById(@PathVariable("id") Integer id);

    @ApiOperation(value = "Save tax",
            notes = "Saves TaxEntity",
            response = DefaultResponseTaxEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseTaxEntity.class)
    })
    @PostMapping(value = "/tax")
    ResponseEntity<DefaultResponse<TaxEntity>> save(@Valid @NotNull @RequestBody TaxEntity taxEntity,
                                                    BindingResult bindingResult);

    @ApiOperation(value = "Update tax",
            notes = "Updates TaxEntity",
            response = DefaultResponseTaxEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseTaxEntity.class)
    })
    @PutMapping(value = "/tax")
    ResponseEntity<DefaultResponse<TaxEntity>> update(@Valid @NotNull @RequestBody TaxEntity taxEntity,
                                                      BindingResult bindingResult);

    @ApiOperation(value = "Delete tax by ID",
            notes = "Deletes TaxEntity",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @DeleteMapping(value = "/tax/{id}")
    ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);
}