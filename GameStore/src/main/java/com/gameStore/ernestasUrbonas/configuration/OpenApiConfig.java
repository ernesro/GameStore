package com.gameStore.ernestasUrbonas.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gameStoreOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Game Store API")
                        .description("API documentation for the Game Store application.")
                        .version("1.0.0"));
    }
}