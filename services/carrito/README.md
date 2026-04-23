# Microservicio carrito

Servicio base de botica para el dominio **carrito**.

## Endpoints base

- GET /api/v1/carrito/instancia
- GET /actuator/health

## Ejecucion dev

- docker compose -f docker-compose-dev.yml up -d
- mvn spring-boot:run