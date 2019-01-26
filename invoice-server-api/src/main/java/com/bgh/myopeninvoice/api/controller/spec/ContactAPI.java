package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.ContactDTO;
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

@Api(value = "Contact Controller")
@RequestMapping(value = "/api/v1")
public interface ContactAPI {

  @ApiOperation(
      value = "Find all contact data",
      notes = "Returns a list of ContactDTO",
      response = DefaultResponseContactDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseContactDTO.class)
      })
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "queryParameters",
        value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
  })
  @GetMapping(value = "/contact", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ContactDTO>> findAll(
      @RequestParam Map<String, String> queryParameters);

  @ApiOperation(
      value = "Find contact by ID",
      notes = "Returns a list of ContactDTO",
      response = DefaultResponseContactDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseContactDTO.class)
      })
  @GetMapping(value = "/contact/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ContactDTO>> findById(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save contact",
      notes = "Saves ContactDTO",
      response = DefaultResponseContactDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseContactDTO.class)
      })
  @PostMapping(value = "/contact")
  ResponseEntity<DefaultResponse<ContactDTO>> save(
      @Valid @NotNull @RequestBody ContactDTO contactDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Update contact",
      notes = "Updates ContactDTO",
      response = DefaultResponseContactDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseContactDTO.class)
      })
  @PutMapping(value = "/contact", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ContactDTO>> update(
      @Valid @NotNull @RequestBody ContactDTO contactDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Delete contact by ID",
      notes = "Deletes ContactDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @DeleteMapping(value = "/contact/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Find contact content by ID",
      notes = "Find content for ContactDTO",
      response = Byte[].class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @GetMapping(value = "/contact/{id}/content")
  @ResponseBody
  ResponseEntity<byte[]> findContentByContactId(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save content for contact by ID",
      notes = "Saves content for ContactDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @PostMapping(
      value = "/contact/{id}/content",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ContactDTO>> saveContentByContactId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file);

  class DefaultResponseContactDTO extends DefaultResponse<ContactDTO> {

    public DefaultResponseContactDTO() {
      super(ContactDTO.class);
    }
  }
}
