# Kotlin Keycloak Authentication Service

## Description

This project is an authentication service based on Keycloak, implemented using Kotlin, Spring Boot, reactive programming, and coroutines. The service provides APIs for user management and authentication within the system.

## Features

- User registration
- User authentication
- User and role management
- Integration with Keycloak via Keycloak Admin REST API

## Technologies

- Kotlin
- Spring Boot (WebFlux)
- Coroutines
- Keycloak
- Docker


## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/MikalaiSamtsevich/kotlin-keycloak-auth-service.git
   cd kotlin-keycloak-auth-service
   
2. Launch the necessary infrastructure services (e.g., Kafka, Keycloak) using Docker Compose:

    ```bash
    cd docker
    docker-compose -f compose-tools.yml up
    ```
3. Go back to the root directory and run
    ```bash
    ./gradlew bootRun
    ```
### Running with Docker Compose

```bash
cd docker
docker-compose -f compose-app.yml build
docker-compose -f compose-app.yml up
```

## Access the Swagger Documentation:
http://localhost:8092/keycloak-auth-service-docs/webjars/swagger-ui/index.html

## Keycloak Endpoint

- **Keycloak Administration UI**: Accessible at `http://localhost:9082` (Login: `admin/admin`).