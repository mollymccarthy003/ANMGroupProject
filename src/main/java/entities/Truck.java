package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "food_trucks")
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name="food_type")
    private String foodType;

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
     * Setter for truck id
     *
     */
    public void setId(int id) {

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