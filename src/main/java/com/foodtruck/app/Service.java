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

@Path("/api")
@Tag(name = "Food Truck API", description = "Operations for managing food trucks and schedules")
public class Service {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final GenericDao<Truck> truckDao = new GenericDao<>(Truck.class);
    private final GenericDao<Location> locationDao = new GenericDao<>(Location.class);
    private final GenericDao<Schedule> scheduleDao = new GenericDao<>(Schedule.class);

    // ----------------------------
    // FOOD TRUCK ENDPOINTS
    // ----------------------------

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
        List<Truck> trucks = truckDao.getAll();
        return Response.ok(trucks).build();
    }

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
        return Response.ok(truck).build();
    }

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

        if (date != null && locationId != null) {
            results = scheduleDao.getByPropertyEqual("date", date);
            results.removeIf(s -> s.getLocationId() != locationId); // filter by location
        } else if (date != null) {
            results = scheduleDao.getByPropertyEqual("date", date);
        } else if (locationId != null) {
            results = scheduleDao.getByPropertyEqual("locationId", locationId.toString());
        } else {
            results = scheduleDao.getAll();
        }

        return Response.ok(results).build();
    }

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
