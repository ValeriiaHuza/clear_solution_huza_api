
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
