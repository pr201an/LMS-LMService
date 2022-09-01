package com.example.lmservice.Swagger;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class Config {
    public Docket postApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("LMS Project")
                .apiInfo(apiInfo()).select()
                .paths(regex("/book.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Library Service")
                .description("Swagger Documentation for Library Service")
                .build();
    }
}
