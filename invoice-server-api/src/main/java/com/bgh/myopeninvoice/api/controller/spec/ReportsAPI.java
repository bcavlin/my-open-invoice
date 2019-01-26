package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.ReportsDTO;
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

@Api(value = "Reports Controller")
@RequestMapping(value = "/api/v1")
public interface ReportsAPI {

  @ApiOperation(
      value = "Find all reports data",
      notes = "Returns a list of ReportsDTO",
      response = DefaultResponseReportsDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseReportsDTO.class)
      })
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "queryParameters",
        value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
  })
  @GetMapping(value = "/reports", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ReportsDTO>> findAll(
      @RequestParam Map<String, String> queryParameters);

  @ApiOperation(
      value = "Find reports by ID",
      notes = "Returns a list of ReportsDTO",
      response = DefaultResponseReportsDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseReportsDTO.class)
      })
  @GetMapping(value = "/reports/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ReportsDTO>> findById(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save reports",
      notes = "Saves ReportsDTO",
      response = DefaultResponseReportsDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseReportsDTO.class)
      })
  @PostMapping(value = "/reports")
  ResponseEntity<DefaultResponse<ReportsDTO>> save(
      @Valid @NotNull @RequestBody ReportsDTO reportsDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Update reports",
      notes = "Updates ReportsDTO",
      response = DefaultResponseReportsDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseReportsDTO.class)
      })
  @PutMapping(value = "/reports", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ReportsDTO>> update(
      @Valid @NotNull @RequestBody ReportsDTO reportsDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Delete reports by ID",
      notes = "Deletes ReportsDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @DeleteMapping(value = "/reports/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Find reports content by ID",
      notes = "Find content for ReportsDTO",
      response = Byte[].class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @GetMapping(value = "/reports/{id}/content")
  @ResponseBody
  ResponseEntity<byte[]> findContentByReportsId(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save content for reports by ID",
      notes = "Saves content for ReportsDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @PostMapping(
      value = "/reports/{id}/content",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ReportsDTO>> saveContentByReportsId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file);

  class DefaultResponseReportsDTO extends DefaultResponse<ReportsDTO> {

    public DefaultResponseReportsDTO() {
      super(ReportsDTO.class);
    }
  }
}
