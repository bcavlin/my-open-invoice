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

import com.bgh.myopeninvoice.api.domain.response.DefaultResponse;
import com.bgh.myopeninvoice.api.domain.response.OperationResponse;
import com.bgh.myopeninvoice.api.service.UserService;
import com.bgh.myopeninvoice.api.spec.UserAPI;
import com.bgh.myopeninvoice.api.util.Utils;
import com.bgh.myopeninvoice.db.domain.RoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class UserController implements UserAPI {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<DefaultResponse<RoleEntity>> getUserRoles(@PathVariable("username") String username) {
        List<RoleEntity> roleEntities;

        try {
            roleEntities = userService.findUserRoles(username);
        } catch (Exception e) {
            return Utils.getErrorResponse(RoleEntity.class, e);

        }

        DefaultResponse<RoleEntity> defaultResponse = new DefaultResponse<>(RoleEntity.class);
        defaultResponse.setDetails(roleEntities);
        defaultResponse.setCount((long) roleEntities.size());
        defaultResponse.setOperationStatus(OperationResponse.OperationResponseStatus.SUCCESS);
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

}