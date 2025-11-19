package com.foodtruck.testsupport;

import com.foodtruck.persistence.GenericDao;
import org.junit.Before;

/**
 * Abstract class to reset database before each test
 */
public abstract class DbReset {
    @Before
    public void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    /**
     * Helper method to create a GenericDao for a given type
     *
     * @param type the class type for the DAO
     * @param <T>  the type parameter
     * @return a GenericDao for the specified type
     */
    protected <T> GenericDao<T> dao(Class<T> type) {
        return new GenericDao<>(type);
    }
}
