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

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    //needed this for update to the default schema
    private static final String DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT = "insert into invoice.oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)";

    private static final String DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT_MERGE = "MERGE INTO invoice.OAUTH_ACCESS_TOKEN OAT " +
            "USING (SELECT " +
            "         ? TOKEN_ID, " +
            "         ? TOKEN, " +
            "         ? AUTHENTICATION_ID, " +
            "         ? USER_NAME, " +
            "         ? CLIENT_ID, " +
            "         ? AUTHENTICATION, " +
            "         ? REFRESH_TOKEN " +
            "       FROM DUAL) INCOMING " +
            "ON (OAT.AUTHENTICATION_ID = INCOMING.AUTHENTICATION_ID) " +
            "WHEN MATCHED THEN " +
            "  UPDATE SET OAT.TOKEN_ID = INCOMING.TOKEN_ID, OAT.TOKEN = INCOMING.TOKEN, OAT.USER_NAME = INCOMING.USER_NAME, OAT.CLIENT_ID = INCOMING.CLIENT_ID, " +
            "    OAT.AUTHENTICATION    = INCOMING.AUTHENTICATION, OAT.REFRESH_TOKEN = INCOMING.REFRESH_TOKEN " +
            "WHEN NOT MATCHED THEN " +
            "  INSERT (TOKEN_ID, TOKEN, AUTHENTICATION_ID, USER_NAME, CLIENT_ID, AUTHENTICATION, REFRESH_TOKEN) " +
            "  VALUES (INCOMING.TOKEN_ID, INCOMING.TOKEN, INCOMING.AUTHENTICATION_ID, INCOMING.USER_NAME, INCOMING.CLIENT_ID, INCOMING.AUTHENTICATION, " +
            "          INCOMING.REFRESH_TOKEN)";

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

    private static final String DEFAULT_SELECT_STATEMENT_ACS = "select code, authentication from invoice.oauth_code where code = ?";

    private static final String DEFAULT_INSERT_STATEMENT_ACS = "insert into invoice.oauth_code (code, authentication) values (?, ?)";

    private static final String DEFAULT_DELETE_STATEMENT_ACS = "delete from invoice.oauth_code where code = ?";

    private static final String DEFAULT_FIND_STATEMENT_CDS = "select client_id, client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove from invoice.oauth_client_details order by client_id";

    private static final String DEFAULT_SELECT_STATEMENT_CDS = "select client_id, client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove from invoice.oauth_client_details where client_id = ?";

    private static final String DEFAULT_INSERT_STATEMENT_CDS = "insert into invoice.oauth_client_details (client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove, client_id) values (?,?,?,?,?,?,?,?,?,?,?)";

    private static final String DEFAULT_UPDATE_STATEMENT_CDS = "update invoice.oauth_client_details set resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove".replaceAll(", ", "=?, ") + "=? where client_id = ?";

    private static final String DEFAULT_UPDATE_SECRET_STATEMENT_CDS = "update invoice.oauth_client_details set client_secret = ? where client_id = ?";

    private static final String DEFAULT_DELETE_STATEMENT_CDS = "delete from invoice.oauth_client_details where client_id = ?";

    @Autowired
    @Qualifier("comboDataSource")
    private DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;

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

    @Bean
    public ClientDetailsService customClientDetailsService() {
        final JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setDeleteClientDetailsSql(DEFAULT_DELETE_STATEMENT_CDS);
        jdbcClientDetailsService.setFindClientDetailsSql(DEFAULT_FIND_STATEMENT_CDS);
        jdbcClientDetailsService.setInsertClientDetailsSql(DEFAULT_INSERT_STATEMENT_CDS);
        jdbcClientDetailsService.setSelectClientDetailsSql(DEFAULT_SELECT_STATEMENT_CDS);
        jdbcClientDetailsService.setUpdateClientDetailsSql(DEFAULT_UPDATE_STATEMENT_CDS);
        jdbcClientDetailsService.setUpdateClientSecretSql(DEFAULT_UPDATE_SECRET_STATEMENT_CDS);
        return jdbcClientDetailsService;
    }

    @Bean
    protected AuthorizationCodeServices customAuthorizationCodeServices() {
        final JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices = new JdbcAuthorizationCodeServices(dataSource);
        jdbcAuthorizationCodeServices.setDeleteAuthenticationSql(DEFAULT_DELETE_STATEMENT_ACS);
        jdbcAuthorizationCodeServices.setInsertAuthenticationSql(DEFAULT_INSERT_STATEMENT_ACS);
        jdbcAuthorizationCodeServices.setSelectAuthenticationSql(DEFAULT_SELECT_STATEMENT_ACS);
        return jdbcAuthorizationCodeServices;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .authorizationCodeServices(customAuthorizationCodeServices());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore());
        return tokenServices;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(customClientDetailsService());
    }

}
