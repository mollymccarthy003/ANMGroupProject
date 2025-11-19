package com.foodtruck.persistence;

import com.foodtruck.entities.Location;
import com.foodtruck.testsupport.DbReset;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

// Test class for LocationData DAO
// Extends DbReset to ensure a clean database state before each test
// Tests getting locations by ID
public class LocationDataTest extends DbReset {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private LocationData locationData;

    // Initialize the LocationData DAO before each test
    @Before
    public void initializeDaos() {
        locationData = new LocationData();
    }

    // Test getting a location by a valid ID
    @Test
    public void getByIdSuccess() {
        // Get location with ID 1
        Location testLocation = locationData.getById(1);

        // Verify the location details
        assertNotNull(testLocation);
        assertEquals("The Square", testLocation.getName());
        assertEquals("101 State Street", testLocation.getAddress());
        assertEquals("Wisconsin", testLocation.getState());
        assertEquals(53703, testLocation.getZip());
        assertEquals("United States", testLocation.getCountry());
    }

    // Test getting a location by a missing ID returns null
    @Test
    public void getByIdMissingReturnsNull() {
        // Verify that getting a non-existent location returns null
        assertNull(locationData.getById(999999));
    }
}
