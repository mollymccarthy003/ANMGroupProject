package com.foodtruck.app;

import com.foodtruck.entities.Location;
import com.foodtruck.entities.Schedule;
import com.foodtruck.entities.Truck;
import com.foodtruck.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

//Jackson imports
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * RESTful service providing endpoints to manage food truck information
 * All endpoints return JSON responses and use standard HTTP status codes for success and failure.
 */
@Path("/api")
@Tag(name = "Food Truck API", description = "Operations for managing food trucks and schedules")
public class Service {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final GenericDao<Truck> truckDao = new GenericDao<>(Truck.class);
    private final GenericDao<Location> locationDao = new GenericDao<>(Location.class);
    private final GenericDao<Schedule> scheduleDao = new GenericDao<>(Schedule.class);

    // ----------------------------
    // FOOD TRUCK ENDPOINTS
    // ----------------------------
    /**
     * Retrieves all food trucks.
     *
     * @return JSON list of all {@link Truck} objects with HTTP 200, or error JSON with HTTP 500.
     */
    @GET
    @Path("/trucks")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Get all food trucks",
            description = "Returns a list of all food trucks",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of trucks retrieved successfully")
            }
    )
    public Response getAllTrucks() {
        logger.debug("GET /trucks called");
        List<Truck> trucks = truckDao.getAll();
        logger.debug("Found " + trucks.size() + " trucks");

        try {
            String json = objectMapper.writeValueAsString(trucks);
            return Response
                    .ok(json, MediaType.APPLICATION_JSON)
                    .build();
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize trucks to JSON", e);
            return Response
                    .serverError()
                    .entity("{\"error\":\"Failed to serialize response\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    /**
     * Retrieves a single food truck by ID.
     *
     * @param id ID of the truck to retrieve.
     * @return JSON representation of the {@link Truck} with HTTP 200, or HTTP 404 if not found,
     *         or error JSON with HTTP 500 on serialization error.
     */
    @GET
    @Path("/trucks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Get a single food truck",
            description = "Returns a food truck by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Truck found"),
                    @ApiResponse(responseCode = "404", description = "Truck not found")
            }
    )
    public Response getTruckById(
            @Parameter(description = "ID of the truck", required = true)
            @PathParam("id") int id) {
        Truck truck = truckDao.getById(id);
        if (truck == null) return Response.status(Response.Status.NOT_FOUND).build();

        try {
            String json = objectMapper.writeValueAsString(truck);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize truck", e);
            return Response.serverError()
                    .entity("{\"error\":\"Failed to serialize response\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    /**
     * Creates a new food truck.
     *
     * @param truck {@link Truck} object to create.
     * @return The created {@link Truck} JSON with HTTP 201.
     */
    @POST
    @Path("/trucks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Add a new food truck",
            description = "Creates a new food truck record",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Truck created successfully")
            }
    )
    public Response addTruck(
            @Parameter(description = "Truck object to add", required = true) Truck truck) {
        int id = truckDao.insert(truck);
        return Response.status(Response.Status.CREATED).entity(truckDao.getById(id)).build();
    }

    /**
     * Updates an existing food truck by ID.
     *
     * @param id    ID of the truck to update.
     * @param truck Updated {@link Truck} object.
     * @return The updated {@link Truck} JSON with HTTP 200, or HTTP 404 if not found.
     */
    @PUT
    @Path("/trucks/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Update truck details",
            description = "Updates an existing food truck by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Truck updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Truck not found")
            }
    )
    public Response updateTruck(
            @Parameter(description = "ID of the truck to update", required = true)
            @PathParam("id") int id,
            @Parameter(description = "Updated truck object", required = true) Truck truck) {
        Truck existing = truckDao.getById(id);
        if (existing == null) return Response.status(Response.Status.NOT_FOUND).build();
        truck.setId(id);
        truckDao.update(truck);
        return Response.ok(truckDao.getById(id)).build();
    }

    /**
     * Deletes a food truck by ID.
     *
     * @param id ID of the truck to delete.
     * @return HTTP 204 on success, or HTTP 404 if truck not found.
     */
    @DELETE
    @Path("/trucks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Delete a food truck",
            description = "Removes a food truck by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Truck deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Truck not found")
            }
    )
    public Response deleteTruck(
            @Parameter(description = "ID of the truck to delete", required = true)
            @PathParam("id") int id) {
        Truck existing = truckDao.getById(id);
        if (existing == null) return Response.status(Response.Status.NOT_FOUND).build();
        truckDao.delete(existing);
        return Response.noContent().build();
    }

    // ----------------------------
    // SCHEDULE ENDPOINTS
    // ----------------------------

    /**
     * Retrieves schedule entries.
     *
     * @param date       Optional filter by date (YYYY-MM-DD).
     * @param locationId Optional filter by location ID.
     * @return List of {@link Schedule} entries matching the filters with HTTP 200,
     *         or error JSON with HTTP 500 on serialization error.
     */
    @GET
    @Path("/schedule")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Get schedule entries",
            description = "Returns all schedules or filters by date or location_id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Schedules retrieved successfully")
            }
    )
    public Response getSchedule(
            @Parameter(description = "Filter by date (YYYY-MM-DD)") @QueryParam("date") String date,
            @Parameter(description = "Filter by location ID") @QueryParam("location_id") Integer locationId) {

        List<Schedule> results;
        // When entering the data for schedule, make sure you don't add a 0 in front of a single digit date or month
        // e.g., use 2024-6-5 instead of 2024-06-05. Could also be validated better in CRUD operations, maybe.
        //Also, I need more data to _fully_ test this endpoint's filtering logic.
        if (date != null && locationId != null) {
            // Filter by date first
            results = scheduleDao.getByPropertyEqual("date", date);
            // Then filter by location
            results.removeIf(s -> s.getLocation() == null || s.getLocation().getId() != locationId);
        } else if (date != null) {
            results = scheduleDao.getByPropertyEqual("date", date);
        } else if (locationId != null) {
            results = scheduleDao.getAll();
            results.removeIf(s -> s.getLocation() == null || s.getLocation().getId() != locationId);
        } else {
            results = scheduleDao.getAll();
        }

        try {
            String json = objectMapper.writeValueAsString(results);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize schedule list", e);
            return Response.serverError()
                    .entity("{\"error\":\"Failed to serialize schedule results\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    /**
     * Creates a new schedule entry.
     *
     * @param schedule {@link Schedule} object to create.
     * @return The created {@link Schedule} JSON with HTTP 201.
     */
    @POST
    @Path("/schedule")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Add a schedule entry",
            description = "Creates a new schedule for a truck at a location",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Schedule created successfully")
            }
    )
    public Response addSchedule(
            @Parameter(description = "Schedule object to add", required = true) Schedule schedule) {
        int id = scheduleDao.insert(schedule);
        return Response.status(Response.Status.CREATED).entity(scheduleDao.getById(id)).build();
    }

    /**
     * Deletes a schedule entry by ID.
     *
     * @param id ID of the schedule entry to delete.
     * @return HTTP 204 on success, or HTTP 404 if schedule not found.
     */
    @DELETE
    @Path("/schedule/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Delete a schedule entry",
            description = "Removes a schedule entry by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Schedule deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Schedule not found")
            }
    )
    public Response deleteSchedule(
            @Parameter(description = "ID of the schedule to delete", required = true)
            @PathParam("id") int id) {
        Schedule existing = scheduleDao.getById(id);
        if (existing == null) return Response.status(Response.Status.NOT_FOUND).build();
        scheduleDao.delete(existing);
        return Response.noContent().build();
    }
}
