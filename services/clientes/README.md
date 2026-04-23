# Microservicio clientes

Servicio base de botica para el dominio **clientes**.

## Endpoints base

- GET /api/v1/clientes/instancia
- GET /actuator/health

## Ejecucion dev

- docker compose -f docker-compose-dev.yml up -d
- mvn spring-boot:run