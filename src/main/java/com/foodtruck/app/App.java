package com.foodtruck.app;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class App extends Application {

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
