package com.foodtruck.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;


/**
 * This entity maps to the "food_trucks" table in the database.
 * Each truck can have multiple schedules associated with it.
 */
@Entity
@Table(name = "food_trucks")
public class Truck {

    /** Unique identifier for the truck */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    private int id;

    /** Name of the food truck */
    @Column(name = "name")
    private String name;

    /** Type of food served by the truck */
    @Column(name="food_type")
    private String foodType;

    /** List of schedules for this truck (not serialized to JSON) */
    @OneToMany(mappedBy = "truck", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Schedule> schedules;

    /**
     * Default Truck constructor
     *
     */
    public Truck() {

    }

    /**
     * Constructor that sets all variables
     *
     */
    public Truck(String name, String foodType) {
        this.name = name;
        this.foodType = foodType;
    }

    /**
     * Getter for the truck id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the truck id
     *
     * @param id - auto inc
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the truck name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for truck name
     *
     * @param name -the truck's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the truck's food type
     *
     * @return foodType
     */
    public String getFoodType() {
        return foodType;
    }

    /**
     * Setter for the trucks food type
     *
     * @param foodType - a truck's food type
     */
    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
}