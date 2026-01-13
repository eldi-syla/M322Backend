package ch.bbw.er.backend.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "The API Backend built @ Bbc", version = "1.0.0"),
        security = @SecurityRequirement(name = "Authorization")
)
public class OpenAPIConfiguration {
}
