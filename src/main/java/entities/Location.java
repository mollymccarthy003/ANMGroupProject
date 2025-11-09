package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private int zip;

    @Column(name = "country")
    private String country;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    /**
     * Default Location constructor
     *
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
    public double getLatitude() {
        return latitude;
    }

    /**
     * Setter fpr latitude
     *
     * @param latitude - location latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter for longitude
     *
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Setter fpr longitude
     *
     * @param longitude - location longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}