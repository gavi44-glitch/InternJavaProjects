package com.example.demo.utils;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("User API").version("1.0"))
                // memberitahu endpoint yang pakai security harus ada header Authorization
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(
                        new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes("bearerAuth",
                                        new SecurityScheme()

                                                // untuk memberitahu Swagger kalo ada penggunaan JWT
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )

                        /*
                            dengan ini, Swagger UI akan muncul AUthorize button di kanan atas untuk diinput
                            JWT dari login, dan Swagger akan pakai token ini untuk request selanjutnya
                         */

                );
    }
    }

