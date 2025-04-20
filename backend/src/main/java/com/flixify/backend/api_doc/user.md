### 📄 **API Documentation**

**1. Add a New User**

- **Endpoint**: `POST /user`
- **Description**: Creates a new user in the system.
- **Request Body**:
  - `AddUserDto` (JSON): Contains user registration details.
- **Responses**:
  - `201 Created`: User successfully created.
  - `400 Bad Request`: Invalid input data.
- **Response Body**:
  - `ResponseDto`: Contains confirmation and user details.

---

**2. Get User Details**

- **Endpoint**: `GET /user`
- **Description**: Retrieves user information by user ID.
- **Query Parameters**:
  - `userId` (Integer, required): ID of the user to retrieve.
- **Responses**:
  - `200 OK`: Successfully fetched user details.
  - `404 Not Found`: User does not exist.
- **Response Body**:
  - `ResponseDto`: Contains user information.

---
