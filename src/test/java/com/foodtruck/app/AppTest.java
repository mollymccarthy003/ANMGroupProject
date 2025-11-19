package com.foodtruck.app;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class AppTest {

    // Test that getClasses() returns the expected classes
    @Test
    public void getClassesCheckServiceAndSwaggerConfig() {
        // Instantiate the App class
        App app = new App();

        // Get the classes from the App instance
        Set<Class<?>> classes = app.getClasses();

        // Check that the expected classes are present
        assertTrue(classes.contains(Service.class));
        assertTrue(classes.contains(SwaggerConfig.class));
        // Optional (can delete if not wanted later) - this locks in so that nothing weird gets added.
        // Just make sure that if you add more classes later, you update this test
        assertEquals(2, classes.size());
    }
}