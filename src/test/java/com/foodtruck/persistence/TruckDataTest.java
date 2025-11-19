package com.foodtruck.persistence;

import com.foodtruck.entities.Truck;
import com.foodtruck.testsupport.DbReset;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

// Test class for TruckData DAO
// Extends DbReset to ensure a clean database state before each test
// Tests getting trucks by ID
public class TruckDataTest extends DbReset {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private TruckData truckData;

    // Initialize the TruckData DAO before each test
    @Before
    public void initializeDaos() {
        truckData = new TruckData();
    }

    // Test getting a truck by a valid ID
    @Test
    public void getByIdSuccess() {
        // Get truck with ID 1
        Truck testTruck = truckData.getById(1);

        // Verify the truck details
        assertNotNull(testTruck);
        assertEquals("ANM Burger Buds", testTruck.getName());
        assertEquals("Burgers", testTruck.getFoodType());
    }

    // Test getting a truck by a missing ID returns null
    @Test
    public void getByIdMissingReturnsNull() {
        // Verify that getting a non-existent truck returns null
        assertNull(truckData.getById(999999));
    }
}