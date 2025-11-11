package com.foodtruck.persistence;

import com.foodtruck.util.Database;
import com.foodtruck.entities.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationDataTest {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private LocationData locationData;

    @Before
    public void setUp() {
        // Reset DB
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");

        // Init DAO
        locationData = new LocationData();
    }

    @Test
    public void getByIdSuccess() {
        Location testLocation = locationData.getById(1);
        assertNotNull(testLocation);
        assertEquals("The Square", testLocation.getName());
        assertEquals("101 State Street", testLocation.getAddress());
        assertEquals("Wisconsin", testLocation.getState());
        assertEquals(53703, testLocation.getZip());
        assertEquals("United States", testLocation.getCountry());
    }

    @Test
    public void getByIdMissingReturnsNull() {
        assertNull(locationData.getById(999999));
    }
}
