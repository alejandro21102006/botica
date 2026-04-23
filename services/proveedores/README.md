# Microservicio proveedores

Servicio base de botica para el dominio **proveedores**.

## Endpoints base

- GET /api/v1/proveedores/instancia
- GET /actuator/health

## Ejecucion dev

- docker compose -f docker-compose-dev.yml up -d
- mvn spring-boot:run