package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.InvoiceAPI;
import com.bgh.myopeninvoice.api.domain.dto.InvoiceDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.api.service.InvoiceService;
import com.bgh.myopeninvoice.api.transformer.InvoiceTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.InvoiceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class InvoiceController extends AbstractController implements InvoiceAPI {

    private static final String ENTITY_ID_CANNOT_BE_NULL = "entity.id-cannot-be-null";

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceTransformer invoiceTransformer;

    @Override
    public ResponseEntity<DefaultResponse<InvoiceDTO>> findAll(@RequestParam Map<String, String> queryParameters) {
        List<InvoiceDTO> result = new ArrayList<>();
        long count;

        try {
            count = invoiceService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
            List<InvoiceEntity> entities = invoiceService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));
            result = invoiceTransformer.transformEntityToDTO(entities, InvoiceDTO.class);

        } catch (Exception e) {
            return Utils.getErrorResponse(InvoiceDTO.class, e);
        }

        DefaultResponse<InvoiceDTO> defaultResponse = new DefaultResponse<>(InvoiceDTO.class);
        defaultResponse.setCount(count);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<InvoiceDTO>> findById(@PathVariable("id") Integer id) {
        List<InvoiceDTO> result = new ArrayList<>();

        try {
            Assert.notNull(id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
            List<InvoiceEntity> entities = invoiceService.findById(id);
            result = invoiceTransformer.transformEntityToDTO(entities, InvoiceDTO.class);

        } catch (Exception e) {
            return Utils.getErrorResponse(InvoiceDTO.class, e);
        }

        DefaultResponse<InvoiceDTO> defaultResponse = new DefaultResponse<>(InvoiceDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<InvoiceDTO>> save(@Valid @NotNull @RequestBody InvoiceDTO invoiceDTO,
                                                            BindingResult bindingResult) {
        List<InvoiceDTO> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }

            if (invoiceDTO.getInvoiceId() != null) {
                throw new InvalidDataException(
                        getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
            }

            List<InvoiceEntity> entities = invoiceService.save(
                    invoiceTransformer.transformDTOToEntity(invoiceDTO, InvoiceEntity.class));
            result = invoiceTransformer.transformEntityToDTO(entities, InvoiceDTO.class);


            if (CollectionUtils.isEmpty(result)) {
                throw new InvalidResultDataException("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(InvoiceDTO.class, e);
        }

        DefaultResponse<InvoiceDTO> defaultResponse = new DefaultResponse<>(InvoiceDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<InvoiceDTO>> update(@Valid @NotNull @RequestBody InvoiceDTO invoiceDTO,
                                                              BindingResult bindingResult) {

        List<InvoiceDTO> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }
            if (invoiceDTO.getInvoiceId() == null) {
                throw new InvalidDataException("When updating, data entity must have ID");
            }

            List<InvoiceEntity> entities = invoiceService.save(
                    invoiceTransformer.transformDTOToEntity(invoiceDTO, InvoiceEntity.class));
            result = invoiceTransformer.transformEntityToDTO(entities, InvoiceDTO.class);

            if (CollectionUtils.isEmpty(result)) {
                throw new InvalidResultDataException("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(InvoiceDTO.class, e);
        }

        DefaultResponse<InvoiceDTO> defaultResponse = new DefaultResponse<>(InvoiceDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

        try {
            Assert.notNull(id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
            invoiceService.delete(id);

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
    public ResponseEntity<byte[]> findContentByInvoiceId(@PathVariable("id") Integer id) {
        throw new org.apache.commons.lang.NotImplementedException();
    }

    @Override
    public ResponseEntity<DefaultResponse<InvoiceDTO>> saveContentByInvoiceId(@PathVariable("id") Integer id,
                                                                              @RequestParam("file") MultipartFile file) {
        throw new org.apache.commons.lang.NotImplementedException();
    }

}
