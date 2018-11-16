package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.InvoiceItemsDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import io.swagger.annotations.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Api(value = "InvoiceItems Controller")
@RequestMapping(value = "/api/v1",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface InvoiceItemsAPI {

    class DefaultResponseInvoiceItemsDTO extends DefaultResponse<InvoiceItemsDTO> {

        public DefaultResponseInvoiceItemsDTO() {
            super(InvoiceItemsDTO.class);
        }

    }

    @ApiOperation(value = "Find all invoiceitems data",
            notes = "Returns a list of InvoiceItemsDTO",
            response = DefaultResponseInvoiceItemsDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseInvoiceItemsDTO.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryParameters", value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
    })
    @GetMapping(value = "/invoiceitems")
    ResponseEntity<DefaultResponse<InvoiceItemsDTO>> findAll(@RequestParam Map<String, String> queryParameters);

    @ApiOperation(value = "Find invoiceitems by ID",
            notes = "Returns a list of InvoiceItemsDTO",
            response = DefaultResponseInvoiceItemsDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseInvoiceItemsDTO.class)
    })
    @GetMapping(value = "/invoiceitems/{id}")
    ResponseEntity<DefaultResponse<InvoiceItemsDTO>> findById(@PathVariable("id") Integer id);

    @ApiOperation(value = "Save invoiceitems",
            notes = "Saves InvoiceItemsDTO",
            response = DefaultResponseInvoiceItemsDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseInvoiceItemsDTO.class)
    })
    @PostMapping(value = "/invoiceitems")
    ResponseEntity<DefaultResponse<InvoiceItemsDTO>> save(@Valid @NotNull @RequestBody InvoiceItemsDTO invoiceitemsDTO,
                                                        BindingResult bindingResult);

    @ApiOperation(value = "Update invoiceitems",
            notes = "Updates InvoiceItemsDTO",
            response = DefaultResponseInvoiceItemsDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseInvoiceItemsDTO.class)
    })
    @PutMapping(value = "/invoiceitems")
    ResponseEntity<DefaultResponse<InvoiceItemsDTO>> update(@Valid @NotNull @RequestBody InvoiceItemsDTO invoiceitemsDTO,
                                                          BindingResult bindingResult);

    @ApiOperation(value = "Delete invoiceitems by ID",
            notes = "Deletes InvoiceItemsDTO",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @DeleteMapping(value = "/invoiceitems/{id}")
    ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);
         
    @ApiOperation(value = "Find invoiceitems content by ID",
            notes = "Find content for InvoiceItemsDTO",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })                    
    @GetMapping(value = "/invoiceitems/{id}/content")
    ResponseEntity<InputStreamResource> findContentByInvoiceItemsId(@PathVariable("id") Integer id);

	@ApiOperation(value = "Save content for invoiceitems by ID",
            notes = "Saves content for InvoiceItemsDTO",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @PostMapping(value = "/invoiceitems/{id}/content")
    ResponseEntity<DefaultResponse<InvoiceItemsDTO>> saveContentByInvoiceItemsId(@PathVariable("id") Integer id,
                                                               @RequestParam("file") MultipartFile file);

}