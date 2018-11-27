package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.InvoiceDTO;
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

@Api(value = "Invoice Controller")
@RequestMapping(value = "/api/v1")
public interface InvoiceAPI {

    class DefaultResponseInvoiceDTO extends DefaultResponse<InvoiceDTO> {

        public DefaultResponseInvoiceDTO() {
            super(InvoiceDTO.class);
        }

    }

    @ApiOperation(value = "Find all invoice data",
            notes = "Returns a list of InvoiceDTO",
            response = DefaultResponseInvoiceDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseInvoiceDTO.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryParameters", value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
    })
    @GetMapping(value = "/invoice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<DefaultResponse<InvoiceDTO>> findAll(@RequestParam Map<String, String> queryParameters);

    @ApiOperation(value = "Find invoice by ID",
            notes = "Returns a list of InvoiceDTO",
            response = DefaultResponseInvoiceDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseInvoiceDTO.class)
    })
    @GetMapping(value = "/invoice/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<DefaultResponse<InvoiceDTO>> findById(@PathVariable("id") Integer id);

    @ApiOperation(value = "Save invoice",
            notes = "Saves InvoiceDTO",
            response = DefaultResponseInvoiceDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseInvoiceDTO.class)
    })
    @PostMapping(value = "/invoice")
    ResponseEntity<DefaultResponse<InvoiceDTO>> save(@Valid @NotNull @RequestBody InvoiceDTO invoiceDTO,
                                                        BindingResult bindingResult);

    @ApiOperation(value = "Update invoice",
            notes = "Updates InvoiceDTO",
            response = DefaultResponseInvoiceDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseInvoiceDTO.class)
    })
    @PutMapping(value = "/invoice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<DefaultResponse<InvoiceDTO>> update(@Valid @NotNull @RequestBody InvoiceDTO invoiceDTO,
                                                          BindingResult bindingResult);

    @ApiOperation(value = "Delete invoice by ID",
            notes = "Deletes InvoiceDTO",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @DeleteMapping(value = "/invoice/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);
         
    @ApiOperation(value = "Find invoice content by ID",
            notes = "Find content for InvoiceDTO",
            response = Byte[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })                    
    @GetMapping(value = "/invoice/{id}/content")
    @ResponseBody
    ResponseEntity<byte[]> findContentByInvoiceId(@PathVariable("id") Integer id);

	@ApiOperation(value = "Save content for invoice by ID",
            notes = "Saves content for InvoiceDTO",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @PostMapping(value = "/invoice/{id}/content",
              consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
              produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<DefaultResponse<InvoiceDTO>> saveContentByInvoiceId(@PathVariable("id") Integer id,
                                                               @RequestParam("file") MultipartFile file);

}
