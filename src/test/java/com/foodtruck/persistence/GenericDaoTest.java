package com.foodtruck.persistence;

import com.foodtruck.entities.Location;
import com.foodtruck.entities.Schedule;
import com.foodtruck.entities.Truck;
import com.foodtruck.testsupport.DbReset;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

/*
    Things to do when time permits:
    Extract helper methods to reduce redundancy:
    - Create methods for seeding trucks, locations, and schedules.
    - Create methods for verifying entity properties.
 */

// Generic DAO tests for Truck, Location, and Schedule entities.
// Each test verifies CRUD operations and relationships between entities.
// Extends DbReset to ensure a clean database state before each test.
public class GenericDaoTest extends DbReset {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private GenericDao<Truck> truckDao;
    private GenericDao<Location> locationDao;
    private GenericDao<Schedule> scheduleDao;

    // Initialize DAOs before each test
    @Before
    public void initializeDaos() {
        truckDao = dao(Truck.class);
        locationDao = dao(Location.class);
        scheduleDao = dao(Schedule.class);
    }

    //----------------------------
    // Truck CRUD
    //----------------------------

    // Test inserting a Truck entity.
    // Verifies that the truck is correctly inserted and can be retrieved with the expected properties.
    @Test
    public void truckInsertSuccess() {
        // Seed a truck
        int id = truckDao.insert(new Truck("Travelling Taco", "Authentic Mexican"));

        // Retrieve truck by ID
        Truck testTruck = truckDao.getById(id);

        // Verify properties
        assertNotNull(testTruck);
        assertEquals("Travelling Taco", testTruck.getName());
        assertEquals("Authentic Mexican", testTruck.getFoodType());
    }

    // Test updating a Truck entity.
    // Verifies that the truck's properties are correctly updated and persisted.
    @Test
    public void truckUpdateSuccess() {
        // Seed a truck to update
        int id = truckDao.insert(new Truck("Rolling Ribs", "BBQ"));

        // Get truck by ID
        Truck truckToUpdate = truckDao.getById(id);

        // Modify properties
        truckToUpdate.setName("The Rolling Rib Rack");
        truckToUpdate.setFoodType("BBQ and More");
        truckDao.update(truckToUpdate);

        // Retrieve updated truck
        Truck updatedTruck = truckDao.getById(id);

        // Verify updated properties
        assertEquals("The Rolling Rib Rack", updatedTruck.getName());
        assertEquals("BBQ and More", updatedTruck.getFoodType());
    }

    // Test deleting a Truck entity.
    // Verifies that the truck is correctly deleted and cannot be retrieved afterwards.
    @Test
    public void truckDeletedSuccess() {
        // Need to add cascade delete for schedules later
        int id = truckDao.insert(new Truck("Tart Truck", "Desserts"));

        // Get truck by ID
        Truck truckToDelete = truckDao.getById(id);

        // Delete the truck
        truckDao.delete(truckToDelete);

        // Assert that the truck no longer exists
        assertNull(truckDao.getById(id));
    }

    // Test that deleting a Truck entity cascades to delete associated Schedule entities.
    // Verifies that schedules linked to the deleted truck are also removed from the database.
    @Test
    public void truckDeletionCascadesToSchedules() {
        // Create truck, location, and schedule
        Truck testTruck = new Truck("Cascade Test Truck", "Test Food");
        truckDao.insert(testTruck);
        Location testLocation = new Location("Cascade Test Location", "123 Cascade St", "WI", 12345, "USA", 43.07, -89.40);
        locationDao.insert(testLocation);
        Schedule testSchedule = new Schedule(testTruck, testLocation, "Friday", "2024-10-11", "11:00", "15:00");
        int scheduleId = scheduleDao.insert(testSchedule);

        // Delete the truck
        Truck truckParent = truckDao.getById(testTruck.getId());
        truckDao.delete(truckParent);

        // Verify the schedule is also deleted
        assertNull(scheduleDao.getById(scheduleId));
    }

    // Test retrieving all Truck entities.
    // Verifies that the correct number of trucks is returned from getAll()/database and then
    @Test
    public void truckGetAllAndCheck() {
        // Check how many trucks are in the DB
        List<Truck> initialTrucks = truckDao.getAll();
        int initialCount = initialTrucks.size();

        // Seed data to populate 3 trucks
        truckDao.insert(new Truck("Burger Bus", "Burgers"));
        truckDao.insert(new Truck("Sushi Stop", "Sushi"));
        truckDao.insert(new Truck("Curry Cart", "Indian"));

        // Retrieve all trucks again
        List<Truck> allTrucks = truckDao.getAll();

        // Verify the count has increased by 3
        assertEquals(initialCount + 3, allTrucks.size());
    }

    // Test retrieving Truck entities by property equality.
    // Verifies that trucks with the specified food type are correctly returned.
    @Test
    public void truckPropertyEqualSuccess() {
        // Check initial count of trucks with foodType "Italian"
        int beforeTruckCountByType = truckDao.getByPropertyEqual("foodType", "Italian").size();

        // Seed extra trucks
        truckDao.insert(new Truck("Pasta Palace", "Italian"));
        truckDao.insert(new Truck("Noodle Nook", "Asian"));
        truckDao.insert(new Truck("Pasta Point", "Italian"));

        // Retrieve trucks with foodType "Italian"
        List<Truck> italianTrucks = truckDao.getByPropertyEqual("foodType", "Italian");

        // Verify that the count has increased by 2
        assertEquals(beforeTruckCountByType + 2, italianTrucks.size());

        // Verify each returned truck's food type
        for (Truck truck : italianTrucks) {
            assertEquals("Italian", truck.getFoodType());
        }
    }

    // Test retrieving Truck entities by property pattern matching.
    // Verifies that trucks with names containing a like pattern are correctly returned.
    @Test
    public void truckPropertyLikeSuccess() {
        // Seed extra trucks
        truckDao.insert(new Truck("Taco Town", "Mexican"));
        truckDao.insert(new Truck("Taco Terrace", "Mexican"));
        truckDao.insert(new Truck("Burger Barn", "Burgers"));

        // Retrieve trucks with "Taco" in the name
        List<Truck> tacoTrucks = truckDao.getByPropertyLike("name", "Taco");
        logger.debug("Taco Trucks found: " + tacoTrucks.size());

        // Verify that 2 trucks are returned
        assertEquals(2, tacoTrucks.size());

        // Verify each returned truck's name contains "Taco"
        for (Truck truck : tacoTrucks) {
            assertTrue(truck.getName().contains("Taco"));
        }
    }

    // ----------------------------
    // Location CRUD
    // ----------------------------

    // Test inserting a Location entity.
    // Verifies that the location is correctly inserted and can be retrieved with the expected properties.
    @Test
    public void locationInsertSuccess() {
        // Seed a location
        int id = locationDao.insert(new Location("Test Plaza", "123 Main", "WI", 12345, "USA", 43.07, -89.40));

        // Retrieve location by ID
        Location testLocation = locationDao.getById(id);

        // Verify properties
        assertNotNull(testLocation);
        assertEquals("Test Plaza", testLocation.getName());
        assertEquals("123 Main", testLocation.getAddress());
        assertEquals("WI", testLocation.getState());
        assertEquals(12345, testLocation.getZip());
        assertEquals("USA", testLocation.getCountry());
        assertEquals(43.07, testLocation.getLatitude(), 0.0001);
        assertEquals(-89.40, testLocation.getLongitude(), 0.0001);
    }

    // Test updating a Location entity.
    // Verifies that the location's properties are correctly updated and persisted.
    @Test
    public void locationUpdateSuccess() {
        // Seed a location to update
        int id = locationDao.insert(new Location("Old Name", "456 Side St", "WI", 54321, "USA", 44.00, -88.00));

        // Update the location
        Location locationToUpdate = locationDao.getById(id);

        // Modify properties
        locationToUpdate.setName("New Name");
        locationToUpdate.setAddress("789 New St");
        locationToUpdate.setState("IL");
        locationToUpdate.setZip(60601);
        locationToUpdate.setCountry("USA");
        locationToUpdate.setLatitude(41.88);
        locationToUpdate.setLongitude(-87.63);
        locationDao.update(locationToUpdate);

        // Retrieve updated location
        Location updatedLocation = locationDao.getById(id);

        // Verify updated properties
        assertEquals("New Name", updatedLocation.getName());
        assertEquals("789 New St", updatedLocation.getAddress());
        assertEquals("IL", updatedLocation.getState());
        assertEquals(60601, updatedLocation.getZip());
        assertEquals("USA", updatedLocation.getCountry());
        assertEquals(41.88, updatedLocation.getLatitude(), 0.0001);
        assertEquals(-87.63, updatedLocation.getLongitude(), 0.0001);
    }

    // Test deleting a Location entity.
    // Verifies that the location is correctly deleted and cannot be retrieved afterwards.
    @Test
    public void locationDeleteSuccess() {
        // Seed a location to delete
        int id = locationDao.insert(new Location("Delete Me", "111 Nowhere", "WI", 12345, "USA", 0.0, 0.0));

        // Delete the location
        Location locationToDelete = locationDao.getById(id);
        locationDao.delete(locationToDelete);

        // Verify the location is deleted
        assertNull(locationDao.getById(id));
    }

    // Test that deleting a Location entity cascades to delete associated Schedule entities.
    // Verifies that schedules linked to the deleted location are also removed from the database.
    @Test
    public void locationDeletionCascadesToSchedules() {
        // Create truck, location, and schedule
        Truck testTruck = new Truck("Cascade Location Truck", "Test Food");
        truckDao.insert(testTruck);
        Location testLocation = new Location("Cascade Location", "123 Cascade St", "WI", 12345, "USA", 43.07, -89.40);
        int locationId = locationDao.insert(testLocation);
        Schedule testSchedule = new Schedule(testTruck, testLocation, "Saturday", "2024-10-12", "12:00", "16:00");
        int scheduleId = scheduleDao.insert(testSchedule);

        // Delete the location
        Location locationParent = locationDao.getById(locationId);
        locationDao.delete(locationParent);

        // Verify the schedule is also deleted
        assertNull(scheduleDao.getById(scheduleId));
    }

    // Test retrieving all Location entities.
    // Verifies that the correct number of locations is returned from getAll()/database and then checks their properties.
    @Test
    public void locationGetAllAndCheck() {
        // Check how many locations are in the DB
        int beforeCount = locationDao.getAll().size();

        // Seed data to populate 3 locations
        locationDao.insert(new Location("Location One", "111 First St", "WI", 11111, "USA", 43.00, -89.00));
        locationDao.insert(new Location("Location Two", "222 Second St", "WI", 22222, "USA", 44.00, -88.00));
        locationDao.insert(new Location("Location Three", "333 Third St", "WI", 33333, "USA", 45.00, -87.00));

        // Retrieve all locations again
        List<Location> allLocations = locationDao.getAll();

        // Verify the count has increased by 3
        assertEquals(beforeCount + 3, allLocations.size());
    }

    // Test retrieving Location entities by property equality.
    // Verifies that locations in the specified state are correctly returned.
    @Test
    public void locationPropertyEqualSuccess() {
        // Get initial count of locations in Wisconsin
        int beforeLocationCountByState = locationDao.getByPropertyEqual("state", "Wisconsin").size();

        // Seed extra locations
        locationDao.insert(new Location("Wisconsin Location 1", "123 WI St", "Wisconsin", 13579, "USA", 45.00, -87.00));
        locationDao.insert(new Location("Illinois Location", "456 IL St", "Illinois", 24680, "USA", 41.88, -87.63));
        locationDao.insert(new Location("Wisconsin Location 2", "789 WI Ave", "Wisconsin", 11223, "USA", 46.00, -86.00));

        // Retrieve locations in Wisconsin
        List<Location> wisLocations = locationDao.getByPropertyEqual("state", "Wisconsin");

        // Verify that the count has increased by 2
        assertEquals(beforeLocationCountByState + 2, wisLocations.size());
    }

    // Test retrieving Location entities by property pattern matching.
    // Verifies that locations with addresses containing a like pattern are correctly returned.
    @Test
    public void locationPropertyLikeSuccess() {
        // Get initial count of locations with "Street" in the address
        int beforeLocationCountByAddress = locationDao.getByPropertyLike("address", "Street").size();

        // Seed extra locations
        locationDao.insert(new Location("Street Location", "100 Street Ave", "WI", 13579, "USA", 45.00, -87.00));
        locationDao.insert(new Location("Avenue Location", "200 Avenue Rd", "WI", 24680, "USA", 46.00, -86.00));
        locationDao.insert(new Location("Boulevard Location", "300 Blvd Street", "WI", 11223, "USA", 47.00, -85.00));

        // Retrieve locations with "Street" in the address
        List<Location> streetLocations = locationDao.getByPropertyLike("address", "Street");

        // Verify that the count has increased by 2
        assertEquals(beforeLocationCountByAddress + 2, streetLocations.size());
    }

    // ----------------------------
    // Schedule CRUD
    // ----------------------------

    // Test inserting a Schedule entity.
    // Verifies that the schedule is correctly inserted and can be retrieved with the expected properties.
    @Test
    public void scheduleInsertSuccess() {
        // Create truck and location first
        Truck testTruck = new Truck("Schedule Test Truck", "Test Food");
        truckDao.insert(testTruck);
        Location testLocation = new Location("Schedule Test Location", "123 Schedule St", "WI", 12345, "USA", 43.07, -89.40);
        locationDao.insert(testLocation);
        Schedule testSchedule = new Schedule(testTruck, testLocation, "Wednesday", "2024-10-10", "10:00am", "02:00pm");
        int scheduleId = scheduleDao.insert(testSchedule);

        // Retrieve schedule by ID
        Schedule retrievedSchedule = scheduleDao.getById(scheduleId);

        // Verify properties
        assertNotNull(retrievedSchedule);
        assertEquals("Wednesday", retrievedSchedule.getDayOfWeek());
        assertEquals("2024-10-10", retrievedSchedule.getDate());
        assertEquals("10:00am", retrievedSchedule.getStartTime());
        assertEquals("02:00pm", retrievedSchedule.getEndTime());

        // Verify associated truck and location
        assertEquals(testTruck.getId(), retrievedSchedule.getTruck().getId());
        assertEquals(testLocation.getId(), retrievedSchedule.getLocation().getId());
    }

    // Test updating a Schedule entity.
    // Verifies that the schedule's properties are correctly updated and persisted.
    @Test
    public void scheduleUpdateSuccess() {
        // Create truck, location, and schedule
        Truck testTruck = new Truck("Update Test Truck", "Test Food");
        truckDao.insert(testTruck);
        Location testLocation = new Location("Update Test Location", "456 Update St", "WI", 54321, "USA", 44.00, -88.00);
        locationDao.insert(testLocation);
        Schedule scheduleToUpdate = new Schedule(testTruck, testLocation, "Wednesday", "2024-11-11", "11:00am", "03:00pm");
        int scheduleId = scheduleDao.insert(scheduleToUpdate);

        // Update the schedule
        Schedule retrievedSchedule = scheduleDao.getById(scheduleId);

        // Modify properties
        retrievedSchedule.setDayOfWeek("Thursday");
        retrievedSchedule.setDate("2024-12-12");
        retrievedSchedule.setStartTime("12:00");
        retrievedSchedule.setEndTime("16:00");
        scheduleDao.update(retrievedSchedule);

        // Retrieve updated schedule
        Schedule updatedSchedule = scheduleDao.getById(scheduleId);

        // Verify updated properties
        assertEquals("Thursday", updatedSchedule.getDayOfWeek());
        assertEquals("2024-12-12", updatedSchedule.getDate());
        assertEquals("12:00", updatedSchedule.getStartTime());
        assertEquals("16:00", updatedSchedule.getEndTime());
    }

    // Test deleting a Schedule entity.
    // Verifies that the schedule is correctly deleted and cannot be retrieved afterwards.
    @Test
    public void scheduleDeleteSuccess() {
        // Create truck, location, and schedule
        Truck testTruck = new Truck("Delete Test Truck", "Test Food");
        truckDao.insert(testTruck);
        Location testLocation = new Location("Delete Test Location", "789 Delete St", "WI", 67890, "USA", 45.00, -87.00);
        locationDao.insert(testLocation);
        Schedule scheduleToDelete = new Schedule(testTruck, testLocation, "Wednesday" , "2024-09-09", "09:00am", "01:00pm");
        int scheduleId = scheduleDao.insert(scheduleToDelete);

        // Get schedule by ID
        Schedule retrievedSchedule = scheduleDao.getById(scheduleId);

        // Delete the schedule
        scheduleDao.delete(retrievedSchedule);

        // Verify the schedule is deleted
        assertNull(scheduleDao.getById(scheduleId));
    }

    // Test that deleting a Schedule entity does not cascade to delete associated Truck or Location entities.
    // Verifies that the truck and location linked to the deleted schedule still exist in the database
    @Test
    public void scheduleDeletionDoesNotCascadeToTruckOrLocation() {
        // Create truck, location, and schedule
        Truck testTruck = new Truck("Non-Cascade Truck", "Test Food");
        int truckId = truckDao.insert(testTruck);
        Location testLocation = new Location("Non-Cascade Location", "321 NonCascade St", "WI", 11223, "USA", 46.00, -86.00);
        int locationId = locationDao.insert(testLocation);
        Schedule scheduleToDelete = new Schedule(testTruck, testLocation, "Sunday", "2024-08-08", "08:00am", "12:00pm");
        int scheduleId = scheduleDao.insert(scheduleToDelete);

        // Delete the schedule
        Schedule retrievedSchedule = scheduleDao.getById(scheduleId);
        scheduleDao.delete(retrievedSchedule);

        // Verify the truck still exists
        assertNotNull(truckDao.getById(truckId));

        // Verify the location still exists
        assertNotNull(locationDao.getById(locationId));
    }

    // Test retrieving all Schedule entities.
    // Verifies that the correct number of schedules is returned from getAll()/database and then checks their properties.
    @Test
    public void scheduleGetAllAndCheck() {
        // Get initial count of schedules
        int beforeCount = scheduleDao.getAll().size();

        // Add seed data to populate 2 schedules
        Truck testTruck1 = new Truck("All Check Truck 1", "Test Food");
        truckDao.insert(testTruck1);
        Location testLocation1 = new Location("All Check Location 1", "111 All St", "WI", 11111, "USA", 43.00, -89.00);
        locationDao.insert(testLocation1);
        Schedule schedule1 = new Schedule(testTruck1, testLocation1, "Monday", "2024-07-01", "10:00am", "02:00pm");
        scheduleDao.insert(schedule1);
        Truck testTruck2 = new Truck("All Check Truck 2", "Test Food");
        truckDao.insert(testTruck2);
        Location testLocation2 = new Location("All Check Location 2", "222 All St", "WI", 22222, "USA", 44.00, -88.00);
        locationDao.insert(testLocation2);
        Schedule schedule2 = new Schedule(testTruck2, testLocation2, "Tuesday", "2024-07-02", "11:00am", "03:00pm");
        scheduleDao.insert(schedule2);

        // Retrieve all schedules again
        List<Schedule> allSchedules = scheduleDao.getAll();

        // Verify the count has increased by 2
        assertEquals(beforeCount + 2, allSchedules.size());
    }

    // Test retrieving Schedule entities by property equality.
    // Verifies that schedules on the specified day of the week are correctly returned.
    @Test
    public void schedulePropertyEqualSuccess() {
        // Get initial count of schedules on Tuesday
        int beforeScheduleCountByDay = scheduleDao.getByPropertyEqual("dayOfWeek", "Tuesday").size();

        // Seed extra schedules
        Truck testTruck1 = new Truck("Tuesday Truck 1", "Test Food");
        truckDao.insert(testTruck1);
        Location testLocation1 = new Location("Tuesday Location 1", "123 Tuesday St", "WI", 12345, "USA", 43.07, -89.40);
        locationDao.insert(testLocation1);
        Schedule schedule1 = new Schedule(testTruck1, testLocation1, "Tuesday", "2024-09-03", "10:00am", "02:00pm");
        scheduleDao.insert(schedule1);
        Truck testTruck2 = new Truck("Wednesday Truck", "Test Food");
        truckDao.insert(testTruck2);
        Location testLocation2 = new Location("Wednesday Location", "456 Wednesday St", "WI", 54321, "USA", 44.00, -88.00);
        locationDao.insert(testLocation2);
        Schedule schedule2 = new Schedule(testTruck2, testLocation2, "Wednesday", "2024-09-04", "11:00am", "03:00pm");
        scheduleDao.insert(schedule2);

        // Retrieve schedules on Tuesday
        List<Schedule> tuesdaySchedules = scheduleDao.getByPropertyEqual("dayOfWeek", "Tuesday");

        // Verify that the count has increased by 1
        assertEquals(beforeScheduleCountByDay + 1, tuesdaySchedules.size());

        // Verify each returned schedule's day of the week
        for (Schedule schedule : tuesdaySchedules) {
            assertEquals("Tuesday", schedule.getDayOfWeek());
        }
    }

    // Test retrieving Schedule entities by property pattern matching.
    // Verifies that schedules with start times containing a like pattern are correctly returned.
    @Test
    public void schedulePropertyLikeSuccess() {
        // Get initial count of schedules with "am" in the start time
        int beforeScheduleCountByTime = scheduleDao.getByPropertyLike("startTime", "am").size();

        // Seed extra schedules
        Truck testTruck1 = new Truck("Morning Truck 1", "Test Food");
        truckDao.insert(testTruck1);
        Location testLocation1 = new Location("Morning Location 1", "123 Morning St", "WI", 12345, "USA", 43.07, -89.40);
        locationDao.insert(testLocation1);
        Schedule schedule1 = new Schedule(testTruck1, testLocation1, "Thursday", "2024-10-03", "09:00am", "01:00pm");
        scheduleDao.insert(schedule1);
        Truck testTruck2 = new Truck("Afternoon Truck", "Test Food");
        truckDao.insert(testTruck2);
        Location testLocation2 = new Location("Afternoon Location", "456 Afternoon St", "WI", 54321, "USA", 44.00, -88.00);
        locationDao.insert(testLocation2);
        Schedule schedule2 = new Schedule(testTruck2, testLocation2, "Friday", "2024-10-04", "1:00pm", "06:00pm");
        scheduleDao.insert(schedule2);

        // Retrieve schedules with "am" in the start time
        List<Schedule> timeSchedules = scheduleDao.getByPropertyLike("startTime", "am");

        // Verify that the count has increased by 1
        assertEquals(beforeScheduleCountByTime + 1, timeSchedules.size());

        // Verify each returned schedule's start time contains "am"
        for (Schedule schedule : timeSchedules) {
            assertTrue(schedule.getStartTime().contains("am"));
        }
    }
}