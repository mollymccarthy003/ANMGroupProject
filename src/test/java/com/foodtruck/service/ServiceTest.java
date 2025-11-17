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

public class ServiceTest extends DbReset {

    private Service service;
    private ObjectMapper objectMapper;

    private static final Logger logger = LogManager.getLogger(ServiceTest.class);

    @Before
    public void setUpService() {
        // DbReset has already run and cleaned/seeded the database due to inheritance
        service = new Service();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllTrucksReturnsSeededData() throws Exception {
        Response response = service.getAllTrucks();

        assertEquals(200, response.getStatus());

        String json = (String) response.getEntity();
        // Got type error trying to use .readValue with List<Truck>, due to test trying to deserialize to generic type
        // so using JsonNode tree instead for this test. Basically it just parses to a generic JSON tree structure instead
        // of actually deserializing and binding/mapping to specific Java types.
        JsonNode rootNode = objectMapper.readTree(json);
        logger.debug("Received trucks JSON: " + rootNode.toString());

        assertTrue("Expected an array of trucks", rootNode.isArray());
        assertTrue("Expected at least one truck", rootNode.size() > 0);

        JsonNode firstTruck = rootNode.get(0);
        assertEquals("ANM Burger Buds", firstTruck.get("name").asText());
    }

    @Test
    public void getTrucksByIdNotFoundReturns404() {
        Response response = service.getTruckById(9999);
        // Assuming 9999 does not exist
        assertEquals(404, response.getStatus());
        assertNull(response.getEntity());
    }

    @Test
    public void getScheduleFilterByDate() throws Exception {
        Response response = service.getSchedule("5/7/2026", null);

        assertEquals(200, response.getStatus());

        String json = (String) response.getEntity();
        JsonNode rootNode = objectMapper.readTree(json);

        assertTrue("Expected an array of schedules", rootNode.isArray());
        assertEquals(1, rootNode.size());

        JsonNode firstSchedule = rootNode.get(0);
        assertEquals("5/7/2026", firstSchedule.get("date").asText());
        assertEquals("8:00am", firstSchedule.get("startTime").asText());
        assertEquals("ANM Burger Buds", firstSchedule.get("truck").get("name").asText());
    }

    @Test
    public void getScheduleFilterByLocation() throws Exception {
        Response response = service.getSchedule(null, 1);

        assertEquals(200, response.getStatus());

        String json = (String) response.getEntity();
        JsonNode rootNode = objectMapper.readTree(json);

        assertTrue("Expected an array of schedules", rootNode.isArray());
        assertTrue("Expected at least one schedule", rootNode.size() > 0);

        for (JsonNode scheduleNode : rootNode) {
            assertEquals(1, scheduleNode.get("location").get("id").asInt());
        }
    }

    @Test
    public void getScheduleBadLocationReturnsEmpty() throws Exception {
        Response response = service.getSchedule(null, 9999); // Assuming 9999 is invalid

        assertEquals(200, response.getStatus());

        String json = (String) response.getEntity();
        JsonNode rootNode = objectMapper.readTree(json);

        assertTrue("Expected an array of schedules", rootNode.isArray());
        assertEquals(0, rootNode.size());
    }
}
