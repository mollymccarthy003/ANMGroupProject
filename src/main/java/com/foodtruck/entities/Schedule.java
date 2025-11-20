package com.foodtruck.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * This entity maps to the "schedules" table in the database.
 * Each schedule links a Truck to a Location and includes day, date, and start/end times.
 */
@Entity
@Table(name = "schedules")
public class Schedule {

    /** Unique identifier for the schedule */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    @Schema(readOnly = true)
    private int id;

    /** Truck assigned to this schedule */
    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;

    /** Location assigned to this schedule */
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    /** Day of the week for this schedule (e.g., Monday) */
    @Column(name = "day_of_week")
    private String dayOfWeek;

    /** Date for this schedule  */
    @Column(name = "date")
    private String date;

    /** Start time for this schedule  */
    @Column(name = "start_time")
    private String startTime;

    /** End time for this schedule */
    @Column(name = "end_time")
    private String endTime;

    /**
     * Default constructor
     *
     */
    public Schedule() {

    }

    /**
     * Constructor that sets all variables
     *
     * @param truck - object to get Truck data
     * @param location - object to get Location data
     * @param dayOfWeek - day of the week
     * @param date - date
     * @param startTime - state time
     * @param endTime - end time
     */
    public Schedule(Truck truck, Location location, String dayOfWeek, String date, String startTime, String endTime ) {
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
     * Setter for id
     *
     */
    public void setId(int id) {
        this.id = id;
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

    /** @return the location (alias for getLocation) */
    public Location getLocationId() { return location; }
}