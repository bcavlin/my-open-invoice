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

package com.bgh.myopeninvoice.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    //needed this for update to the default schema
    private static final String DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT = "insert into invoice.oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT = "select token_id, token from invoice.oauth_access_token where token_id = ?";
    private static final String DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT = "select token_id, authentication from invoice.oauth_access_token where token_id = ?";
    private static final String DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT = "select token_id, token from invoice.oauth_access_token where authentication_id = ?";
    private static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_AND_CLIENT_SELECT_STATEMENT = "select token_id, token from invoice.oauth_access_token where user_name = ? and client_id = ?";
    private static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT = "select token_id, token from invoice.oauth_access_token where user_name = ?";
    private static final String DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT = "select token_id, token from invoice.oauth_access_token where client_id = ?";
    private static final String DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT = "delete from invoice.oauth_access_token where token_id = ?";
    private static final String DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT = "delete from invoice.oauth_access_token where refresh_token = ?";
    private static final String DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT = "insert into invoice.oauth_refresh_token (token_id, token, authentication) values (?, ?, ?)";
    private static final String DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT = "select token_id, token from invoice.oauth_refresh_token where token_id = ?";
    private static final String DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT = "select token_id, authentication from invoice.oauth_refresh_token where token_id = ?";
    private static final String DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT = "delete from invoice.oauth_refresh_token where token_id = ?";

    private DataSource dataSource;
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("comboDataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
//    @Qualifier("authenticationManagerBean")
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("customUserDetailsService")
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean(name = "customTokenStore")
    public TokenStore tokenStore() {

        final JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
        jdbcTokenStore.setDeleteAccessTokenFromRefreshTokenSql(DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT);
        jdbcTokenStore.setDeleteAccessTokenSql(DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT);
        jdbcTokenStore.setDeleteRefreshTokenSql(DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT);
        jdbcTokenStore.setInsertAccessTokenSql(DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT);
        jdbcTokenStore.setInsertRefreshTokenSql(DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT);
        jdbcTokenStore.setSelectAccessTokenAuthenticationSql(DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT);
        jdbcTokenStore.setSelectAccessTokenFromAuthenticationSql(DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT);
        jdbcTokenStore.setSelectAccessTokensFromClientIdSql(DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT);
        jdbcTokenStore.setSelectAccessTokensFromUserNameAndClientIdSql(DEFAULT_ACCESS_TOKENS_FROM_USERNAME_AND_CLIENT_SELECT_STATEMENT);
        jdbcTokenStore.setSelectAccessTokensFromUserNameSql(DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT);
        jdbcTokenStore.setSelectAccessTokenSql(DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT);
        jdbcTokenStore.setSelectRefreshTokenAuthenticationSql(DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT);
        jdbcTokenStore.setSelectRefreshTokenSql(DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT);
        return jdbcTokenStore;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {

        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("acme")
                .secret("acmesecret")
                .accessTokenValiditySeconds(60 * 10)
                .refreshTokenValiditySeconds(60 * 60)
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
                .resourceIds("resource123");
    }

}
