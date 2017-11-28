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

import com.bgh.myopeninvoice.api.model.user.LoggedUserDetailsResponse;
import com.bgh.myopeninvoice.api.model.user.LogoutRespose;
import com.bgh.myopeninvoice.api.utils.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.bgh.myopeninvoice.api.model.response.OperationResponse.ResponseStatusEnum.ERROR;
import static com.bgh.myopeninvoice.api.model.response.OperationResponse.ResponseStatusEnum.SUCCESS;

@RestController
@Api(tags = {"Users"})
@Slf4j
public class UserController {

    private TokenStore tokenStore;

    @Autowired
    @Qualifier("customTokenStore")
    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Will return details of a logged user", response =
            LoggedUserDetailsResponse.class)})
    @RequestMapping(value = "/me", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoggedUserDetailsResponse currentLoggedUser() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication authentication = context.getAuthentication();
        LoggedUserDetailsResponse resp = new LoggedUserDetailsResponse(authentication);
        resp.setOperationStatus(SUCCESS);
        resp.setOperationMessage("Dummy me Success");
        return resp;
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Will remove token from the token store", response =
            LogoutRespose.class)})
    @RequestMapping(value = "/oauth/revoke-token", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public LogoutRespose logout(HttpServletRequest request) {
        LogoutRespose resp = new LogoutRespose();
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            CommonUtils.removeToken(authHeader, tokenStore);
            log.info("Token removed: " + authHeader);
            resp.setOperationStatus(SUCCESS);
            resp.setOperationMessage("Auth header removed: " + authHeader);
        } else {
            resp.setOperationStatus(ERROR);
            resp.setOperationMessage("There was a problem in removing the authHeader: " + authHeader);
        }
        return resp;
    }
}
