package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.CompanyContactDTO;
import com.bgh.myopeninvoice.common.domain.DefaultResponse;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Api(value = "CompanyContact Controller")
@RequestMapping(value = "/api/v1")
public interface CompanyContactAPI {

  @ApiOperation(
      value = "Find all companycontact data",
      notes = "Returns a list of CompanyContactDTO",
      response = DefaultResponseCompanyContactDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseCompanyContactDTO.class)
      })
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "queryParameters",
        value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
  })
  @GetMapping(value = "/companycontact", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<CompanyContactDTO>> findAll(
      @RequestParam Map<String, String> queryParameters);

  @ApiOperation(
      value = "Find companycontact by ID",
      notes = "Returns a list of CompanyContactDTO",
      response = DefaultResponseCompanyContactDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseCompanyContactDTO.class)
      })
  @GetMapping(value = "/companycontact/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<CompanyContactDTO>> findById(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save companycontact",
      notes = "Saves CompanyContactDTO",
      response = DefaultResponseCompanyContactDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseCompanyContactDTO.class)
      })
  @PostMapping(value = "/companycontact")
  ResponseEntity<DefaultResponse<CompanyContactDTO>> save(
      @Valid @NotNull @RequestBody CompanyContactDTO companycontactDTO,
      BindingResult bindingResult);

  @ApiOperation(
      value = "Update companycontact",
      notes = "Updates CompanyContactDTO",
      response = DefaultResponseCompanyContactDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseCompanyContactDTO.class)
      })
  @PutMapping(value = "/companycontact", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<CompanyContactDTO>> update(
      @Valid @NotNull @RequestBody CompanyContactDTO companycontactDTO,
      BindingResult bindingResult);

  @ApiOperation(
      value = "Delete companycontact by ID",
      notes = "Deletes CompanyContactDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @DeleteMapping(value = "/companycontact/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Find companycontact by company ID",
      notes = "ind companycontact by company ID",
      response = Byte[].class)
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "Successful operation", response = Byte[].class)})
  @GetMapping(value = "/companycontact/{id}/content")
  @ResponseBody
  ResponseEntity<byte[]> findContentByCompanyContactId(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save content for companycontact by ID",
      notes = "Saves content for CompanyContactDTO",
      response = DefaultResponseCompanyContactDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseCompanyContactDTO.class)
      })
  @PostMapping(
      value = "/companycontact/{id}/content",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<CompanyContactDTO>> saveContentByCompanyContactId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file);

  @ApiOperation(
      value = "Update bulk companycontact",
      notes = "Updates CompanyContactDTO in a bulk",
      response = DefaultResponseCompanyContactDTO[].class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseCompanyContactDTO[].class)
      })
  @PutMapping(value = "/companycontact/bulk", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<CompanyContactDTO>> updateBulk(
      @Valid @NotNull @RequestBody CompanyContactDTO[] companycontactDTO,
      BindingResult bindingResult);

  class DefaultResponseCompanyContactDTO extends DefaultResponse<CompanyContactDTO> {

    public DefaultResponseCompanyContactDTO() {
      super(CompanyContactDTO.class);
    }
  }
}
