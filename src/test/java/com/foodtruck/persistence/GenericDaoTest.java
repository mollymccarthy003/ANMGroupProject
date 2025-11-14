package com.foodtruck.persistence;

import com.foodtruck.entities.Location;
import com.foodtruck.entities.Schedule;
import com.foodtruck.entities.Truck;
import com.foodtruck.testsupport.DbReset;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

public class GenericDaoTest extends DbReset {

    private GenericDao<Truck> truckDao;
    private GenericDao<Location> locationDao;
    private GenericDao<Schedule> scheduleDao;

    @Before
    public void initializeDaos() {
        truckDao = dao(Truck.class);
        locationDao = dao(Location.class);
        scheduleDao = dao(Schedule.class);
    }

    //----------------------------
    // Truck CRUD
    //----------------------------
    @Test
    public void truckInsertSuccess() {
        int id = truckDao.insert(new Truck("Travelling Taco", "Authentic Mexican"));
        Truck testTruck = truckDao.getById(id);
        assertNotNull(testTruck);
        assertEquals("Travelling Taco", testTruck.getName());
        assertEquals("Authentic Mexican", testTruck.getFoodType());
    }

    @Test
    public void truckUpdateSuccess() {
        int id = truckDao.insert(new Truck("Rolling Ribs", "BBQ"));
        Truck truckToUpdate = truckDao.getById(id);
        truckToUpdate.setName("The Rolling Rib Rack");
        truckToUpdate.setFoodType("BBQ and More");
        truckDao.update(truckToUpdate);
        Truck updatedTruck = truckDao.getById(id);
        assertEquals("The Rolling Rib Rack", updatedTruck.getName());
        assertEquals("BBQ and More", updatedTruck.getFoodType());
    }

    @Test
    public void truckDeleteSuccess() {
        // Need to add cascade delete for schedules later
        int id = truckDao.insert(new Truck("Tart Truck", "Desserts"));
        Truck truckToDelete = truckDao.getById(id);
        truckDao.delete(truckToDelete);
        Truck deletedTruck = truckDao.getById(id);
        assertNull(truckDao.getById(id));
    }

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

    @Test
    public void truckGetAllAndCheck() {
        truckDao.insert(new Truck("Burger Bus", "Burgers"));
        truckDao.insert(new Truck("Sushi Stop", "Sushi"));
        truckDao.insert(new Truck("Curry Cart", "Indian"));
        List<Truck> allTrucks = truckDao.getAll();
        // TODO: Better to check for at least 4 since seed data populates one truck
        assertEquals(4, allTrucks.size());
    }

    @Test
    public void truckPropertyEqualSuccess() {
        truckDao.insert(new Truck("Pasta Palace", "Italian"));
        truckDao.insert(new Truck("Noodle Nook", "Asian"));
        truckDao.insert(new Truck("Pasta Point", "Italian"));
        List<Truck> italianTrucks = truckDao.getByPropertyEqual("foodType", "Italian");
        assertEquals(2, italianTrucks.size());
        for (Truck truck : italianTrucks) {
            assertEquals("Italian", truck.getFoodType());
        }
    }

    @Test
    public void truckPropertyLikeSuccess() {
        truckDao.insert(new Truck("Taco Town", "Mexican"));
        truckDao.insert(new Truck("Taco Terrace", "Mexican"));
        truckDao.insert(new Truck("Burger Barn", "Burgers"));
        List<Truck> tacoTrucks = truckDao.getByPropertyLike("name", "Taco");
        assertEquals(2, tacoTrucks.size());
        for (Truck truck : tacoTrucks) {
            assertTrue(truck.getName().contains("Taco"));
        }
    }

    // ----------------------------
    // Location CRUD
    // ----------------------------
    @Test
    public void locationInsertSuccess() {
        int id = locationDao.insert(new Location("Test Plaza", "123 Main", "WI", 12345, "USA", 43.07, -89.40));
        Location testLocation = locationDao.getById(id);
        assertNotNull(testLocation);
        assertEquals("Test Plaza", testLocation.getName());
        assertEquals("123 Main", testLocation.getAddress());
        assertEquals("WI", testLocation.getState());
        assertEquals(12345, testLocation.getZip());
        assertEquals("USA", testLocation.getCountry());
        assertEquals(43.07, testLocation.getLatitude(), 0.0001);
        assertEquals(-89.40, testLocation.getLongitude(), 0.0001);
    }

    @Test
    public void locationUpdateSuccess() {
        int id = locationDao.insert(new Location("Old Name", "456 Side St", "WI", 54321, "USA", 44.00, -88.00));
        Location locationToUpdate = locationDao.getById(id);
        locationToUpdate.setName("New Name");
        locationToUpdate.setAddress("789 New St");
        locationToUpdate.setState("IL");
        locationToUpdate.setZip(60601);
        locationToUpdate.setCountry("USA");
        locationToUpdate.setLatitude(41.88);
        locationToUpdate.setLongitude(-87.63);
        locationDao.update(locationToUpdate);
        Location updatedLocation = locationDao.getById(id);
        assertEquals("New Name", updatedLocation.getName());
        assertEquals("789 New St", updatedLocation.getAddress());
        assertEquals("IL", updatedLocation.getState());
        assertEquals(60601, updatedLocation.getZip());
        assertEquals("USA", updatedLocation.getCountry());
        assertEquals(41.88, updatedLocation.getLatitude(), 0.0001);
        assertEquals(-87.63, updatedLocation.getLongitude(), 0.0001);
    }

    @Test
    public void locationDeleteSuccess() {
        int id = locationDao.insert(new Location("Delete Me", "111 Nowhere", "WI", 12345, "USA", 0.0, 0.0));
        Location locationToDelete = locationDao.getById(id);
        locationDao.delete(locationToDelete);
        assertNull(locationDao.getById(id));
    }

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

    @Test
    public void locationGetAllAndCheck() {
        locationDao.insert(new Location("Location One", "111 First St", "WI", 11111, "USA", 43.00, -89.00));
        locationDao.insert(new Location("Location Two", "222 Second St", "WI", 22222, "USA", 44.00, -88.00));
        List<Location> allLocations = locationDao.getAll();
        assertEquals(3, allLocations.size());
    }

    @Test
    public void locationPropertyEqualSuccess() {
        List<Location> wisLocations = locationDao.getByPropertyEqual("state", "Wisconsin");
        assertEquals(1, wisLocations.size());
    }

    @Test
    public void locationPropertyLikeSuccess() {
        List<Location> streetLocations = locationDao.getByPropertyLike("address", "Street");
        assertEquals(1, streetLocations.size());
    }

    // ----------------------------
    // Schedule CRUD
    // ----------------------------
    @Test
    public void scheduleInsertSuccess() {
        Truck testTruck = new Truck("Schedule Test Truck", "Test Food");
        truckDao.insert(testTruck);
        Location testLocation = new Location("Schedule Test Location", "123 Schedule St", "WI", 12345, "USA", 43.07, -89.40);
        locationDao.insert(testLocation);
        Schedule testSchedule = new Schedule(testTruck, testLocation, "Wednesday", "2024-10-10", "10:00", "14:00");
        int scheduleId = scheduleDao.insert(testSchedule);
        Schedule retrievedSchedule = scheduleDao.getById(scheduleId);
        assertNotNull(retrievedSchedule);
        assertEquals("Wednesday", retrievedSchedule.getDayOfWeek());
        assertEquals("2024-10-10", retrievedSchedule.getDate());
        assertEquals("10:00", retrievedSchedule.getStartTime());
        assertEquals("14:00", retrievedSchedule.getEndTime());
        assertEquals(testTruck.getId(), retrievedSchedule.getTruck().getId());
        assertEquals(testLocation.getId(), retrievedSchedule.getLocation().getId());
    }

    @Test
    public void scheduleDeletionDoesNotDeleteParents() {
        Truck testTruck = new Truck("Keep Truck", "Test Food");
        int truckId = truckDao.insert(testTruck);
        Location testLocation = new Location("Keep Location", "999 Nowhere St", "WI", 99999, "USA", 0.0, 0.0);
        int locationId = locationDao.insert(testLocation);
        Schedule testSchedule = new Schedule(testTruck, testLocation, "Monday", "2024-10-01", "09:00", "13:00");
        int scheduleId = scheduleDao.insert(testSchedule);

        // Delete schedule
        scheduleDao.delete(scheduleDao.getById(scheduleId));

        // Verify truck and location still exist
        assertNotNull(truckDao.getById(truckId));
        assertNotNull(locationDao.getById(locationId));
    }

    @Test
    public void scheduleUpdateSuccess() {
        Truck testTruck = new Truck("Update Test Truck", "Test Food");
        truckDao.insert(testTruck);
        Location testLocation = new Location("Update Test Location", "456 Update St", "WI", 54321, "USA", 44.00, -88.00);
        locationDao.insert(testLocation);
        Schedule scheduleToUpdate = new Schedule(testTruck, testLocation, "Wednesday", "2024-11-11", "11:00", "15:00");
        int scheduleId = scheduleDao.insert(scheduleToUpdate);
        Schedule retrievedSchedule = scheduleDao.getById(scheduleId);
        retrievedSchedule.setDayOfWeek("Thursday");
        retrievedSchedule.setDate("2024-12-12");
        retrievedSchedule.setStartTime("12:00");
        retrievedSchedule.setEndTime("16:00");
        scheduleDao.update(retrievedSchedule);
        Schedule updatedSchedule = scheduleDao.getById(scheduleId);
        assertEquals("Thursday", updatedSchedule.getDayOfWeek());
        assertEquals("2024-12-12", updatedSchedule.getDate());
        assertEquals("12:00", updatedSchedule.getStartTime());
        assertEquals("16:00", updatedSchedule.getEndTime());
    }

    @Test
    public void scheduleDeleteSuccess() {
        Truck testTruck = new Truck("Delete Test Truck", "Test Food");
        truckDao.insert(testTruck);
        Location testLocation = new Location("Delete Test Location", "789 Delete St", "WI", 67890, "USA", 45.00, -87.00);
        locationDao.insert(testLocation);
        Schedule scheduleToDelete = new Schedule(testTruck, testLocation, "Wednesday" , "2024-09-09", "09:00", "13:00");
        int scheduleId = scheduleDao.insert(scheduleToDelete);
        Schedule retrievedSchedule = scheduleDao.getById(scheduleId);
        scheduleDao.delete(retrievedSchedule);
        assertNull(scheduleDao.getById(scheduleId));
    }

    @Test
    public void scheduleDeletionDoesNotCascadeToTruckOrLocation() {
        Truck testTruck = new Truck("Non-Cascade Truck", "Test Food");
        int truckId = truckDao.insert(testTruck);
        Location testLocation = new Location("Non-Cascade Location", "321 NonCascade St", "WI", 11223, "USA", 46.00, -86.00);
        int locationId = locationDao.insert(testLocation);
        Schedule scheduleToDelete = new Schedule(testTruck, testLocation, "Sunday", "2024-08-08", "08:00", "12:00");
        int scheduleId = scheduleDao.insert(scheduleToDelete);

        // Delete the schedule
        Schedule retrievedSchedule = scheduleDao.getById(scheduleId);
        scheduleDao.delete(retrievedSchedule);

        // Verify the truck and location still exist
        assertNotNull(truckDao.getById(truckId));
        assertNotNull(locationDao.getById(locationId));
    }

    @Test
    public void scheduleGetAllAndCheck() {
        List<Schedule> allSchedules = scheduleDao.getAll();
        // Seed data populates 2 schedules
        assertEquals(2, allSchedules.size());
    }

    @Test
    public void schedulePropertyEqualSuccess() {
        List<Schedule> tuesdaySchedules = scheduleDao.getByPropertyEqual("dayOfWeek", "Tuesday");
        assertEquals(1, tuesdaySchedules.size());
        for (Schedule schedule : tuesdaySchedules) {
            assertEquals("Tuesday", schedule.getDayOfWeek());
        }
    }

    @Test
    public void schedulePropertyLikeSuccess() {
        List<Schedule> timeSchedules = scheduleDao.getByPropertyLike("startTime", "am");
        assertEquals(2, timeSchedules.size());
        for (Schedule schedule : timeSchedules) {
            assertTrue(schedule.getStartTime().contains("am"));
        }
    }
}