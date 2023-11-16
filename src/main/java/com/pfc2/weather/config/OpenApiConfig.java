package com.pfc2.weather.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
            contact = @Contact(
                    name = "José Carrillo",
                    email = "josecarrillo8@gmail.com"
            ),
            title = "REST API WEATHER TEST - PROMERICA",
            description = "Aplicación rest para la consulta del clima por latitud y longitud",
            version = "V1.0.0",
            license = @License(
                    name = "Apache 2.0",
                    url = "http://springdoc.org"
            )
    ),
    servers = {
            @Server(
                    url = "http://localhost:8080",
                    description = "Localhost Server URL"
            )
    }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Get token Auth0 from api server",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
