# 🎬 Flixify

**Flixify** is a microservice-based video processing solution built with **Spring Boot**, **PostgreSQL**, and **React**. It allows OTT platforms to upload videos, split them into multiple resolutions, encode them, and retrieve a `manifest.json` for adaptive streaming.

---

## 🚀 Features

### 📦 Backend (Spring Boot + PostgreSQL)

- Accept video uploads from users/admins
- Split and encode videos into multiple resolutions (1080p, 720p, etc.)
- Generate `manifest.json` for adaptive streaming protocols (e.g., HLS, MPEG-DASH)
- Store metadata in PostgreSQL
- Support API endpoints to:
  - Upload videos
  - Fetch video/chunk metadata
  - Download manifest.json
  - Delete videos or individual chunks
  - View download statistics
  - Invite and manage collaborators
- Packaged as:
  - A **microservice**
  - A **distributable SDK/JAR** for OTT platforms to use on their own infrastructure

### 🖥️ Frontend (React)

- Login and user authentication
- Video upload interface
- View uploaded videos and their chunk details
- Download the manifest.json anytime
- Delete videos or specific chunks
- View chunk statistics (e.g., number of downloads)
- Invite collaborators and manage shared access

---

## 📁 Tech Stack

| Layer          | Technology                    |
| -------------- | ----------------------------- |
| Backend        | Spring Boot                   |
| Database       | PostgreSQL                    |
| Frontend       | React                         |
| Video Engine   | FFmpeg                        |
| Packaging      | Docker, JAR (for SDK)         |
| Optional Tools | Kafka, Redis (future scaling) |

---

## 📂 Input/Output

- **Input:** Video file (uploaded via API or UI)
- **Output:** `manifest.json` describing video resolutions and segments

---

## 📦 Deployment Options

- Deploy as a standalone microservice
- Integrate into existing OTT infrastructure via provided SDK/JAR
- Cloud-native friendly: can be containerized using Docker and orchestrated with Kubernetes

---

## 📌 Use Case

Flixify serves as a general-purpose video storage and processing service for OTT platforms. Think of it as your **video backend-as-a-service**, similar to AWS S3 but purpose-built for video encoding and adaptive streaming.

---

## 🔜 Upcoming Features

- Integration with CDN for faster delivery
- WebSocket-based real-time encoding status
- Role-based access controls
- Advanced analytics dashboard
- Multi-language subtitle support

---

## 🧪 Development

Want to contribute or extend this service?  
Start with:

```bash
# Backend
cd backend
./mvnw spring-boot:run

# Frontend
cd frontend
npm install
npm start
```
