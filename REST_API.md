# REST API Documentation

> This documentation describes the REST API endpoints for **Flixify**, a microservice designed for adaptive video upload, processing, and delivery inspired by S3 and YouTube's adaptive streaming.

---

## 🧑 User API

### Create New User

- **Method**: `POST`
- **URL**: `/v1/api/user`
- **Body** (JSON):

```json
{
  "userName": "dharmaraj.rd",
  "displayName": "Dharmaraj R",
  "email": "dharmaraj@gmail.com",
  "phoneNumber": "123456789",
  "password": "myPassword"
}
```

- **Response**: `201 Created` or error response.

## 🎥 Video API

### Upload Video

- **Method**: `POST`
- **URL**: `/v1/api/video/upload`
- **Body** (form-data):

  - `file`: Video file
  - `meta`: JSON string with metadata

```json
{
  "title": "Leo",
  "userId": 1,
  "videoSplitterRule": "DURATION_BASED_VIDEO_SPLITTER"
}
```

- **Response**: `200 OK` with video details.

### Get All Videos of a User

- **Method**: `GET`
- **URL**: `/v1/api/video?userId=1`
- **Response**: List of uploaded videos.

### Get All Videos of a User in the trash

- **Method**: `GET`
- **URL**: `/v1/api/video/trash?userId=1`
- **Response**: List of videos in the trash.

### Get Video Info

- **Method**: `GET`
- **URL**: `/v1/api/video/{videoId}?userId=1`
- **Response**: Detailed video metadata.

### Get All Chunks of a Video

- **Method**: `GET`
- **URL**: `/v1/api/video/{videoId}/chunks?userId=1`
- **Response**: Chunk metadata by resolution and order.

### Get Specific Chunk Media

- **Method**: `GET`
- **URL**: `/v1/api/video/{videoId}/chunk/{chunkNumber}/resolution/{resolution}?userId=1`
- **Response**: Video chunk media.

### Delete a Video and All Chunks

- **Method**: `DELETE`
- **URL**: `/v1/api/video/{videoId}?userId=1`
- **Response**: Deletion confirmation.

### Recover the deleted video

- **Method**: `PUT`
- **URL**: `/v1/api/video/recover/{videoId}?userId=1`
- **Response**: Recovery confirmation.

---

## 📄 Manifest API

### Get Manifest.json (All Resolutions)

- **Method**: `GET`
- **URL**: `/v1/api/manifest/video/{videoId}?userId=1`
- **Response**: Manifest file for adaptive playback.

### Get Manifest.json by Resolution

- **Method**: `GET`
- **URL**: `/v1/api/manifest/video/{videoId}/resolution/{resolution}?userId=1`
- **Response**: Resolution-specific manifest.

---

## 📈 Media Analytics API

### Get Media Download Stats

- **Method**: `GET`
- **URL**: `/v1/api/media/stats/{videoId}?userId=1`
- **Response**: Statistics such as download count per resolution/chunk.

---

## ⚙️ Notes

- All endpoints are currently unauthenticated (`NoAuth`).
- Video processing is asynchronous; upload returns immediately while processing continues in the background.
- Manifest files are generated only after video chunking and encoding are complete.

---

## 🔜 Planned Enhancements

- Auth integration (JWT or OAuth2)
- CDN support and signed URLs
- Retry/resume support for failed uploads
- Real-time processing status via WebSocket or polling endpoint
