package com.foodtruck.app;

import org.glassfish.jersey.server.ResourceConfig;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;

public class SwaggerConfig extends ResourceConfig {
    public SwaggerConfig() {
        packages("com.foodtruck.app"); // scans your services
        register(OpenApiResource.class); // registers OpenAPI endpoint
    }
}
