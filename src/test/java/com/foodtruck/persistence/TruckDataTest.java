package com.foodtruck.persistence;

import com.foodtruck.util.Database;
import com.foodtruck.entities.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TruckDataTest {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private TruckData truckData;

    @Before
    public void setUp() {
        // Reset DB
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");

        // Init DAO
        truckData = new TruckData();
    }

    @Test
    public void getByIdSuccess() {
        Truck testTruck = truckData.getById(1);
        assertNotNull(testTruck);
        assertEquals("ANM Burger Buds", testTruck.getName());
        assertEquals("Burgers", testTruck.getFoodType());
    }

    @Test
    public void getByIdMissingReturnsNull() {
        assertNull(truckData.getById(999999));
    }
}