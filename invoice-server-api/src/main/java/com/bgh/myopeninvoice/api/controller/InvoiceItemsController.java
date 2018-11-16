package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.InvoiceItemsAPI;
import com.bgh.myopeninvoice.api.domain.dto.InvoiceItemsDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.api.service.InvoiceItemsService;
import com.bgh.myopeninvoice.api.transformer.InvoiceItemsTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.InvoiceItemsEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class InvoiceItemsController extends AbstractController implements InvoiceItemsAPI {

    private static final String ENTITY_ID_CANNOT_BE_NULL = "entity.id-cannot-be-null";

    @Autowired
    private InvoiceItemsService invoiceitemsService;

    @Autowired
    private InvoiceItemsTransformer invoiceitemsTransformer;

    @Override
    public ResponseEntity<DefaultResponse<InvoiceItemsDTO>> findAll(@RequestParam Map<String, String> queryParameters) {
        List<InvoiceItemsDTO> result = new ArrayList<>();
        long count;

        try {
            count = invoiceitemsService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
            List<InvoiceItemsEntity> entities = invoiceitemsService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));
            result = invoiceitemsTransformer.transformEntityToDTO(entities);

        } catch (Exception e) {
            return Utils.getErrorResponse(InvoiceItemsDTO.class, e);
        }

        DefaultResponse<InvoiceItemsDTO> defaultResponse = new DefaultResponse<>(InvoiceItemsDTO.class);
        defaultResponse.setCount(count);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<InvoiceItemsDTO>> findById(@PathVariable("id") Integer id) {
        List<InvoiceItemsDTO> result = new ArrayList<>();

        try {
            Assert.notNull(id, getMessageSource().getMessage("entity.id-cannot-be-null", null, getContextLocale()));
            List<InvoiceItemsEntity> entities = invoiceitemsService.findById(id);
            result = invoiceitemsTransformer.transformEntityToDTO(entities);

        } catch (Exception e) {
            return Utils.getErrorResponse(InvoiceItemsDTO.class, e);
        }

        DefaultResponse<InvoiceItemsDTO> defaultResponse = new DefaultResponse<>(InvoiceItemsDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<InvoiceItemsDTO>> save(@Valid @NotNull @RequestBody InvoiceItemsDTO invoiceitemsDTO,
                                                                 BindingResult bindingResult) {
        List<InvoiceItemsDTO> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }

            if (invoiceitemsDTO.getInvoiceItemId() != null) {
                throw new InvalidDataException(
                        getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
            }

            List<InvoiceItemsEntity> entities = invoiceitemsService.save(invoiceitemsTransformer.transformDTOToEntity(invoiceitemsDTO));
            result = invoiceitemsTransformer.transformEntityToDTO(entities);


            if (CollectionUtils.isEmpty(result)) {
                throw new InvalidResultDataException("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(InvoiceItemsDTO.class, e);
        }

        DefaultResponse<InvoiceItemsDTO> defaultResponse = new DefaultResponse<>(InvoiceItemsDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<InvoiceItemsDTO>> update(@Valid @NotNull @RequestBody InvoiceItemsDTO invoiceitemsDTO,
                                                                   BindingResult bindingResult) {

        List<InvoiceItemsDTO> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }
            if (invoiceitemsDTO.getInvoiceItemId() == null) {
                throw new InvalidDataException("When updating, data entity must have ID");
            }

            List<InvoiceItemsEntity> entities = invoiceitemsService.save(invoiceitemsTransformer.transformDTOToEntity(invoiceitemsDTO));
            result = invoiceitemsTransformer.transformEntityToDTO(entities);

            if (CollectionUtils.isEmpty(result)) {
                throw new InvalidResultDataException("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(InvoiceItemsDTO.class, e);
        }

        DefaultResponse<InvoiceItemsDTO> defaultResponse = new DefaultResponse<>(InvoiceItemsDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

        try {
            Assert.notNull(id, getMessageSource().getMessage("entity.id-cannot-be-null", null, getContextLocale()));
            invoiceitemsService.delete(id);

        } catch (Exception e) {
            return Utils.getErrorResponse(Boolean.class, e, false);
        }

        DefaultResponse<Boolean> defaultResponse = new DefaultResponse<>(Boolean.class);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        defaultResponse.setOperationMessage("Deleted entity with id: " + id);
        defaultResponse.setDetails(Collections.singletonList(true));
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<InputStreamResource> findContentByInvoiceItemsId(Integer id) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<DefaultResponse<InvoiceItemsDTO>> saveContentByInvoiceItemsId(Integer id, MultipartFile file) {
        throw new NotImplementedException();
    }

}
