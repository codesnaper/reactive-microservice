package org.example.product.productApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2

public class SwaggerConf {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).
                select()
                .apis(RequestHandlerSelectors.basePackage("org.example.product.productApi.controller"))
                .paths(PathSelectors.ant("/product-api/*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("Product-API", "Product Rest API", "1.0-SnapShot", "Terms of service", new Contact("Shubham Khandelwal", "http://localhost:8080", "shubham.khandelwal019@gmail.com"), "License of API", "API license URL", Collections.emptyList());
        return apiInfo;
    }
}
