### 📄 **API Documentation**

**1. Get All Chunks of a Video**

- **Endpoint**: `GET /videos/{videoId}/chunks`
- **Description**: Retrieves all the video chunks associated with a specific video ID for a given user.
- **Query Parameters**:
  - `userId` (Integer, required): ID of the user requesting the chunks.
- **Path Parameters**:
  - `videoId` (Integer, required): ID of the video for which chunks need to be fetched.
- **Responses**:
  - `200 OK`: Successfully retrieved chunks.
    - Body: `ResponseDto` containing chunk details.
  - `404 Not Found`: User or video does not exist.
  - `403 Forbidden`: User does not have permission to access this video.
- **Exceptions**:
  - `UserNotFound`
  - `VideoNotExist`
  - `PermissionDenied`

---
