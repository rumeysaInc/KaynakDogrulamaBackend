package org.dogrula.kaynakdogrulamabackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Kaynak Doğrulama API",
                version = "1.0",
                description = "Referans doğrulama servisi"
        )
)
public class SwaggerConfig {
    // Springdoc OpenAPI kullanıldığı için ekstra Docket bean'i gerekmiyor.
}
