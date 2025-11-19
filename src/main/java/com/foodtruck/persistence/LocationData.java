package com.foodtruck.persistence;

import com.foodtruck.entities.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Data access class for Location entities.
 * Provides basic operations to interact with the database.
 */
public class LocationData {

    /** Logger for debugging and error messages */
    private final Logger logger = LogManager.getLogger(this.getClass());

    /** Hibernate SessionFactory for opening sessions */
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Retrieves a Location entity by its ID.
     *
     * @param id The ID of the Location
     * @return The Location instance if found, otherwise null
     */
    public Location getById(int id) {
        Session session = sessionFactory.openSession();
        Location location = session.get(Location.class, id);
        session.close();
        return location;
    }
}