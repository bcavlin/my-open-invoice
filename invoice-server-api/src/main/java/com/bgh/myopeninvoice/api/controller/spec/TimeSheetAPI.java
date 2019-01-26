package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.TimeSheetDTO;
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

@Api(value = "TimeSheet Controller")
@RequestMapping(value = "/api/v1")
public interface TimeSheetAPI {

  @ApiOperation(
      value = "Find all timesheet data",
      notes = "Returns a list of TimeSheetDTO",
      response = DefaultResponseTimeSheetDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseTimeSheetDTO.class)
      })
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "queryParameters",
        value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
  })
  @GetMapping(value = "/timesheet", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<TimeSheetDTO>> findAll(
      @RequestParam Map<String, String> queryParameters);

  @ApiOperation(
      value = "Find timesheet by ID",
      notes = "Returns a list of TimeSheetDTO",
      response = DefaultResponseTimeSheetDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseTimeSheetDTO.class)
      })
  @GetMapping(value = "/timesheet/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<TimeSheetDTO>> findById(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save timesheet",
      notes = "Saves TimeSheetDTO",
      response = DefaultResponseTimeSheetDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseTimeSheetDTO.class)
      })
  @PostMapping(value = "/timesheet")
  ResponseEntity<DefaultResponse<TimeSheetDTO>> save(
      @Valid @NotNull @RequestBody TimeSheetDTO timesheetDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Update timesheet",
      notes = "Updates TimeSheetDTO",
      response = DefaultResponseTimeSheetDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseTimeSheetDTO.class)
      })
  @PutMapping(value = "/timesheet", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<TimeSheetDTO>> update(
      @Valid @NotNull @RequestBody TimeSheetDTO timesheetDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Delete timesheet by ID",
      notes = "Deletes TimeSheetDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @DeleteMapping(value = "/timesheet/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Find timesheet content by ID",
      notes = "Find content for TimeSheetDTO",
      response = Byte[].class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @GetMapping(value = "/timesheet/{id}/content")
  @ResponseBody
  ResponseEntity<byte[]> findContentByTimeSheetId(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save content for timesheet by ID",
      notes = "Saves content for TimeSheetDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @PostMapping(
      value = "/timesheet/{id}/content",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<TimeSheetDTO>> saveContentByTimeSheetId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file);

  class DefaultResponseTimeSheetDTO extends DefaultResponse<TimeSheetDTO> {

    public DefaultResponseTimeSheetDTO() {
      super(TimeSheetDTO.class);
    }
  }
}
