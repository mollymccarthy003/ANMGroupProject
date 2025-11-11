package com.foodtruck.persistence;

import com.foodtruck.util.Database;
import com.foodtruck.entities.Location;
import com.foodtruck.entities.Schedule;
import com.foodtruck.entities.Truck;
import org.junit.Before;

public class ScheduleDaoTest {

    public GenericDao<Schedule> scheduleDao;
    public GenericDao<Truck> truckDao;
    public GenericDao<Location> locationDao;

    @Before
    public void setup() {

        // Reset DB
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");

        // Init DAOs
        scheduleDao = new GenericDao<>(Schedule.class);
        truckDao = new GenericDao<>(Truck.class);
        locationDao = new GenericDao<>(Location.class);
    }

}
