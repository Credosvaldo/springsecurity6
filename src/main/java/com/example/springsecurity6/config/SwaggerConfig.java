package com.example.springsecurity6.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  OpenAPI customOpenAPI() {
    return new OpenAPI()
      .info(
        new Info()
          .title("Spring Security 6 with JWT Example")
          .version("1.0.0")
          .description(
            "API documentation for Spring Security 6 application using JWT for authentication."
          )
      )
      .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
      .components(
        new Components()

          .addSecuritySchemes(
            "oauth2",
            new SecurityScheme()
              .type(SecurityScheme.Type.OAUTH2)
              .flows(
                new OAuthFlows()
                  .password(
                    new OAuthFlow()
                      .tokenUrl("/login")
                      .scopes(new Scopes().addString("ADMIN", "Admin access"))
                  )
              )
          )
      );
  }
}
