package org.haedal.zzansuni.global.api;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;


@Configuration
public class SwaggerConfig {
    private static final String BEARER_KEY = "bearer-key";

    @Bean
    public OpenAPI openAPI(Server server) {
        var securityRequirement = new SecurityRequirement();
        securityRequirement.addList(BEARER_KEY);

        return new OpenAPI()
                .components(components())
                .info(info())
                .servers(List.of(server))
                .addSecurityItem(securityRequirement);
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes(BEARER_KEY, securityScheme());
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT 인증을 위한 Bearer 토큰을 입력하세요.(예 : Bearer {토큰}에서 {토큰}만 입력)");
    }

    private Info info() {
        return new Info()
                .title("Zzansuni API")
                .description("Zzansuni API 명세서")
                .version("1.0.0");
    }


    @Bean
    public Server getServer(
            @Value("${server-url:http://localhost:8080}")
            String serverUrl,
            Environment environment
    ) {
        String[] activeProfiles = environment.getActiveProfiles();
        String profileStr = activeProfiles.length > 0
                ? Arrays.stream(activeProfiles).reduce((a, b) -> a + ", " + b).get()
                : "default"
                + "Server";
        return new Server()
                .url(serverUrl)
                .description(profileStr);
    }
}
