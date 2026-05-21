# Flight Booking System

Flight Booking System is a Spring Boot backend application for managing users, flights, bookings, uploaded files, and operational reports. The project is built as a REST API with a layered architecture, PostgreSQL persistence, JWT-based security, Swagger documentation, file handling, asynchronous processing, logging, Docker deployment, health checks, and automated tests.

The application follows the common controller-service-repository structure. Controllers expose HTTP endpoints, services contain business logic, repositories communicate with the database through Spring Data JPA, and DTO classes are used to separate API request/response models from persistence entities.

## Technology Stack

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JWT with `jjwt`
- PostgreSQL
- Bean Validation
- SpringDoc Swagger UI
- Spring Boot Actuator
- Docker and Docker Compose
- Maven
- JUnit and Mockito

## Main Features

The project implements a backend for a flight booking domain. Users can register, authenticate, receive JWT tokens, view flights, create bookings, upload and download files, and call asynchronous reporting endpoints. Admin users can create flights and create other admin accounts.

The API uses DTO classes for requests and responses. Validation annotations are applied to incoming data such as usernames, emails, passwords, flight fields, booking IDs, and uploaded files. Invalid requests are handled by a global exception handler that returns structured JSON error responses.

Database access is implemented with PostgreSQL and Spring Data JPA. The application stores users, flights, bookings, and uploaded file metadata in database tables, while uploaded file content is stored on disk in a configurable upload directory.

## Security

Authentication is implemented with Spring Security and JWT tokens. A user logs in through `/api/auth/login` and receives a token. That token must be sent in the `Authorization` header for secured endpoints:

```text
Authorization: Bearer <jwt-token>
```

The security configuration uses stateless sessions, disables CSRF for REST API usage, and applies role-based authorization rules. Public endpoints include registration, login, Swagger, API docs, and health checks. Regular authenticated users can read flights and create bookings. Admin users can create flights and create admin accounts.

Important security classes include:

- `SecurityConfig`
- `JwtAuthenticationFilter`
- `JwtUtil`
- `JwtAuthenticationEntryPoint`
- `JwtAccessDeniedHandler`
- `CustomUserDetailsService`

## API Overview

Authentication and user management are available through:

```text
POST /api/users
POST /api/users/admin
POST /api/auth/login
```

Flight endpoints support flight creation, pagination, sorting, searching, and filtering:

```text
POST /api/flights
GET  /api/flights?page=0&size=5&sortBy=id
GET  /api/flights/search?departureCity=Almaty&page=0&size=5
GET  /api/flights/filter?minPrice=100&maxPrice=700&page=0&size=5
```

Bookings are created through:

```text
POST /api/bookings
```

File upload and download are implemented through:

```text
POST /api/files/upload
GET  /api/files
GET  /api/files/{id}/download
```

Asynchronous report endpoints return `CompletableFuture` responses:

```text
GET /api/reports/flights
GET /api/reports/bookings
GET /api/reports/system
```

## Pagination, Sorting, Searching, and Filtering

The flight API demonstrates advanced query features. `/api/flights` supports pagination and sorting with `page`, `size`, and `sortBy` query parameters. `/api/flights/search` searches flights by departure city. `/api/flights/filter` filters flights by price range.

These endpoints use Spring Data `Page`, `Pageable`, and repository query methods, so responses include useful page metadata such as total elements, total pages, current page number, and page size.

## File Upload and Download

The application supports multipart file upload. Uploaded files are saved to the configured storage directory, and metadata such as original file name, stored file name, content type, file size, and upload time is saved in PostgreSQL.

The storage directory can be configured with:

```properties
file.storage-dir=uploads
```

When running with Docker Compose, uploads are stored in a Docker volume so files are not lost when the container restarts.

## Asynchronous Processing

Asynchronous processing is enabled with `@EnableAsync`. The project defines a custom task executor and uses `@Async` service methods returning `CompletableFuture`.

The async report service generates separate reports for flights, bookings, and system statistics. These endpoints show how longer-running or background-style operations can be separated from the main request thread.

## Logging

The project logs HTTP requests, important business actions, and errors. A request logging filter records the HTTP method, URI, response status, and duration. Services log key actions such as successful login, user creation, flight creation, booking creation, and file upload. The global exception handler logs validation errors, authentication failures, missing resources, file storage problems, and unexpected exceptions.

Logs are written to:

```text
logs/flight-booking-system.log
```

The `logs/` directory is ignored by Git because logs are runtime output, not source code.

## Swagger Documentation

Swagger UI is enabled with SpringDoc. It provides interactive documentation for the API and supports JWT bearer authentication through the Authorize button.

After starting the application, Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

The OpenAPI JSON is available at:

```text
http://localhost:8080/v3/api-docs
```

## Health Checks

Spring Boot Actuator is included for application monitoring. Health, readiness, and liveness probes are enabled.

The main health endpoint is:

```text
GET /actuator/health
```

Docker and Docker Compose use this endpoint to check whether the application is running correctly.

## Docker Deployment

The project includes a multistage Dockerfile. The first stage builds the application with Maven and JDK 17. The second stage runs the generated JAR on a smaller JRE image.

Build and run the full system with PostgreSQL:

```bash
docker compose up --build
```

The Docker Compose file starts:

- PostgreSQL database
- Spring Boot application
- Persistent database volume
- Persistent upload volume
- Persistent log volume
- Health checks for both services

The API will be available at:

```text
http://localhost:8080
```

## Local Development

To run the application locally, make sure PostgreSQL is running and the database settings in `application.properties` match your environment.

Run tests:

```bash
./mvnw test
```

On Windows:

```powershell
.\mvnw.cmd test
```

Start the application:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

## Configuration

Important settings can be provided through environment variables:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
DB_MAX_POOL_SIZE
DB_MIN_IDLE
JWT_SECRET
JWT_EXPIRATION
FILE_STORAGE_DIR
LOG_FILE_NAME
JPA_SHOW_SQL
```

The Docker Compose file already provides these values for containerized execution.

## Testing

The project includes automated tests for the application context, JWT generation and validation, authentication, user registration, duplicate user handling, and asynchronous reports.

The current test suite verifies that security tokens are generated and validated correctly, invalid login attempts are rejected, users are created with encoded passwords and the correct role, duplicate usernames are blocked, and async reports return expected statistics.

Run the test suite with:

```bash
./mvnw test
```

## Example Authentication Flow

Register a user:

```http
POST /api/users
Content-Type: application/json

{
  "username": "arthur2",
  "email": "arthur2@example.com",
  "password": "123456"
}
```

Login:

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "arthur2",
  "password": "123456"
}
```

Use the returned token:

```http
GET /api/flights
Authorization: Bearer <jwt-token>
```

## Example Flight Creation

Flight creation requires an admin token:

```http
POST /api/flights
Authorization: Bearer <admin-jwt-token>
Content-Type: application/json

{
  "flightNumber": "KC101",
  "departureCity": "Almaty",
  "arrivalCity": "Tokyo",
  "departureTime": "2026-06-10T10:30:00",
  "arrivalTime": "2026-06-10T18:00:00",
  "availableSeats": 150,
  "price": 499.99
}
```

## Project Structure

```text
src/main/java/com/arthurtokarev/flightbookingsystem
+-- config
+-- controller
+-- dto
+-- entity
+-- exception
+-- mapper
+-- payload
+-- repository
+-- security
+-- service
```

This structure keeps the application organized and separates HTTP handling, business logic, persistence, security, DTO mapping, and error handling.

## Notes

The current implementation focuses on the core flight booking workflow, authentication, authorization, file handling, async reports, logging, documentation, Docker deployment, health checks, and tests. Runtime folders such as `logs/`, `uploads/`, and `target/` are ignored by Git because they are generated by the application or build process.
