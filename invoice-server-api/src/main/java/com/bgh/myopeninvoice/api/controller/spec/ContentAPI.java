package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.ContentDTO;
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

@Api(value = "Content Controller")
@RequestMapping(value = "/api/v1",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface ContentAPI {

    class DefaultResponseContentDTO extends DefaultResponse<ContentDTO> {

        public DefaultResponseContentDTO() {
            super(ContentDTO.class);
        }

    }

    @ApiOperation(value = "Find all content data",
            notes = "Returns a list of ContentDTO",
            response = DefaultResponseContentDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseContentDTO.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryParameters", value = "page/size/sortField/sortOrder=ASC,DESC,NONE/filter")
    })
    @GetMapping(value = "/content")
    ResponseEntity<DefaultResponse<ContentDTO>> findAll(@RequestParam Map<String, String> queryParameters);

    @ApiOperation(value = "Find content by ID",
            notes = "Returns a list of ContentDTO",
            response = DefaultResponseContentDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseContentDTO.class)
    })
    @GetMapping(value = "/content/{id}")
    ResponseEntity<DefaultResponse<ContentDTO>> findById(@PathVariable("id") Integer id);

    @ApiOperation(value = "Save content",
            notes = "Saves ContentDTO",
            response = DefaultResponseContentDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseContentDTO.class)
    })
    @PostMapping(value = "/content")
    ResponseEntity<DefaultResponse<ContentDTO>> save(@Valid @NotNull @RequestBody ContentDTO contentDTO,
                                                     BindingResult bindingResult);

    @ApiOperation(value = "Update content",
            notes = "Updates ContentDTO",
            response = DefaultResponseContentDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseContentDTO.class)
    })
    @PutMapping(value = "/content")
    ResponseEntity<DefaultResponse<ContentDTO>> update(@Valid @NotNull @RequestBody ContentDTO contentDTO,
                                                       BindingResult bindingResult);

    @ApiOperation(value = "Delete content by ID",
            notes = "Deletes ContentDTO",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @DeleteMapping(value = "/content/{id}")
    ResponseEntity<DefaultResponse<Boolean>> delete(@PathVariable("id") Integer id);

    @ApiOperation(value = "Find content content by ID",
            notes = "Find content for ContentDTO",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @GetMapping(value = "/content/{id}/content")
    ResponseEntity<InputStreamResource> findContentByContentId(@PathVariable("id") Integer id);

    @ApiOperation(value = "Save content for content by ID",
            notes = "Saves content for ContentDTO",
            response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Boolean.class)
    })
    @PostMapping(value = "/content/{id}/content")
    ResponseEntity<DefaultResponse<ContentDTO>> saveContentByContentId(@PathVariable("id") Integer id,
                                                                       @RequestParam("file") MultipartFile file);

}