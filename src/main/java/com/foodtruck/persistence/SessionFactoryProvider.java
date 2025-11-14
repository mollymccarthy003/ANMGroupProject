package com.foodtruck.persistence;

import com.foodtruck.entities.Location;
import com.foodtruck.entities.Schedule;
import com.foodtruck.entities.Truck;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * This file provides a SessionFactory for use with DAOs using Hibernate
 *
 * @author paulawaite
 * @version 3.0
 */
public class SessionFactoryProvider {

    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry registry;

    /**
     * Create session factory.
     */
    public static void createSessionFactory() {

        // Create registry
        registry = new StandardServiceRegistryBuilder().build();

        // Register annotated entities
        MetadataSources sources = new MetadataSources(registry)
                .addAnnotatedClass(Truck.class)
                .addAnnotatedClass(Location.class)
                .addAnnotatedClass(Schedule.class);

        // Create Metadata
        Metadata metadata = sources.getMetadataBuilder().build();

        // Create SessionFactory
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    /**
     * Gets session factory.
     *
     * @return the session factory
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            createSessionFactory();
        }
        return sessionFactory;

    }
}