package com.uxpsystems.assignement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
    @Bean
    public Docket apiDocket() {
    	
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
				.apiInfo(getApiInfo());
    }
    
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
           .title("Amdocs Test Code")
           .description("Test code for the Java Developer role at the compnay Amdocs")
           .contact(new Contact("Ricardo Ribeiro", "http://localhost:9090/assignement/", "ricardorqr@gmail.com"))
           .version("1.0")
           .build();
    }
    
}