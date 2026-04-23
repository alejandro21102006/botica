# Microservicio pagos

Servicio base de botica para el dominio **pagos**.

## Endpoints base

- GET /api/v1/pagos/instancia
- GET /actuator/health

## Ejecucion dev

- docker compose -f docker-compose-dev.yml up -d
- mvn spring-boot:run