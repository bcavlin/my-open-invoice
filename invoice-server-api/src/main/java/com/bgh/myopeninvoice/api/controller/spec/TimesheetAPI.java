package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.TimesheetDTO;
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

@Api(value = "Timesheet Controller")
@RequestMapping(value = "/api/v1")
public interface TimesheetAPI {

  @ApiOperation(
      value = "Find all timesheet data",
      notes = "Returns a list of TimesheetDTO",
      response = DefaultResponseTimesheetDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseTimesheetDTO.class)
      })
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "queryParameters",
        value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
  })
  @GetMapping(value = "/timesheet", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<TimesheetDTO>> findAll(
      @RequestParam Map<String, String> queryParameters);

  @ApiOperation(
      value = "Find timesheet by ID",
      notes = "Returns a list of TimesheetDTO",
      response = DefaultResponseTimesheetDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseTimesheetDTO.class)
      })
  @GetMapping(value = "/timesheet/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<TimesheetDTO>> findById(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save timesheet",
      notes = "Saves TimesheetDTO",
      response = DefaultResponseTimesheetDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseTimesheetDTO.class)
      })
  @PostMapping(value = "/timesheet")
  ResponseEntity<DefaultResponse<TimesheetDTO>> save(
          @Valid @NotNull @RequestBody TimesheetDTO timesheetDTO, BindingResult bindingResult);

  @ApiOperation(
          value = "Save all timesheets",
          notes = "Saves TimesheetDTO[]",
          response = DefaultResponseTimesheetDTO.class)
  @ApiResponses(
          value = {
                  @ApiResponse(
                          code = 200,
                          message = "Successful operation",
                          response = DefaultResponseTimesheetDTO.class)
          })
  @PostMapping(value = "/timesheets")
  ResponseEntity<DefaultResponse<TimesheetDTO>> saveAll(
          @Valid @NotNull @RequestBody TimesheetDTO[] timesheetDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Update timesheet",
      notes = "Updates TimesheetDTO",
      response = DefaultResponseTimesheetDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseTimesheetDTO.class)
      })
  @PutMapping(value = "/timesheet", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<TimesheetDTO>> update(
          @Valid @NotNull @RequestBody TimesheetDTO timesheetDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Delete timesheet by ID",
      notes = "Deletes TimesheetDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @DeleteMapping(value = "/timesheet/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Find timesheet content by ID",
      notes = "Find content for TimesheetDTO",
      response = Byte[].class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @GetMapping(value = "/timesheet/{id}/content")
  @ResponseBody
  ResponseEntity<byte[]> findContentByTimesheetId(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save content for timesheet by ID",
      notes = "Saves content for TimesheetDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @PostMapping(
      value = "/timesheet/{id}/content",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<TimesheetDTO>> saveContentByTimesheetId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file);

  class DefaultResponseTimesheetDTO extends DefaultResponse<TimesheetDTO> {
    public DefaultResponseTimesheetDTO() {
      super(TimesheetDTO.class);
    }
  }
}
