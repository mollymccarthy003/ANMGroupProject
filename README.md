## 🧠 Problem Statement

Finding food trucks can be a delicious adventure—but it’s often tricky to know when and where your favorite trucks will show up. The **Food Truck Locator API** solves this problem by providing a simple, developer-friendly way to access real-time information about food trucks, their locations, and operating schedules.

Instead of building their own databases from scratch, **developers** can use this API to power mobile apps, websites, or city guides that help hungry customers track and discover food trucks by date or location. The API also gives food truck owners an easy way to update their schedules, promote their business, and connect directly with their fans.

---

## 🎯 Objectives

- Provide a well-documented RESTful API for developers to integrate food truck data into apps or websites.
- Allow food truck owners to add, update, or delete their truck information and schedules.
- Enable filtering and search by location or date (e.g., “Show all trucks in Madison today”).
- Ensure that API responses are consistent, well-structured, and easily consumable by frontend or mobile clients.
- Build the foundation for a future public-facing Food Truck Locator web or mobile application.

## Database Design
### Table 1: `food_trucks`

| Column      | Type | Description |
|-------------|------|-------------|
| `id`        | INT (PK, auto_increment) | Unique ID for each truck |
| `name`      | VARCHAR(100) | Food truck name |
| `food_type` | VARCHAR(100) | Owner’s name |
| `website`   | VARCHAR(255) | Truck’s website or social media link |

---

### Table 2: `locations`

| Column      | Type                     | Description           |
|-------------|--------------------------|-----------------------|
| `id`        | INT (PK, auto_increment) | Unique schedule entry ID |
| `name`      | VARCHAR(100)             | Name of location      |
| `address`   | VARCHAR(100)             | Address of location   |
| `state`     | VARCHAR(15)              | State                 |
| `zip`       | CHAR(5)                  | Zip Code              |
| `country`   | VARCHAR(100)             | Country               |
| `latitude`  | DECIMAL(9,6)             | Latitude              |
| `longitude` | DECIMAL(9,6)             | Longitude             |

---
### Table 3: `schedules`

| Column        | Type                     | Description              |
|---------------|--------------------------|--------------------------|
| `id`          | INT (PK, auto_increment) | Unique schedule entry ID |
| `truck_id`    | INT FK               | References `food_trucks(id)` |
| `location_id` | INT FK               | References `locations(id)`   |
| `day_of_week` | VARCHAR(25)              | Day of week              |
| `date`        | VARCHAR(15)              | Date                     |
| `start_time`  | VARCHAR(100)             | Start time at location   |
| `end_time`    | VARCHAR(100)              | End time  at location    |


---

## API Endpoints

| Method | Endpoint                       | Description |
|--------|--------------------------------|-------------|
| `GET` | `/api/trucks`                   | Get all food trucks |
| `GET` | `/api/trucks/{id}`              | Get a single food truck|
| `POST` | `/api/trucks`                  | Add a new food truck |
| `PUT` | `/api/trucks/{id}`              | Update truck details |
| `DELETE` | `/api/trucks/{id}`           | Delete a food truck |
| `GET` | `/api/schedule`                 | Get all schedule entries |
| `GET` | `/api/schedule?date={YYYY-MM-DD}` | Get trucks for a specific date |
| `GET` | `/api/schedule?location_id={id}` | Get trucks at a specific location (by ID) |
| `POST` | `/api/schedule`                | Add a schedule entry for a truck (`truck_id` + `location_id`) |
| `DELETE` | `/api/schedule/{id}`         | Remove a schedule entry |

---

## Roles (TBD)

| Name     | Role                       | Description                                                                              |
|----------|----------------------------|------------------------------------------------------------------------------------------|
| Nick     | Swagger/API Documentation/UI | Write Swagger Annotations, test endpoints, hosted swagger ui, |
| Molly M  | API Endpoints| Controllers/servlets working, json response for each endpoint, integrate w/dao|
| Austin S | Database and Models | SQL script for tables/relationships, java entity and dto classes, sample data for testing |