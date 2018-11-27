package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.CurrencyDTO;
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

@Api(value = "Currency Controller")
@RequestMapping(value = "/api/v1")
public interface CurrencyAPI {

    class DefaultResponseCurrencyDTO extends DefaultResponse<CurrencyDTO> {

        public DefaultResponseCurrencyDTO() {
            super(CurrencyDTO.class);
        }

    }

    @ApiOperation(value = "Find all currency data",
            notes = "Returns a list of CurrencyDTO",
            response = DefaultResponseCurrencyDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseCurrencyDTO.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryParameters", value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
    })
    @GetMapping(value = "/currency", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<DefaultResponse<CurrencyDTO>> findAll(@RequestParam Map<String, String> queryParameters);

    @ApiOperation(value = "Find currency by ID",
            notes = "Returns a list of CurrencyDTO",
            response = DefaultResponseCurrencyDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseCurrencyDTO.class)
    })
    @GetMapping(value = "/currency/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<DefaultResponse<CurrencyDTO>> findById(@PathVariable("id") Integer id);

    @ApiOperation(value = "Save currency",
            notes = "Saves CurrencyDTO",
            response = DefaultResponseCurrencyDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseCurrencyDTO.class)
    })
    @PostMapping(value = "/currency")
    ResponseEntity<DefaultResponse<CurrencyDTO>> save(@Valid @NotNull @RequestBody CurrencyDTO currencyDTO,
                                                        BindingResult bindingResult);

    @ApiOperation(value = "Update currency",
            notes = "Updates CurrencyDTO",
            response = DefaultResponseCurrencyDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseCurrencyDTO.class)
    })
    @PutMapping(value = "/currency", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<DefaultResponse<CurrencyDTO>> update(@Valid @NotNull @RequestBody CurrencyDTO currencyDTO,
                                                          BindingResult bindingResult);

    @ApiOperation(value = "Delete currency by ID",
            notes = "Deletes CurrencyDTO",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @DeleteMapping(value = "/currency/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);
         
    @ApiOperation(value = "Find currency content by ID",
            notes = "Find content for CurrencyDTO",
            response = Byte[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })                    
    @GetMapping(value = "/currency/{id}/content")
    @ResponseBody
    ResponseEntity<byte[]> findContentByCurrencyId(@PathVariable("id") Integer id);

	@ApiOperation(value = "Save content for currency by ID",
            notes = "Saves content for CurrencyDTO",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @PostMapping(value = "/currency/{id}/content",
              consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
              produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<DefaultResponse<CurrencyDTO>> saveContentByCurrencyId(@PathVariable("id") Integer id,
                                                               @RequestParam("file") MultipartFile file);

}
