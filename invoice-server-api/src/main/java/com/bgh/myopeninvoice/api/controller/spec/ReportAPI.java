package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.ReportDTO;
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

@Api(value = "Report Controller")
@RequestMapping(value = "/api/v1")
public interface ReportAPI {

  @ApiOperation(
      value = "Find all report data",
      notes = "Returns a list of ReportDTO",
      response = DefaultResponseReportDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseReportDTO.class)
      })
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "queryParameters",
        value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
  })
  @GetMapping(value = "/report", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ReportDTO>> findAll(
      @RequestParam Map<String, String> queryParameters);

  @ApiOperation(
      value = "Find report by ID",
      notes = "Returns a list of ReportDTO",
      response = DefaultResponseReportDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseReportDTO.class)
      })
  @GetMapping(value = "/report/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ReportDTO>> findById(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save report",
      notes = "Saves ReportDTO",
      response = DefaultResponseReportDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseReportDTO.class)
      })
  @PostMapping(value = "/report")
  ResponseEntity<DefaultResponse<ReportDTO>> save(
          @Valid @NotNull @RequestBody ReportDTO reportDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Update report",
      notes = "Updates ReportDTO",
      response = DefaultResponseReportDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseReportDTO.class)
      })
  @PutMapping(value = "/report", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ReportDTO>> update(
          @Valid @NotNull @RequestBody ReportDTO reportDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Delete report by ID",
      notes = "Deletes ReportDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @DeleteMapping(value = "/report/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Find report content by ID",
      notes = "Find content for ReportDTO",
      response = Byte[].class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @GetMapping(value = "/report/{id}/content")
  @ResponseBody
  ResponseEntity<byte[]> findContentByReportId(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save content for report by ID",
      notes = "Saves content for ReportDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @PostMapping(
      value = "/report/{id}/content",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ReportDTO>> saveContentByReportId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file);

  class DefaultResponseReportDTO extends DefaultResponse<ReportDTO> {

    public DefaultResponseReportDTO() {
      super(ReportDTO.class);
    }
  }
}
