# Women Empowerment Backend

## Overview
The Women Empowerment Backend is a Spring Boot-based REST API that serves as the backend for the Women Empowerment platform. This platform aims to support and empower women by providing financial literacy, skill development, and personal safety features.

## Features
- **User Authentication & Authorization** (JWT-based security)
- **Role-based Access Control** (Admin, User)
- **Transaction Management** (Track income and expenses)
- **Exception Handling** (Global error handling)
- **RESTful APIs** (Structured and scalable endpoints)

## Tech Stack
- **Backend**: Java, Spring Boot
- **Database**: MySQL
- **Security**: Spring Security, JWT

## Project Structure
```
WE-Backend/
├── src/
│   ├── main/
│   │   ├── java/com/we/
│   │   │   ├── config/             # Configuration classes
│   │   │   ├── controller/         # REST Controllers
│   │   │   ├── dto/                # Data Transfer Objects
│   │   │   ├── exception/          # Custom exceptions & handlers
│   │   │   ├── model/              # Entity classes
│   │   │   ├── repository/         # JPA Repositories
│   │   │   ├── security/           # Security & JWT configuration
│   │   │   ├── service/            # Business logic
│   │   │   ├── WeBackendApplication.java  # Main Application
│   ├── test/                       # Unit and Integration tests
├── pom.xml                          # Project dependencies
├── .gitignore                        # Ignored files
├── README.md                         # Project documentation
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
   git clone https://github.com/VishalNarsinh/Women-Empowerment.git
   cd WE-Backend
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
Contributions are welcome! Feel free to fork this repository and submit pull requests.


## Contact
- **Developer**: Vishal Narsinh
- **GitHub**: [VishalNarsinh](https://github.com/VishalNarsinh)
- **Email**: [vishalnarsinh@gmail.com](mailto:vishalnarsinh@gmail.com)


