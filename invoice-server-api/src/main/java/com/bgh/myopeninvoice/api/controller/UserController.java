/*
 * Copyright 2018 Branislav Cavlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.UserAPI;
import com.bgh.myopeninvoice.api.domain.dto.RoleDTO;
import com.bgh.myopeninvoice.api.domain.dto.UserDTO;
import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.exception.InvalidDataException;
import com.bgh.myopeninvoice.api.security.AccountCredentials;
import com.bgh.myopeninvoice.api.security.JwtAuthenticationResponse;
import com.bgh.myopeninvoice.api.security.JwtTokenProvider;
import com.bgh.myopeninvoice.api.service.UserService;
import com.bgh.myopeninvoice.api.transformer.RoleTransformer;
import com.bgh.myopeninvoice.api.transformer.UserTransformer;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.RoleEntity;
import com.bgh.myopeninvoice.db.domain.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class UserController extends AbstractController implements UserAPI {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleTransformer roleTransformer;

    @Autowired
    private UserTransformer userTransformer;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<DefaultResponse<RoleDTO>> getUserRoles(@PathVariable("username") String username) {
        List<RoleDTO> result = new ArrayList<>();

        try {
            List<RoleEntity> userRoles = userService.findUserRoles(username);
            result = roleTransformer.transformEntityToDTO(userRoles, RoleDTO.class);

        } catch (Exception e) {
            return Utils.getErrorResponse(RoleDTO.class, e);

        }

        DefaultResponse<RoleDTO> defaultResponse = new DefaultResponse<>(RoleDTO.class);
        defaultResponse.setDetails(result);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        ResponseEntity<DefaultResponse<RoleDTO>> responseEntity = new ResponseEntity<>(defaultResponse, HttpStatus.OK);

        log.debug("Returning: {}", responseEntity);

        return responseEntity;
    }

    @Override
    public ResponseEntity<?> login(@Valid @RequestBody AccountCredentials credentials,
                                   BindingResult bindingResult) {

        try {
            if (bindingResult.hasErrors()) {
                String collect = bindingResult.getAllErrors().stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidDataException(collect);
            }

            log.info("Logging in user: {}", credentials.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);

            userService.updateLastLoggedDate(credentials.getUsername());

            return ResponseEntity.ok(JwtAuthenticationResponse.builder().accessToken(jwt).build());

        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return ResponseEntity.badRequest().body("{\"message\":\"There was a problem logging in. Please call technical support.\"}");
    }

    @Override
    public ResponseEntity<DefaultResponse<UserDTO>> getUsers() {
        List<UserDTO> result = new ArrayList<>();

        try {
            List<UserEntity> users = userService.getUsers();
            result = userTransformer.transformEntityToDTO(users, UserDTO.class);

        } catch (Exception e) {
            return Utils.getErrorResponse(UserDTO.class, e);

        }

        DefaultResponse<UserDTO> defaultResponse = new DefaultResponse<>(UserDTO.class);
        defaultResponse.setDetails(result);
        defaultResponse.setCount((long) result.size());
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        ResponseEntity<DefaultResponse<UserDTO>> responseEntity = new ResponseEntity<>(defaultResponse, HttpStatus.OK);

        log.debug("Returning: {}", responseEntity);

        return responseEntity;
    }

}