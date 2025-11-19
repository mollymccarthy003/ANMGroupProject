package com.foodtruck.persistence;

import com.foodtruck.entities.Location;
import com.foodtruck.entities.Schedule;
import com.foodtruck.entities.Truck;
import com.foodtruck.testsupport.DbReset;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

// Test class for Schedule DAO
// Extends DbReset to ensure a clean database state before each test
// Tests getting schedules by ID
public class ScheduleDaoTest extends DbReset {

    public GenericDao<Schedule> scheduleDao;
    public GenericDao<Truck> truckDao;
    public GenericDao<Location> locationDao;

    // Initialize DAOs before each test
    @Before
    public void initializeDaos() {
        scheduleDao = dao(Schedule.class);
        truckDao = dao(Truck.class);
        locationDao = dao(Location.class);
    }

    // Test getting a schedule by a valid ID
    @Test
    public void getByIdSuccess() {
        // Get schedule with ID 2
        Schedule schedule = scheduleDao.getById(2);

        // Verify the schedule details
        assertNotNull(schedule);
        assertEquals("Tuesday", schedule.getDayOfWeek());
        assertEquals("5/8/2026", schedule.getDate());
        assertEquals("8:00am", schedule.getStartTime());
        assertEquals("4:00pm", schedule.getEndTime());
        assertEquals(1, schedule.getTruck().getId());
        assertEquals(1, schedule.getLocation().getId());
    }

    // Test getting a schedule by a missing ID returns null
    @Test
    public void getByIdMissingReturnsNull() {
        // Verify that getting a non-existent schedule returns null
        assertNull(scheduleDao.getById(999999));
    }
}
