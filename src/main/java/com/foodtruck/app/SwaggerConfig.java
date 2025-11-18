package com.foodtruck.app;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;

/**
 * Config class for the Food Truck API Swagger Integration
 */
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
