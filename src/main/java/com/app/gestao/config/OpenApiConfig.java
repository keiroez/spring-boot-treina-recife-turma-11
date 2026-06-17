package com.app.gestao.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestão de Projetos API")
                        .description("API REST para gerenciamento de projetos e tarefas com controle de acesso por perfil (USER/ADMIN)")
                        .version("1.0.0"))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/public/**")
                // Endpoints públicos não exigem autenticação: remove o esquema/cadeado JWT deste grupo.
                .addOpenApiCustomizer(openApi -> {
                    if (openApi.getComponents() != null) {
                        openApi.getComponents().setSecuritySchemes(null);
                    }
                    openApi.setSecurity(null);
                })
                .build();
    }

    @Bean
    public GroupedOpenApi privateApi() {
        return GroupedOpenApi.builder()
                .group("private")
                .pathsToMatch("/**")
                .pathsToExclude("/public/**")
                .build();
    }
}
