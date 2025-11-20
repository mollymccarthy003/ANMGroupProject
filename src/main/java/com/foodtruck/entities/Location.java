package com.foodtruck.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

/**
 * This entity maps to the "locations" table in the database.
 * A location can have multiple schedules associated with it.
 */
@Entity
@Table(name = "locations")
public class Location {

    /** Unique identifier for the location */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    @Schema(readOnly = true)
    private int id;

    /** Name of the location */
    @Column(name = "name")
    private String name;

    /** Street address of the location */
    @Column(name = "address")
    private String address;

    /** State where the location is situated */
    @Column(name = "state")
    private String state;

    /** Zip code of the location */
    @Column(name = "zip")
    private int zip;

    /** Country of the location */
    @Column(name = "country")
    private String country;

    /** Latitude coordinate of the location */
    @Column(name = "latitude")
    private Double latitude;

    /** Longitude coordinate of the location */
    @Column(name = "longitude")
    private Double longitude;

    /** Schedules associated with this location (not serialized in JSON) */
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Schedule> schedules;

    /**
     * Default constructor required by JPA.
     */
    public Location() {

    }

    /**
     * Location constructor that sets all variables
     *
     * @param name - location name
     * @param address - location address
     * @param state - location state
     * @param zip - location zip
     * @param country - location country
     * @param latitude - location latitude
     * @param longitude - location longitude
     */
    public Location(String name, String address, String state, int zip, String country, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    /**
     * Getter for location id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for location id
     *
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for location name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for location name
     *
     * @param name - location name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for address
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for address
     *
     * @param address - location address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for state
     *
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * Setter for state
     *
     * @param state - location state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Getter for zip
     *
     * @return zip
     */
    public int getZip() {
        return zip;
    }

    /**
     * Setter fpr zip
     *
     * @param zip - location zip
     */
    public void setZip(int zip) {
        this.zip = zip;
    }

    /**
     * Getter for country
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Getter for latitude
     *
     * @return latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Setter fpr latitude
     *
     * @param latitude - location latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter for longitude
     *
     * @return longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Setter fpr longitude
     *
     * @param longitude - location longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}