# Microservicio compras

Servicio base de botica para el dominio **compras**.

## Endpoints base

- GET /api/v1/compras/instancia
- GET /actuator/health

## Ejecucion dev

- docker compose -f docker-compose-dev.yml up -d
- mvn spring-boot:run