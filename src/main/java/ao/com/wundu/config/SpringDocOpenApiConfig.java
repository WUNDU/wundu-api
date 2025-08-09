package ao.com.wundu.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, createSecurityScheme()))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .info(
                        new Info()
                                .title("API - WUNDU")
                                .description("Wundu é um aplicativo financeiro que visa ajudar os usuários a gerenciar suas despesas, acompanhar\n" +
                                        "\t\trendimentos e estabelecer objetivos financeiros de curto e longo prazo")
                                .version("v1")
                                .license(new License().name("Apache 2.0"))
                                .contact(new Contact().name("WUNDU").email("fernandowundu@gmail.com"))
                );
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Insira um bearer token válido para prosseguir");
    }
}
