package com.bgh.myopeninvoice.common.swagger;

import com.google.common.base.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Slf4j
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    private static final String CLIENT_ID = "client-user-1";
    private static final String AUTH_SERVER = "http://localhost:8083/options-auth";

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(CLIENT_ID)
                .clientSecret("6bdeee05-ca5b-42ad-bd46-cf46f598430a")
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("options-api")
                .apiInfo(apiInfo())
                .select()
                .paths(postPaths())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        Collections.singletonList(new SecurityReference("spring_oauth", scopes())))
                .forPaths(PathSelectors.regex("/options-auth/api/v1/.*"))
                .build();
    }

    private SecurityScheme securityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/oauth/token", "oauthtoken"))
                .tokenRequestEndpoint(
                        new TokenRequestEndpoint(AUTH_SERVER + "/oauth/authorize", CLIENT_ID, CLIENT_ID))
                .build();

        return new OAuthBuilder().name("spring_oauth")
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope("all", "All scopes")
        };
    }


    private Predicate<String> postPaths() {
        return regex("/options-auth/api/v1/.*");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Options Cruncher API")
                .description("Options Cruncher API reference for developers")
                .termsOfServiceUrl("http://options-cruncher.com")
                .contact(new Contact("Branislav Cavlin", "", "bcavlin@bghexagon.com"))
                .license("Proprietary License")
                .version("1.0")
                .build();
    }
}
