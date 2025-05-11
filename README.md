## 🎬 Flixify

Flixify is a modular **microservice designed for video upload, transcoding, chunking, and adaptive streaming support**. It powers modern OTT platforms by processing uploaded videos into multiple resolutions and generating manifest files compatible with streaming protocols like HLS and MPEG-DASH.

### 🚀 Features

- 📥 Accept video uploads from users or administrators.
- 🧩 Automatically split and encode uploaded videos into multiple resolutions (e.g., 1080p, 720p, 480p,..).
- 📄 Generate manifest json for adaptive bitrate streaming.
- 🗄️ Store and manage video/chunk metadata using PostgreSQL.
- 💬 Tracking the status of video processing.
- 📊 Provide access to download statistics and monitoring.

---

### 🛠️ Tech Stack

- **Backend:** Spring Boot
- **Database:** PostgreSQL
- **File Storage:** Local disk
- **Async Processing**: `@Async`, `@TransactionalEventListener`

---

### 📚 Important Resources

Here are some essential documents and references to help you get started with Flixify:

- 🔗 [**REST API Documentation**](https://github.com/dharmarajrdr/Flixify/blob/master/REST_API.md)  
  Comprehensive list of all endpoints including request/response formats.

- 🧩 [**Database Schema Design**](https://dbdiagram.io/d/Flixify-6708a04e97a66db9a39e2394)  
  Entity-relationship model and table structures used in Flixify.

- 🚀 [**Project Walkthrough**](https://www.youtube.com/watch?v=0aX2g1v4k8A)  
  Step-by-step explanation of the architecture, upload-to-streaming flow, async processing, and more.

---

### 📦 Getting Started

#### Prerequisites

- Java 17+
- Maven
- PostgreSQL
- FFmpeg installed

#### Running Locally

```bash
# Clone the repository
git clone https://github.com/dharmarajrdr/Flixify.git
cd Flixify/backend

# Configure application.properties
# Set DB credentials and local storage paths

# Build and run the application
mvn clean install
mvn spring-boot:run
```

---

### 📌 Notes

- Async operations like chunking and encoding do not block HTTP response.
- Designed to be **extensible** — supports custom splitter rules, distributed storage, CDN integration, and DRM module add-ons.

---

### 🧑‍💻 Contributing

```java
throw new LookingForContributorsException("We need frontend developers to build an intuitive UI for Flixify!");
```

#### How to Contribute

1. **Fork** the repository
2. **Create a feature branch**
3. **Commit** your changes with clear messages
4. **Push** to your fork and open a **pull request**

Whether you're fixing bugs, improving the UI/UX, or adding new features — your contributions are appreciated!

Feel free to reach out by opening a GitHub issue if you have any questions or ideas.

---

### 🤝 Acknowledgments

Special thanks to all contributors and supporters. Let's make collaboration better together!

---

❤️ Thank you for checking out Flixify! We hope you find it helpful and engaging.
