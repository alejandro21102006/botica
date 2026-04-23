# Microservicio Inventario

Base recomendada para el microservicio `inventario` en el proyecto botica.

## Objetivo

Gestionar stock por inventario y movimientos de inventario (ingreso/salida/ajuste).

## Integración esperada

- Config Server: `inventario-dev.yml`, `inventario-prod.yml`
- Registry (Eureka): registro por nombre `inventario`
- Gateway: rutas `/api/v1/inventario/**`

## Siguiente paso sugerido

1. Crear proyecto Spring Boot con `spring.application.name=inventario`.
2. Definir entidad mínima `Inventario`:
   - `id`
   - `idInventario`
   - `stockActual`
   - `stockMinimo`
3. Exponer endpoint de prueba:
   - `GET /api/v1/inventario/instancia`
4. Agregar CRUD:
   - `POST /api/v1/inventario`
   - `GET /api/v1/inventario/{idInventario}`
   - `PUT /api/v1/inventario/{idInventario}/ajustar`
