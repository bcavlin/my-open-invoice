package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.ContractDTO;
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

@Api(value = "Contract Controller")
@RequestMapping(value = "/api/v1")
public interface ContractAPI {

  @ApiOperation(
      value = "Find all contract data",
      notes = "Returns a list of ContractDTO",
      response = DefaultResponseContractDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseContractDTO.class)
      })
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "queryParameters",
        value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
  })
  @GetMapping(value = "/contract", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ContractDTO>> findAll(
      @RequestParam Map<String, String> queryParameters);

  @ApiOperation(
      value = "Find contract by ID",
      notes = "Returns a list of ContractDTO",
      response = DefaultResponseContractDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseContractDTO.class)
      })
  @GetMapping(value = "/contract/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ContractDTO>> findById(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save contract",
      notes = "Saves ContractDTO",
      response = DefaultResponseContractDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseContractDTO.class)
      })
  @PostMapping(value = "/contract")
  ResponseEntity<DefaultResponse<ContractDTO>> save(
      @Valid @NotNull @RequestBody ContractDTO contractDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Update contract",
      notes = "Updates ContractDTO",
      response = DefaultResponseContractDTO.class)
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Successful operation",
            response = DefaultResponseContractDTO.class)
      })
  @PutMapping(value = "/contract", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ContractDTO>> update(
      @Valid @NotNull @RequestBody ContractDTO contractDTO, BindingResult bindingResult);

  @ApiOperation(
      value = "Delete contract by ID",
      notes = "Deletes ContractDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @DeleteMapping(value = "/contract/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Find contract content by ID",
      notes = "Find content for ContractDTO",
      response = Byte[].class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @GetMapping(value = "/contract/{id}/content")
  @ResponseBody
  ResponseEntity<byte[]> findContentByContractId(@PathVariable("id") Integer id);

  @ApiOperation(
      value = "Save content for contract by ID",
      notes = "Saves content for ContractDTO",
      response = Boolean.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
      })
  @PostMapping(
      value = "/contract/{id}/content",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<ContractDTO>> saveContentByContractId(
      @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) throws IOException;

  class DefaultResponseContractDTO extends DefaultResponse<ContractDTO> {

    public DefaultResponseContractDTO() {
      super(ContractDTO.class);
    }
  }
}
