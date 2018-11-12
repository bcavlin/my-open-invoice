package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.ContactAPI;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.service.ContactService;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.ContactEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ContactController implements ContactAPI {

    @Autowired
    private ContactService contactService;

    @Override
    public ResponseEntity<DefaultResponse<ContactEntity>> findAll(@RequestParam Map<String, String> queryParameters) {
        List<ContactEntity> result = new ArrayList<>();
        long count;

        try {
            count = contactService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
            result = contactService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));

        } catch (Exception e) {
            return Utils.getErrorResponse(ContactEntity.class, e);
        }

        DefaultResponse<ContactEntity> defaultResponse = new DefaultResponse<>(ContactEntity.class);
        defaultResponse.setCount(count);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<ContactEntity>> findById(@PathVariable("id") Integer id) {
        List<ContactEntity> result = new ArrayList<>();

        try {
            Assert.notNull(id, "Entity id cannot be null");
            result = contactService.findById(id);

        } catch (Exception e) {
            return Utils.getErrorResponse(ContactEntity.class, e);
        }

        DefaultResponse<ContactEntity> defaultResponse = new DefaultResponse<>(ContactEntity.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<ContactEntity>> save(@Valid @NotNull @RequestBody ContactEntity contactEntity,
                                                           BindingResult bindingResult) {
        List<ContactEntity> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }

            if (contactEntity.getContactId() != null) {
                throw new InvalidDataException("When saving, data entity cannot have ID");
            }

            result = contactService.save(contactEntity);

            if (result.size() == 0) {
                throw new Exception("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(ContactEntity.class, e);
        }

        DefaultResponse<ContactEntity> defaultResponse = new DefaultResponse<>(ContactEntity.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<ContactEntity>> update(@Valid @NotNull @RequestBody ContactEntity contactEntity,
                                                             BindingResult bindingResult) {

        List<ContactEntity> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }
            if (contactEntity.getContactId() == null) {
                throw new InvalidDataException("When updating, data entity must have ID");
            }

            result = contactService.save(contactEntity);

            if (result.size() == 0) {
                throw new Exception("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(ContactEntity.class, e);
        }

        DefaultResponse<ContactEntity> defaultResponse = new DefaultResponse<>(ContactEntity.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

        try {
            Assert.notNull(id, "Entity id cannot be null");
            contactService.delete(id);

        } catch (Exception e) {
            return Utils.getErrorResponse(Boolean.class, e, false);
        }

        DefaultResponse<Boolean> defaultResponse = new DefaultResponse<>(Boolean.class);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        defaultResponse.setOperationMessage("Deleted entity with id: " + id);
        defaultResponse.setDetails(Collections.singletonList(true));
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

}
