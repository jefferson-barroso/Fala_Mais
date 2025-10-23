package com.example.fala.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI falaMaisOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fala+ API")
                        .description("API para cadastro e gestão de usuários e feedbacks.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe Fala+")
                                .email("suporte@falaplus.com.br")
                                .url("https://falaplus.com.br"))
                        .license(new License()
                                .name("Licença MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
