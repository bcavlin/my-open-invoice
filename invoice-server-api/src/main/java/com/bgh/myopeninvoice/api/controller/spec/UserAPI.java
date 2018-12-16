package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.api.domain.dto.RoleDTO;
import com.bgh.myopeninvoice.api.domain.dto.UserDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.security.AccountCredentials;
import com.bgh.myopeninvoice.api.security.JwtAuthenticationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "User Controller")
@RequestMapping(value = "/api/v1",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface UserAPI {

    @ApiOperation(value = "Find user roles",
            response = DefaultResponseRoleDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseRoleDTO.class)
    })
    @GetMapping(value = "/user/{username}/roles")
    ResponseEntity<DefaultResponse<RoleDTO>> getUserRoles(@PathVariable("username") String username);

    class DefaultResponseRoleDTO extends DefaultResponse<RoleDTO> {
        public DefaultResponseRoleDTO() {
            super(RoleDTO.class);
        }
    }

    @ApiOperation(value = "Login",
            response = JwtAuthenticationResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = JwtAuthenticationResponse.class)
    })
    @PostMapping(value = "/login")
    ResponseEntity login(@Valid @RequestBody AccountCredentials credentials,
                                                    BindingResult bindingResult);

    @ApiOperation(value = "List users",
            response = DefaultResponseUserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DefaultResponseUserDTO.class)
    })
    @GetMapping(value = "/user")
    ResponseEntity<DefaultResponse<UserDTO>> getUsers();

    class DefaultResponseUserDTO extends DefaultResponse<UserDTO> {
        public DefaultResponseUserDTO() {
            super(UserDTO.class);
        }
    }

}