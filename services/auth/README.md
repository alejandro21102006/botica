# Microservicio auth

Servicio base de botica para el dominio **auth**.

## Endpoints base

- GET /api/v1/auth/instancia
- GET /actuator/health

## Ejecucion dev

- docker compose -f docker-compose-dev.yml up -d
- mvn spring-boot:run