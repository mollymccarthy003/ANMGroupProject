-- Drop tables
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS food_trucks;


-- Creates food trucks table --
CREATE TABLE food_trucks (
    id          INT             AUTO_INCREMENT  PRIMARY KEY,
    name        VARCHAR(100)    NOT NULL,
    food_type   VARCHAR(100)    NOT NULL
);

-- Creates locations table --
CREATE TABLE locations (
    id          INT             AUTO_INCREMENT  PRIMARY KEY,
    name        VARCHAR(100)    NULL,
    address     VARCHAR(100)    NOT NULL,
    state       VARCHAR(15)     NOT NULL,
    zip         CHAR(5)         NOT NULL,
    country     VARCHAR(100)    NOT NULL,
    latitude    DECIMAL(9,6)    NULL,
    longitude   DECIMAL(9,6)    NULL
);

-- Creates schedules table with foreign key constraints --
CREATE TABLE schedules (
    id          INT             AUTO_INCREMENT  PRIMARY KEY,
    truck_id    INT             NOT NULL,
    location_id INT             NOT NULL,
    day_of_week VARCHAR(25)     NOT NULL,
    date        VARCHAR(15)     NOT NULL,
    start_time  VARCHAR(10)     NOT NULL,
    end_time    VARCHAR(10)     NOT NULL,

    CONSTRAINT `fk_schedule_truck` FOREIGN KEY (truck_id)
        REFERENCES food_trucks(id)
        ON DELETE CASCADE,

    CONSTRAINT `fk_schedule_location` FOREIGN KEY (location_id)
        REFERENCES locations(id)
        ON DELETE CASCADE
);

-- Insert test data -
INSERT INTO food_trucks
    (name, food_type)
VALUES
    ('ANM Burger Buds', 'Burgers')
;

INSERT INTO locations
    (name, address, state, zip, country)
VALUES
    ('The Square', '101 State Street', 'Wisconsin', 53703, 'United States')
;

INSERT INTO schedules
    (truck_id, location_id, day_of_week, date, start_time, end_time)
VALUES
    (1, 1, 'Monday', '5/7/2026', '8:00am', '4:00pm')

;
INSERT INTO schedules
    (truck_id, location_id, day_of_week, date, start_time, end_time)
VALUES
    (1, 1, 'Tuesday', '5/8/2026', '8:00am', '4:00pm')
;