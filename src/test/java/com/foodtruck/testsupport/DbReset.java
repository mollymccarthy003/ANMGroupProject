package com.foodtruck.testsupport;

import com.foodtruck.persistence.GenericDao;
import org.junit.Before;

public abstract class DbReset {
    @Before
    public void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    // Mini-helper method to get dao objects
    protected <T> GenericDao<T> dao(Class<T> type) {
        return new GenericDao<>(type);
    }
}
