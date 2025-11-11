package com.foodtruck.persistence;

import com.foodtruck.entities.Truck;
import com.foodtruck.testsupport.DbReset;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TruckDataTest extends DbReset {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private TruckData truckData;

    @Before
    public void initializeDaos() {
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