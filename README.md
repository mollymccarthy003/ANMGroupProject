### Problem Statement

Food trucks are constantly on the move, making it difficult for customers to know where their favorite trucks will be on a given day. The goal of this project is to build a Food Truck Locator API that allows users to find which food trucks are operating at specific locations and dates. The API will also enable food truck owners to manage their trucks’ schedules and provide website links for more information or ordering. 

## Objectives

- Allow users to view all food trucks and their upcoming locations.
- Allow truck owners to add, update, or delete their food trucks and schedules.
- Enable filtering by location or date (e.g., “Show all trucks in Madison today”).  

## Database Design (FINAL TBD)
### Table 1: `food_truck`

| Column | Type | Description |
|--------|------|-------------|
| `id` | INT (PK, auto_increment) | Unique ID for each truck |
| `name` | VARCHAR(100) | Food truck name |
| `owner` | VARCHAR(100) | Owner’s name |
| `website` | VARCHAR(255) | Truck’s website or social media link |

---

### Table 2: `truck_schedule`

| Column | Type | Description |
|--------|------|-------------|
| `id` | INT (PK, auto_increment) | Unique schedule entry ID |
| `food_truck_id` | INT (FK) | References `food_truck(id)` |
| `location` | VARCHAR(255) | Where the truck will be located (address, park name, etc.) |
| `date` | DATE | Date the truck will be at that location |

**Relationship:** One `food_truck` → Many `truck_schedule`

---

## API Endpoints

| Method | Endpoint | Description |
|--------|-----------|-------------|
| `GET` | `/api/trucks` | Get all food trucks |
| `GET` | `/api/trucks/{id}` | Get a single food truck and its schedule |
| `POST` | `/api/trucks` | Add a new food truck |
| `PUT` | `/api/trucks/{id}` | Update truck details |
| `DELETE` | `/api/trucks/{id}` | Delete a food truck |
| `GET` | `/api/schedule` | Get all scheduled locations |
| `GET` | `/api/schedule?date={date}` | Get trucks for a specific date |
| `GET` | `/api/schedule?location={location}` | Get trucks at a specific location |
| `POST` | `/api/schedule` | Add a schedule entry for a truck |
| `DELETE` | `/api/schedule/{id}` | Remove a schedule entry |

---

## Roles (TBD)

| Name           | Role                       | Description                                                                              |
|----------------|----------------------------|------------------------------------------------------------------------------------------|
| Molly McCarthy | Swagger/API Documentation/UI | Write Swagger Annotations, test endpoints, hosted swagger ui, |
| Nick | API Endpoints| Controllers/servlets working, json response for each endpoint, integrate w/dao|
|Austin S | Database and Models | SQL script for tables/relationships, java entity and dto classes, sample data for testing |