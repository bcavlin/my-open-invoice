package com.bgh.myopeninvoice.common.swagger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Slf4j
@EnableSwagger2
@Configuration
@Profile("!prod")
public class SwaggerConfig {

  @Bean
  public Docket postsApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("my-open-invoice-api")
        .apiInfo(apiInfo())
        .select()
        .paths(regex("/api/v1/.*"))
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("My Open Invoice API")
        .description("My Open Invoice API reference for developers")
        .termsOfServiceUrl("http://www.my-open-invoice.ca")
        .contact(new Contact("Branislav Cavlin", "", "bcavlin@bghexagon.com"))
        .license("Apache License")
        .version("1.0")
        .build();
  }
}
