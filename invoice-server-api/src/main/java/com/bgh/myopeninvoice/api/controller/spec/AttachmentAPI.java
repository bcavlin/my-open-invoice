package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.AttachmentDTO;
import com.bgh.myopeninvoice.common.domain.DefaultResponse;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;

@Api(value = "Attachment Controller")
@RequestMapping(value = "/api/v1")
public interface AttachmentAPI {

  @ApiOperation(
      value = "Find all attachment data",
      notes = "Returns a list of AttachmentDTO",
      response = DefaultResponseAttachmentDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseAttachmentDTO.class)
      })
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "queryParameters",
        value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
  })
  @GetMapping(value = "/attachment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<AttachmentDTO>> findAll(
      @RequestParam Map<String, String> queryParameters);

  @ApiOperation(
      value = "Find attachment by ID",
      notes = "Returns a list of AttachmentDTO",
      response = DefaultResponseAttachmentDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseAttachmentDTO.class)
      })
  @GetMapping(value = "/attachment/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<AttachmentDTO>> findById(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save attachment",
      notes = "Saves AttachmentDTO",
      response = DefaultResponseAttachmentDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseAttachmentDTO.class)
      })
  @PostMapping(value = "/attachment")
  ResponseEntity<DefaultResponse<AttachmentDTO>> save(
      @Valid @NotNull @RequestBody AttachmentDTO attachmentDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Update attachment",
      notes = "Updates AttachmentDTO",
      response = DefaultResponseAttachmentDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseAttachmentDTO.class)
      })
  @PutMapping(value = "/attachment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<AttachmentDTO>> update(
      @Valid @NotNull @RequestBody AttachmentDTO attachmentDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Delete attachment by ID",
      notes = "Deletes AttachmentDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @DeleteMapping(value = "/attachment/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Find attachment content by ID",
      notes = "Find content for AttachmentDTO",
      response = Byte[].class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @GetMapping(value = "/attachment/{id}/content")
  @ResponseBody
  ResponseEntity<byte[]> findContentByAttachmentId(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save content for attachment by ID",
      notes = "Saves content for AttachmentDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @PostMapping(
      value = "/attachment/{id}/content",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<AttachmentDTO>> saveContentByAttachmentId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) throws IOException;

  class DefaultResponseAttachmentDTO extends DefaultResponse<AttachmentDTO> {

    public DefaultResponseAttachmentDTO() {
      super(AttachmentDTO.class);
    }
  }
}
