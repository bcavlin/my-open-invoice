/*
 * Copyright 2017 Branislav Cavlin
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

import com.bgh.myopeninvoice.api.model.session.SessionItem;
import com.bgh.myopeninvoice.api.model.session.SessionResponse;
import com.bgh.myopeninvoice.api.model.user.Login;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

import static com.bgh.myopeninvoice.api.model.response.OperationResponse.ResponseStatusEnum.ERROR;
import static com.bgh.myopeninvoice.api.model.response.OperationResponse.ResponseStatusEnum.SUCCESS;

@Slf4j
@RestController
@Api(tags = {"Authentication"})
public class SessionController {

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Will return a security token, which must be passed in every request", response = SessionResponse.class)})
    @RequestMapping(value = "/session", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SessionResponse login(@RequestBody Login login, HttpServletRequest request, HttpServletResponse response) {
        log.info("Session Called username: {}", login.getUsername());
        Collection<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ADMIN"));
        User user = new User(login.getUsername(), login.getPassword(), roles);
        SessionResponse resp = new SessionResponse();
        if (user != null) {
            log.info("User Details: {}", user.getUsername());
            resp.setOperationStatus(SUCCESS);
            resp.setOperationMessage("Dummy Login Success");
            SessionItem sessionItem = new SessionItem();
            resp.setItem(user);
        } else {
            resp.setOperationStatus(ERROR);
            resp.setOperationMessage("Login Failed");
        }
        return resp;
    }

}
