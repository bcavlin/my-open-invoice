package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.db.domain.RoleEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "User Controller")
@RequestMapping(value = "/api/v1",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface UserAPI {

    @ApiOperation(value = "Find user roles",
            response = DefaultResponseRoleEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseRoleEntity.class)
    })
    @GetMapping(value = "/user/{username}/roles")
    ResponseEntity<DefaultResponse<RoleEntity>> getUserRoles(@PathVariable("username") String username);

    class DefaultResponseRoleEntity extends DefaultResponse<RoleEntity> {

        public DefaultResponseRoleEntity() {
            super(RoleEntity.class);
        }

    }

}