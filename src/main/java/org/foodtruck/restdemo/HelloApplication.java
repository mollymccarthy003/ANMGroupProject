package org.foodtruck.restdemo;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

//Defines the base URI for all resource URIs.
@ApplicationPath("/service") //You may want to add a value here so that all traffic isn't routed to the class below.

//The java class declares root resource and provider classes
public class HelloApplication extends Application {

    //The method returns a non-empty collection with classes, that must be included in the published JAX-RS application
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add(HelloWorld.class );
        return h;
    }
}