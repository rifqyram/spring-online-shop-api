package digital.gtech.onlineshop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Online Shop - Tech Interview",
        version = "1.0",
        contact = @Contact(
                name = "GTech Digital",
                url = "https://gtech.digital")
))
public class OpenApiConfig {
}
