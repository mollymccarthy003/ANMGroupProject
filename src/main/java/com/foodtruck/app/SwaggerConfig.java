package com.foodtruck.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;

@OpenAPIDefinition(
        info = @Info(
                title = "Food Truck Locator API",
                version = "1.0",
                description = "API for retrieving food truck data"
        ),
        servers = {
                @Server(
                        url = "FoodTruckAPI_war/api",
                        description = "Base API Path"
                )
        }
)
public class SwaggerConfig extends ResourceConfig {
    /**
     * Default constructor
     */
    public SwaggerConfig() {
        // Register your REST endpoints explicitly
        register(Service.class);

        // Enable Jackson JSON for Jersey
        register(JacksonFeature.class);

        // Swagger / OpenAPI endpoint
        register(OpenApiResource.class);
        packages("com.foodtruck.app"); // scans your services
        register(OpenApiResource.class); // registers OpenAPI endpoint
    }
}
