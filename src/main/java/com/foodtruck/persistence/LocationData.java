package com.foodtruck.persistence;

import com.foodtruck.entities.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class LocationData {
    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Get truck by id
     *
     * @param id - trucks id
     * @return truck
     */
    public Location getById(int id) {
        Session session = sessionFactory.openSession();
        Location location = session.get(Location.class, id);
        session.close();
        return location;
    }
}