package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.db.domain.CompanyEntity;
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

@Api(value = "Company Controller")
@RequestMapping(value = "/api/v1",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface CompanyAPI {

    class DefaultResponseCompanyEntity extends DefaultResponse<CompanyEntity> {

        public DefaultResponseCompanyEntity() {
            super(CompanyEntity.class);
        }

    }

    @ApiOperation(value = "Find all company data",
            notes = "Returns a list of CompanyEntity",
            response = DefaultResponseCompanyEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseCompanyEntity.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryParameters", value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
    })
    @GetMapping(value = "/company")
    ResponseEntity<DefaultResponse<CompanyEntity>> findAll(@RequestParam Map<String, String> queryParameters);

    @ApiOperation(value = "Find company by ID",
            notes = "Returns a list of CompanyEntity",
            response = DefaultResponseCompanyEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseCompanyEntity.class)
    })
    @GetMapping(value = "/company/{id}")
    ResponseEntity<DefaultResponse<CompanyEntity>> findById(@PathVariable("id") Integer id);

    @ApiOperation(value = "Save company",
            notes = "Saves CompanyEntity",
            response = DefaultResponseCompanyEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseCompanyEntity.class)
    })
    @PostMapping(value = "/company")
    ResponseEntity<DefaultResponse<CompanyEntity>> save(@Valid @NotNull @RequestBody CompanyEntity companyEntity,
                                                        BindingResult bindingResult);

    @ApiOperation(value = "Update company",
            notes = "Updates CompanyEntity",
            response = DefaultResponseCompanyEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseCompanyEntity.class)
    })
    @PutMapping(value = "/company")
    ResponseEntity<DefaultResponse<CompanyEntity>> update(@Valid @NotNull @RequestBody CompanyEntity companyEntity,
                                                          BindingResult bindingResult);

    @ApiOperation(value = "Delete company by ID",
            notes = "Deletes CompanyEntity",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @DeleteMapping(value = "/company/{id}")
    ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);

    @GetMapping(value = "/company/{id}/content")
    ResponseEntity<InputStreamResource> findContentByCompanyId(@PathVariable("id") Integer id);

//    @PostMapping(value = "/company/{id}/content")
//    ResponseEntity<InputStreamResource> saveContentById(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file);

}