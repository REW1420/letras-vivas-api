# 📚 Letras Vivas API – Spring Boot CRUD Application

This is a simple yet well-structured Spring Boot application that demonstrates the use of a layered architecture (Controller, Service, Repository, Model) to implement full CRUD (Create, Read, Update, Delete) operations for managing books.

The application is designed with clean code practices in mind and provides a RESTful API that allows users to create new books, retrieve existing ones, update their information, and delete them from the system. It uses an in-memory H2 database for ease of testing and development, and includes basic validation to ensure only valid data is accepted.

Dependency injection is handled using `@Autowired` and constructor-based injection, and the application follows industry best practices to ensure maintainability and scalability.

To enhance API usability, the application includes integrated Swagger documentation, allowing developers to easily explore and test endpoints through a web UI.

Overall, this project serves as a solid starting point for building Java REST APIs with Spring Boot.

## 📌 Features

- Layered architecture: `Controller`, `Service`, `Repository`, `Model`
- CRUD operations for a `Book` entity
- In-memory H2 database for development
- Input validation with annotations
- Dependency injection with `@Autowired` and constructors
- HTTP status code management
- Basic Swagger/OpenAPI documentation

## 📦 Technologies

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Spring Validation (`jakarta.validation`)
- Swagger/OpenAPI (via `springdoc-openapi`)

---

## 🚀 How to Run

1. **Clone the repository**

````bash
git clone https://github.com/REW1420/letras-vivas-api
cd letras-vivas-api

2. **Build the project**

```bash

./mvnw clean install

- Or Grandle

./gradlew build

3. **Run the application**

```bash

./mvnw spring-boot:run

4. **Access the application**

- Swagger UI: http://localhost:8081/swagger-ui.html

- H2 Console: http://localhost:8081/h2-console

- Base URL: http://localhost:8081/

##🔧 API Endpoints

| Method | Endpoint      | Description             |
| ------ | ------------- | ----------------------- |
| GET    | `/books`      | Get all books           |
| GET    | `/books/{id}` | Get a book by ID        |
| POST   | `/books`      | Create a new book       |
| PUT    | `/books/{id}` | Update an existing book |
| DELETE | `/books/{id}` | Delete a book by ID     |

## 🧪 Running Tests

```bash

./mvnw test

## 📂 Project Structure

src/
├── controller/      # REST Controllers
├── service/         # Business logic
├── repository/      # JPA Repositories
├── model/           # Entities
└── BookApplication  # Main Spring Boot entry point

## 🧑‍💻 Author
Created by William Ernesto Ramos Valladares
````
