package com.lucky.luckyproject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("LG.COM ?µí•©?´ë“œë¯?API ?œë¹„??)
                .version("1.0.0")
                .description("LG.COM ?µí•©?´ë“œë¯?Spring Boot ?ìš© ê°€?´ë“œ")
            )
            .components(new Components()
                // 1. ê³µí†µ?¼ë¡œ ?¬ìš©???¤ë” ?•ì˜
                .addParameters("Accept-Language", new HeaderParameter()
                    .name("Accept-Language")
                    .description("?¸ì–´ ?¤ì • (ko, en)")
                    .schema(new StringSchema()._default("ko"))
                    .required(false)
                )
            );
    }
}
