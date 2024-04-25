
# RESTful API FOR USER MANAGING

This api provides functionality for managing user data.


## Steps to Setup

**1. Clone the application**

```bash
  https://github.com/ValeriiaHuza/clear_solution_huza_api.git
```

**2. Database Setup**

This application uses MySQL as the database. Before running the application, ensure that MySQL is installed on your system and create a database for the application. Follow these steps:

- **Install MySql:** If MySql is not already installed, download and install it from the official website: https://www.mysql.com/downloads/

- **Create Database**

```bash
  create database user_db
```

- **Or run SQL Script:** [user_db.sql](https://github.com/ValeriiaHuza/clear_solution_huza_api/blob/main/user_db.sql) script

**3. Change Database Configuration**

- open `src/main/resources/application.properties`

- change `spring.datasource.username` and `spring.datasource.password`

**4. Build and run the app using maven**

```bash
  mvn clean spring-boot:run
```

The app will start running at http://localhost:8080.

## Rest API

#### Retrieve all users

```http
  GET /api/users
```

- Response:
   - Status: 200 OK
   - Body: List of user objects as JSON

#### Retrieve a user by ID

```http
  GET /api/users/{userId}
```

- Parameters:
  - userId: Integer (Path variable) - ID of the user to retrieve.
- Response:
   - Status: 
       - 200 OK
       - 404 Not found
   - Body: User object as JSON

#### Add a new user

```http
  POST /api/users
```

- Request Body: User object (JSON)
- Response:
   - Status:
       - 201 Created: User added successfully.
       - 400 Bad Request: Unable to add user (validation error or other issues)
    - Body: User object (if created successfully)

#### Update an existing user

```http
  PUT /api/users/{userId}
```

- Parameters:
   - userId: Integer (Path variable) - ID of the user to update.
- Request Body: User object (JSON)
- Response:
    - Status: 
        - 200 OK
        - 400 Bad Request: Unable to add user (validation error or other issues)
    - Body: User object (if updated  successfully)

#### Delete a user by ID

```http
  DELETE /api/users/{userId}
```

- Parameters:
   - userId: Integer (Path variable) - ID of the user to delete.
- Response:
   - Status: 200 Ok

#### Search users by birth date range.

```http
  GET /api/users/range
```

- Query Parameters:
    - from: String (Query parameter) - Start date of the range (format: YYYY-MM-DD).
    - to: String (Query parameter) - End date of the range (format: YYYY-MM-DD).
- Response:
    - Status:
      -  200 OK
      -  404 Bad Request (if the "From" date is less than the "To" date or other issues)
- Body: List of user objects
