package com.prem.portfolio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI portfolioOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Portfolio Backend API")
                        .description(
                                "REST API for portfolio management, " +
                                "authentication and authorization"
                        )
                        .version("1.0.0"));
    }
}