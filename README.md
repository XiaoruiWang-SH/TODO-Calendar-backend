# TODO-Calendar-backend

A robust backend service for the <a href="https://todocalendar.live">TODO Calendar</a> application built with Spring Boot and modern security features.

## Related Projects

- Frontend Repository: [TODO-Calendar](https://github.com/XiaoruiWang-SH/TODO-Calendar)
- Official Website: [https://todocalendar.live](https://todocalendar.live)

## Tech Stack

- Java 17
- Spring Boot 3.2.3
- Spring Security with JWT & OAuth2
- MyBatis
- MySQL 8.0
- Gradle
- Docker & Docker Compose

## Features

- User Authentication & Authorization
  - JWT-based authentication
  - OAuth2 support (Google & GitHub)
  - Role-based access control
- Secure session management
- RESTful API endpoints
- Database integration with MySQL
- Docker containerization
- Environment-specific configurations (dev, test, prod)
- HTTPS support with Nginx reverse proxy

## Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- MySQL 8.0 (if running without Docker)
- Gradle (optional, wrapper included)

## Project Structure

```
TODO-Calendar-backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           ├── Tools/
│       │           ├── model/
│       │           └── service/
│       └── resources/
│           ├── application.yml
│           ├── application-dev.yml
│           ├── application-test.yml
│           └── application-prod.yml
├── docker-compose-dev.yml
├── docker-compose-prod.yml
├── Dockerfile
├── build.gradle
└── run.sh
```

## Setup & Installation

1. Clone the repository:
   ```bash
   git clone [repository-url]
   cd TODO-Calendar-backend
   ```

2. Configure environment variables:
   - Create `.env.dev` for development
   - Create `.env.prod` for production
   Example environment variables:
   ```
   APP_PORT=8080
   MYSQL_HOST=localhost
   MYSQL_PORT=3306
   MYSQL_DATABASE=your_db
   MYSQL_USER=your_user
   MYSQL_PASSWORD=your_password
   JWT_SECRET=your_jwt_secret
   GOOGLE_CLIENT_ID=your_google_client_id
   GOOGLE_CLIENT_SECRET=your_google_client_secret
   GITHUB_CLIENT_ID=your_github_client_id
   GITHUB_CLIENT_SECRET=your_github_client_secret
   FRONTEND_URL=https://todocalendar.live
   ```

3. Run the application:
   ```bash
   # Development environment
   ./run.sh dev

   # Production environment
   ./run.sh prod
   ```

## Development

### Building the Project

```bash
./gradlew build
```

### Running Tests

```bash
./gradlew test
```

### Debug Mode

Debug port is exposed at 5005 in development environment.

## Deployment

The application is currently deployed and running:

- Frontend: [https://todocalendar.live](https://todocalendar.live)
- Backend API: [https://api.todocalendar.live](https://api.todocalendar.live)

### Deployment Methods

The application can be deployed using Docker Compose:

1. Production deployment:
   ```bash
   docker compose -f docker-compose-prod.yml --env-file .env.prod up -d
   ```

2. Development deployment:
   ```bash
   docker compose -f docker-compose-dev.yml --env-file .env.dev up -d
   ```

### Resource Limits

- Application container:
  - CPU: 0.5 cores (limit), 0.2 cores (reserved)
  - Memory: 512MB (limit), 256MB (reserved)

- MySQL container:
  - CPU: 0.5 cores (limit), 0.2 cores (reserved)
  - Memory: 1GB (limit), 256MB (reserved)

## Security

- HTTPS enforced in production
- JWT token-based authentication
- OAuth2 integration for social login
- Secure cookie handling
- CORS configuration
- Role-based access control

## API Documentation

Base URL: `https://api.todocalendar.live`

### Authentication Endpoints

- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `GET /api/oauth2/authorize` - OAuth2 authorization
- `GET /api/oauth2/callback/*` - OAuth2 callback

### Protected Endpoints

- `GET /api/users/**` - User management (Admin only)

## License

Copyright © 2025 by Xiaorui Wang. All Rights Reserved.