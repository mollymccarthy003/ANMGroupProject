package com.foodtruck.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodtruck.app.Service;
import com.foodtruck.testsupport.DbReset;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

// Test class for Service layer
public class ServiceTest extends DbReset {

    private Service service;
    private ObjectMapper objectMapper;

    private static final Logger logger = LogManager.getLogger(ServiceTest.class);

    // Set up the Service instance before each test
    @Before
    public void setUpService() {
        // DbReset has already run and cleaned/seeded the database due to inheritance
        service = new Service();
        objectMapper = new ObjectMapper();
    }

    // Test getting all trucks returns seeded data
    @Test
    public void getAllTrucksReturnsSeededData() throws Exception {
        // Call the service method to get all trucks
        Response response = service.getAllTrucks();

        // Verify the response status is 200 OK
        assertEquals(200, response.getStatus());

        // Parse the JSON response
        String json = (String) response.getEntity();
        // Got type error trying to use .readValue with List<Truck>, due to test trying to deserialize to generic type
        // so using JsonNode tree instead for this test. Basically it just parses to a generic JSON tree structure instead
        // of actually deserializing and binding/mapping to specific Java types.
        JsonNode rootNode = objectMapper.readTree(json);
        logger.debug("Received trucks JSON: " + rootNode.toString());

        // Verify the response contains an array of trucks
        assertTrue("Expected an array of trucks", rootNode.isArray());
        assertTrue("Expected at least one truck", rootNode.size() > 0);

        // Verify the first truck's name matches the seeded data
        JsonNode firstTruck = rootNode.get(0);
        assertEquals("ANM Burger Buds", firstTruck.get("name").asText());
    }

    // Test getting truck by valid ID
    @Test
    public void getTrucksByIdNotFoundReturns404() {
        // Call the service method to get a truck by an ID that does not exist
        Response response = service.getTruckById(9999);
        // Assuming 9999 does not exist
        assertEquals(404, response.getStatus());
        // Verify that the entity is null for not found
        assertNull(response.getEntity());
    }

    // Test getting schedule filtered by date
    @Test
    public void getScheduleFilterByDate() throws Exception {
        // Call the service method to get schedule for a specific date
        Response response = service.getSchedule("5/7/2026", null);

        // Verify the response status is 200 OK
        assertEquals(200, response.getStatus());

        // Parse the JSON response
        String json = (String) response.getEntity();
        // Using JsonNode tree for parsing
        JsonNode rootNode = objectMapper.readTree(json);

        // Verify the response contains an array of schedules
        assertTrue("Expected an array of schedules", rootNode.isArray());
        assertEquals(1, rootNode.size());

        // Verify the first schedule's details match the seeded data
        JsonNode firstSchedule = rootNode.get(0);
        assertEquals("5/7/2026", firstSchedule.get("date").asText());
        assertEquals("8:00am", firstSchedule.get("startTime").asText());
        assertEquals("ANM Burger Buds", firstSchedule.get("truck").get("name").asText());
    }

    // Test getting schedule filtered by location
    @Test
    public void getScheduleFilterByLocation() throws Exception {
        // Call the service method to get schedule for a specific location ID
        Response response = service.getSchedule(null, 1);

        // Verify the response status is 200 OK
        assertEquals(200, response.getStatus());

        // Parse the JSON response
        String json = (String) response.getEntity();
        // Using JsonNode tree for parsing
        JsonNode rootNode = objectMapper.readTree(json);

        // Verify the response contains an array of schedules
        assertTrue("Expected an array of schedules", rootNode.isArray());
        assertTrue("Expected at least one schedule", rootNode.size() > 0);

        // Verify all returned schedules are for the specified location ID
        for (JsonNode scheduleNode : rootNode) {
            assertEquals(1, scheduleNode.get("location").get("id").asInt());
        }
    }

    // Test getting schedule with bad location returns empty array
    @Test
    public void getScheduleBadLocationReturnsEmpty() throws Exception {
        // Call the service method to get schedule for an invalid location ID
        Response response = service.getSchedule(null, 9999); // Assuming 9999 is invalid

        // Verify the response status is 200 OK
        assertEquals(200, response.getStatus());

        // Parse the JSON response
        String json = (String) response.getEntity();
        // Using JsonNode tree for parsing
        JsonNode rootNode = objectMapper.readTree(json);

        // Verify the response contains an empty array of schedules
        assertTrue("Expected an array of schedules", rootNode.isArray());
        assertEquals(0, rootNode.size());
    }
}
