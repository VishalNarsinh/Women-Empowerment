# Learning Management System Backend

## Overview
The Learning Management System (LMS) Backend is a Spring Boot-based REST API that powers an educational platform. It is designed to manage users, courses, enrollments, and learning progress, enabling to deliver and track learning effectively.

## Features
- **User Authentication & Authorization** (JWT-based security)
- **Role-based Access Control** (Admin, Tutor, Learner)
- **Course & Enrollment Management**
- **Progress Tracking** (Lessons, quizzes, and completions)
- **Exception Handling** (Global error responses)
- **RESTful APIs** (Well-structured)

## Tech Stack
- **Backend**: Java 21, Spring Boot
- **Database**: MySQL
- **Security**: Spring Security, JWT
- **Build Tool**: Maven

## Project Structure
```
LMS-Backend/
├── src/
│   ├── main/
│   │   ├── java/com/lms/
│   │   │   ├── config/             # Configuration classes (Security, JWT, etc.)
│   │   │   ├── controller/         # REST Controllers
│   │   │   ├── dto/                # Data Transfer Objects
│   │   │   ├── exception/          # Custom exceptions & handlers
│   │   │   ├── mapper/             # Mapper classes
│   │   │   ├── model/              # Entity classes
│   │   │   ├── repository/         # JPA Repositories
│   │   │   ├── security/           # Spring Security & JWT setup
│   │   │   ├── service/            # Business logic
│   │   │   ├── startup/            # Initial data loading or startup configs
│   │   │   └── LMSBackendApplication.java  # Main class
│   └── test/                       # Unit and integration tests
├── pom.xml                         # Project dependencies
├── .gitignore                      # Git ignored files
├── README.md                       # Project documentation
```

## Installation

### Prerequisites
- Java 17+
- Maven
- MySQL
- Git

### Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/VishalNarsinh/Learning-Management-System.git
   cd Learning-Management-System
   ```

2. Configure MySQL database:
   - Update `application.properties` with your database credentials.

3. Build the project:
   ```sh
   mvn clean install
   ```

4. Run the application:
   ```sh
   mvn spring-boot:run
   ```

## Contributing
Contributions are welcome! Feel free to fork this repository and submit pull requests for improvements or features.

## Contact
- **Developer**: Vishal Narsinh  
- **GitHub**: [VishalNarsinh](https://github.com/VishalNarsinh)  
- **Email**: [vishalnarsinh@gmail.com](mailto:vishalnarsinh@gmail.com)


## application.properties (Basic Configuration)

Here is a sample of what your `application.properties` file might look like:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/lmsdb
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT Configuration
jwt.secret=your_jwt_secret_key
jwt.expirationMs=3600000

# Logging
logging.level.org.springframework=INFO
```

> ⚠️ **Note:** Always store sensitive credentials (like DB passwords or JWT secrets) securely using environment variables or Spring Boot config servers for production.
