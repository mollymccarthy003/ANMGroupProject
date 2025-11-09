package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    private int id;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "date")
    private String date;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;


    public Schedule() {

    }

    public Schedule(int truckId, int locationId, String dayOfWeek, String date, String startTime, String endTime ) {
        // TODO: implement these DAO's
        Truck truckData = new TruckData();
        Location locationData = new LocationData();

        Truck truck = truckData.getById(truckId);
        Location location = locationData.getById(locationId);

        this.truck = truck;
        this.location = location;
        this.dayOfWeek = dayOfWeek;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Getter for id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for truck id
     *
     * @return truck
     */
    public Truck getTruck() {
        return truck;
    }

    /**
     * Setter for truck
     *
     * @param truck - a food truck
     */
    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    /**
     * Getter for location
     *
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Getter for day of the week
     *
     * @return day of the week
     */
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Setter for day of the week
     *
     * @param dayOfWeek - the day of the week
     */
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Getter for date
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter for date
     *
     * @param date - a date (e.g. 5/7/2026)
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter for start time
     *
     * @return startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Setter for start time
     *
     * @param startTime - start time (e.g. 8:00am)
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter for end time
     *
     * @return endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Setter for end time
     *
     * @param endTime - end time (e.g. 4:00pm)
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}