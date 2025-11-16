package com.foodtruck.app;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void getClasses_registersServiceAndSwaggerConfig() {
        App app = new App();
        Set<Class<?>> classes = app.getClasses();

        assertTrue(classes.contains(Service.class));
        assertTrue(classes.contains(SwaggerConfig.class));
        // Optional: if you want to lock in that nothing weird gets added:
        assertEquals(2, classes.size());
    }
}
