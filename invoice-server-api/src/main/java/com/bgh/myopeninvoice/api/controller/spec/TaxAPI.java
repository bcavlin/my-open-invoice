package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.TaxDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import io.swagger.annotations.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Api(value = "Tax Controller")
@RequestMapping(value = "/api/v1",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface TaxAPI {

    class DefaultResponseTaxDTO extends DefaultResponse<TaxDTO> {

        public DefaultResponseTaxDTO() {
            super(TaxDTO.class);
        }

    }

    @ApiOperation(value = "Find all tax data",
            notes = "Returns a list of TaxDTO",
            response = DefaultResponseTaxDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseTaxDTO.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryParameters", value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
    })
    @GetMapping(value = "/tax")
    ResponseEntity<DefaultResponse<TaxDTO>> findAll(@RequestParam Map<String, String> queryParameters);

    @ApiOperation(value = "Find tax by ID",
            notes = "Returns a list of TaxDTO",
            response = DefaultResponseTaxDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseTaxDTO.class)
    })
    @GetMapping(value = "/tax/{id}")
    ResponseEntity<DefaultResponse<TaxDTO>> findById(@PathVariable("id") Integer id);

    @ApiOperation(value = "Save tax",
            notes = "Saves TaxDTO",
            response = DefaultResponseTaxDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseTaxDTO.class)
    })
    @PostMapping(value = "/tax")
    ResponseEntity<DefaultResponse<TaxDTO>> save(@Valid @NotNull @RequestBody TaxDTO taxDTO,
                                                        BindingResult bindingResult);

    @ApiOperation(value = "Update tax",
            notes = "Updates TaxDTO",
            response = DefaultResponseTaxDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseTaxDTO.class)
    })
    @PutMapping(value = "/tax")
    ResponseEntity<DefaultResponse<TaxDTO>> update(@Valid @NotNull @RequestBody TaxDTO taxDTO,
                                                          BindingResult bindingResult);

    @ApiOperation(value = "Delete tax by ID",
            notes = "Deletes TaxDTO",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @DeleteMapping(value = "/tax/{id}")
    ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);
         
    @ApiOperation(value = "Find tax content by ID",
            notes = "Find content for TaxDTO",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })                    
    @GetMapping(value = "/tax/{id}/content")
    ResponseEntity<InputStreamResource> findContentByTaxId(@PathVariable("id") Integer id);

	@ApiOperation(value = "Save content for tax by ID",
            notes = "Saves content for TaxDTO",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @PostMapping(value = "/tax/{id}/content")
    ResponseEntity<DefaultResponse<TaxDTO>> saveContentByTaxId(@PathVariable("id") Integer id,
                                                               @RequestParam("file") MultipartFile file);

}