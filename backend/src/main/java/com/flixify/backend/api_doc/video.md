**1. Get All Videos for a User**

- **Endpoint**: `GET /videos`
- **Description**: Retrieves all videos uploaded or associated with a specific user.
- **Query Parameters**:
  - `userId` (Integer, required): ID of the user whose videos are to be fetched.
- **Responses**:
  - `200 OK`: Successfully retrieved list of videos.
  - `404 Not Found`: User does not exist.
- **Response Body**:
  - `ResponseDto`: Contains a list of videos.

---
