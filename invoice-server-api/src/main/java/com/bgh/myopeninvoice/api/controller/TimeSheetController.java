package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.TimeSheetAPI;
import com.bgh.myopeninvoice.api.domain.dto.TimeSheetDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.exception.InvalidResultDataException;
import com.bgh.myopeninvoice.api.service.TimeSheetService;
import com.bgh.myopeninvoice.api.transformer.TimeSheetTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.TimeSheetEntity;
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
public class TimeSheetController extends AbstractController implements TimeSheetAPI {

    private static final String ENTITY_ID_CANNOT_BE_NULL = "entity.id-cannot-be-null";

    @Autowired
    private TimeSheetService timesheetService;

    @Autowired
    private TimeSheetTransformer timesheetTransformer;

    @Override
    public ResponseEntity<DefaultResponse<TimeSheetDTO>> findAll(@RequestParam Map<String, String> queryParameters) {
        List<TimeSheetDTO> result = new ArrayList<>();
        long count;

        try {
            count = timesheetService.count(Utils.mapQueryParametersToSearchParameters(queryParameters));
            List<TimeSheetEntity> entities = timesheetService.findAll(Utils.mapQueryParametersToSearchParameters(queryParameters));
            result = timesheetTransformer.transformEntityToDTO(entities);

        } catch (Exception e) {
            return Utils.getErrorResponse(TimeSheetDTO.class, e);
        }

        DefaultResponse<TimeSheetDTO> defaultResponse = new DefaultResponse<>(TimeSheetDTO.class);
        defaultResponse.setCount(count);
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<TimeSheetDTO>> findById(@PathVariable("id") Integer id) {
        List<TimeSheetDTO> result = new ArrayList<>();

        try {
            Assert.notNull(id, getMessageSource().getMessage(ENTITY_ID_CANNOT_BE_NULL, null, getContextLocale()));
            List<TimeSheetEntity> entities = timesheetService.findById(id);
            result = timesheetTransformer.transformEntityToDTO(entities);

        } catch (Exception e) {
            return Utils.getErrorResponse(TimeSheetDTO.class, e);
        }

        DefaultResponse<TimeSheetDTO> defaultResponse = new DefaultResponse<>(TimeSheetDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<TimeSheetDTO>> save(@Valid @NotNull @RequestBody TimeSheetDTO timesheetDTO,
                                                              BindingResult bindingResult) {
        List<TimeSheetDTO> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }

            if (timesheetDTO.getTimesheetId() != null) {
                throw new InvalidDataException(
                        getMessageSource().getMessage("entity.save-cannot-have-id", null, getContextLocale()));
            }

            List<TimeSheetEntity> entities = timesheetService.save(timesheetTransformer.transformDTOToEntity(timesheetDTO));
            result = timesheetTransformer.transformEntityToDTO(entities);


            if (CollectionUtils.isEmpty(result)) {
                throw new InvalidResultDataException("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(TimeSheetDTO.class, e);
        }

        DefaultResponse<TimeSheetDTO> defaultResponse = new DefaultResponse<>(TimeSheetDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<TimeSheetDTO>> update(@Valid @NotNull @RequestBody TimeSheetDTO timesheetDTO,
                                                                BindingResult bindingResult) {

        List<TimeSheetDTO> result = new ArrayList<>();

        try {

            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }
            if (timesheetDTO.getTimesheetId() == null) {
                throw new InvalidDataException("When updating, data entity must have ID");
            }

            List<TimeSheetEntity> entities = timesheetService.save(timesheetTransformer.transformDTOToEntity(timesheetDTO));
            result = timesheetTransformer.transformEntityToDTO(entities);

            if (CollectionUtils.isEmpty(result)) {
                throw new InvalidResultDataException("Data not saved");
            }

        } catch (Exception e) {
            return Utils.getErrorResponse(TimeSheetDTO.class, e);
        }

        DefaultResponse<TimeSheetDTO> defaultResponse = new DefaultResponse<>(TimeSheetDTO.class);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setDetails(result);
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") @NotNull Integer id) {

        try {
            Assert.notNull(id, getMessageSource().getMessage("entity.id-cannot-be-null", null, getContextLocale()));
            timesheetService.delete(id);

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
    public ResponseEntity<InputStreamResource> findContentByTimeSheetId(Integer id) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<DefaultResponse<TimeSheetDTO>> saveContentByTimeSheetId(Integer id, MultipartFile file) {
        throw new NotImplementedException();
    }

}
