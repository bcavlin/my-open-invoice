package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.db.domain.ContactEntity;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Api(value = "Contact Controller")
@RequestMapping(value = "/api/v1",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface ContactAPI {

    class DefaultResponseContactEntity extends DefaultResponse<ContactEntity> {

        public DefaultResponseContactEntity() {
            super(ContactEntity.class);
        }

    }

    @ApiOperation(value = "Find all contact data",
            notes = "Returns a list of ContactEntity",
            response = DefaultResponseContactEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseContactEntity.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryParameters", value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
    })
    @GetMapping(value = "/contact")
    ResponseEntity<DefaultResponse<ContactEntity>> findAll(@RequestParam Map<String, String> queryParameters);

    @ApiOperation(value = "Find contact by ID",
            notes = "Returns a list of ContactEntity",
            response = DefaultResponseContactEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseContactEntity.class)
    })
    @GetMapping(value = "/contact/{id}")
    ResponseEntity<DefaultResponse<ContactEntity>> findById(@PathVariable("id") Integer id);

    @ApiOperation(value = "Save contact",
            notes = "Saves ContactEntity",
            response = DefaultResponseContactEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseContactEntity.class)
    })
    @PostMapping(value = "/contact")
    ResponseEntity<DefaultResponse<ContactEntity>> save(@Valid @NotNull @RequestBody ContactEntity contactEntity,
                                                    BindingResult bindingResult);

    @ApiOperation(value = "Update contact",
            notes = "Updates ContactEntity",
            response = DefaultResponseContactEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseContactEntity.class)
    })
    @PutMapping(value = "/contact")
    ResponseEntity<DefaultResponse<ContactEntity>> update(@Valid @NotNull @RequestBody ContactEntity contactEntity,
                                                      BindingResult bindingResult);

    @ApiOperation(value = "Delete contact by ID",
            notes = "Deletes ContactEntity",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @DeleteMapping(value = "/contact/{id}")
    ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);
}