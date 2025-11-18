package com.foodtruck.persistence;

import com.foodtruck.entities.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Data access class for Truck entities.
 * Provides basic operations to interact with the database.
 */
public class TruckData {
    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Retrieves a Truck entity by its ID.
     *
     * @param id The ID of the Truck
     * @return The Truck instance if found, otherwise null
     */
    public Truck getById(int id) {
        Session session = sessionFactory.openSession();
        Truck truck = session.get(Truck.class, id);
        session.close();
        return truck;
    }
}