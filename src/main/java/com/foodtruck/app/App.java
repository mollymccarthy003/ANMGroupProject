package com.foodtruck.app;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


/**
 * JAX-RS application configuration class.
 */
public class App extends Application {

    /**
     * Returns the set of classes that make up this JAX-RS application
     *
     * @return set of classes to include in the JAX-RS application
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        // Your service endpoints
        classes.add(Service.class);

        // Swagger / OpenAPI classes
        classes.add(SwaggerConfig.class);

        return classes;
    }
}
