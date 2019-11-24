package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.AccountDataDTO;
import com.bgh.myopeninvoice.common.domain.DefaultResponse;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Api(value = "AccountData Controller")
@RequestMapping(value = "/api/v1")
public interface AccountDataAPI {

  @ApiOperation(
          value = "Find all account-data data",
          notes = "Returns a list of AccountDataDTO",
          response = DefaultResponseAccountDataDTO.class)
  @ApiResponses(
          value = {
                  @ApiResponse(
                          code = 200,
                          message = "Successful operation",
                          response = DefaultResponseAccountDataDTO.class)
          })
  @ApiImplicitParams({
          @ApiImplicitParam(
                  name = "queryParameters",
                  value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
  })
  @GetMapping(value = "/account-data", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<AccountDataDTO>> findAll(
          @RequestParam Map<String, String> queryParameters);

  @ApiOperation(
          value = "Find account-data by account ID",
          notes = "Returns a list of AccountDataDTO",
          response = DefaultResponseAccountDataDTO.class)
  @ApiResponses(
          value = {
                  @ApiResponse(
                          code = 200,
                          message = "Successful operation",
                          response = DefaultResponseAccountDataDTO.class)
          })
  @GetMapping(value = "/account-data/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<AccountDataDTO>> findByAccountId(
          @PathVariable("id") Integer id, @RequestParam Map<String, String> queryParameters);

  @ApiOperation(
          value = "Find account-data by account item ID",
          notes = "Returns a list of AccountDataDTO",
          response = DefaultResponseAccountDataDTO.class)
  @ApiResponses(
          value = {
                  @ApiResponse(
                          code = 200,
                          message = "Successful operation",
                          response = DefaultResponseAccountDataDTO.class)
          })
  @GetMapping(value = "/account-data/item/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<AccountDataDTO>> findById(@PathVariable("id") Integer id);

  @ApiOperation(
          value = "Save account-data",
          notes = "Saves AccountDataDTO",
          response = DefaultResponseAccountDataDTO.class)
  @ApiResponses(
          value = {
                  @ApiResponse(
                          code = 200,
                          message = "Successful operation",
                          response = DefaultResponseAccountDataDTO.class)
          })
  @PostMapping(value = "/account-data", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<DefaultResponse<AccountDataDTO>> save(
          @Valid @NotNull @RequestBody AccountDataDTO accountDataDTO, BindingResult bindingResult);

  @ApiOperation(
          value = "Parse account-data",
          notes = "Parse AccountDataDTO",
          response = String.class)
  @ApiResponses(
          value = {
                  @ApiResponse(
                          code = 200,
                          message = "Successful operation",
                          response = DefaultResponseAccountDataDTO.class)
          })
  @PostMapping(
          value = "/account-data/parse",
          produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
          consumes = "text/plain;charset=UTF-8")
  ResponseEntity<DefaultResponse<String>> parse(
          @Valid @NotNull @RequestBody String data,
          @RequestParam(value = "firstRowHeader", required = false, defaultValue = "false")
                  Boolean firstRowHeader,
          @RequestParam(value = "separator", required = false, defaultValue = "CSV") String separator,
          @RequestParam(value = "lineSeparator", required = false, defaultValue = "CRLF")
                  String lineSeparator,
          @RequestParam(value = "provider", required = false) Integer provider,
          BindingResult bindingResult);

  class DefaultResponseAccountDataDTO extends DefaultResponse<AccountDataDTO> {

    public DefaultResponseAccountDataDTO() {
      super(AccountDataDTO.class);
    }
  }
}
