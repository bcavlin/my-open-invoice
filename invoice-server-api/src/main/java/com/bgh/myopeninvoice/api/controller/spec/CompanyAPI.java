package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.CompanyDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Api(value = "Company Controller")
@RequestMapping(value = "/api/v1")
public interface CompanyAPI {

  @ApiOperation(
      value = "Find all company data",
      notes = "Returns a list of CompanyDTO",
      response = DefaultResponseCompanyDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseCompanyDTO.class)
      })
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "queryParameters",
        value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
  })
  @GetMapping(value = "/company", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<CompanyDTO>> findAll(
      @RequestParam Map<String, String> queryParameters);

  @ApiOperation(
      value = "Find company by ID",
      notes = "Returns a list of CompanyDTO",
      response = DefaultResponseCompanyDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseCompanyDTO.class)
      })
  @GetMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<CompanyDTO>> findById(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save company",
      notes = "Saves CompanyDTO",
      response = DefaultResponseCompanyDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseCompanyDTO.class)
      })
  @PostMapping(value = "/company")
  ResponseEntity<DefaultResponse<CompanyDTO>> save(
      @Valid @NotNull @RequestBody CompanyDTO companyDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Update company",
      notes = "Updates CompanyDTO",
      response = DefaultResponseCompanyDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseCompanyDTO.class)
      })
  @PutMapping(value = "/company", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<CompanyDTO>> update(
      @Valid @NotNull @RequestBody CompanyDTO companyDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Delete company by ID",
      notes = "Deletes CompanyDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @DeleteMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Find company content by ID",
      notes = "Find content for CompanyDTO",
      response = Byte[].class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @GetMapping(value = "/company/{id}/content")
  @ResponseBody
  ResponseEntity<byte[]> findContentByCompanyId(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save content for company by ID",
      notes = "Saves content for CompanyDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @PostMapping(
      value = "/company/{id}/content",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<CompanyDTO>> saveContentByCompanyId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file);

  class DefaultResponseCompanyDTO extends DefaultResponse<CompanyDTO> {

    public DefaultResponseCompanyDTO() {
      super(CompanyDTO.class);
    }
  }
}
