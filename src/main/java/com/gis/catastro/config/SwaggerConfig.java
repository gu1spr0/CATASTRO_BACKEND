package com.gis.catastro.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .pathMapping("/")
                .apiInfo(metaData());
    }

    private ApiInfo metaData(){
        Contact contact = new Contact("Geographic Information System", "http://www.geoapp.com",
                "learsi.guil2010@gmail.com");

        return new ApiInfo(
                "GIS-APP",
                "Gis-Api: EndPoints for the Geographic Information System",
                "0.1",
                "Terms of Service: This terms",
                contact,
                "Geo App - Copyright 2022",
                "https://www.geoapp.com/",
                new ArrayList<>());
    }

    private static ArrayList<? extends SecurityScheme> securitySchemes() {

        return Lists.newArrayList(new ApiKey("Bearer", "Authorization", "header"));
    }
}
