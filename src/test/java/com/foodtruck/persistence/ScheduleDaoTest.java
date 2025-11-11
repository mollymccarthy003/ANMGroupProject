package com.foodtruck.persistence;

import com.foodtruck.util.Database;
import com.foodtruck.entities.Location;
import com.foodtruck.entities.Schedule;
import com.foodtruck.entities.Truck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void getByIdSuccess() {
        Schedule schedule = scheduleDao.getById(2);
        assertNotNull(schedule);
        assertEquals("Tuesday", schedule.getDayOfWeek());
        assertEquals("5/8/2026", schedule.getDate());
        assertEquals("8:00am", schedule.getStartTime());
        assertEquals("4:00pm", schedule.getEndTime());
        assertEquals(1, schedule.getTruck().getId());
        assertEquals(1, schedule.getLocation().getLocationId());
    }

    @Test
    public void getByIdMissingReturnsNull() {
        assertNull(scheduleDao.getById(999999));
    }
}
