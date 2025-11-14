package com.foodtruck.persistence;

import com.foodtruck.entities.Location;
import com.foodtruck.entities.Schedule;
import com.foodtruck.entities.Truck;
import com.foodtruck.testsupport.DbReset;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScheduleDaoTest extends DbReset {

    public GenericDao<Schedule> scheduleDao;
    public GenericDao<Truck> truckDao;
    public GenericDao<Location> locationDao;

    @Before
    public void initializeDaos() {
        scheduleDao = dao(Schedule.class);
        truckDao = dao(Truck.class);
        locationDao = dao(Location.class);
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
        assertEquals(1, schedule.getLocation().getId());
    }

    @Test
    public void getByIdMissingReturnsNull() {
        assertNull(scheduleDao.getById(999999));
    }
}
