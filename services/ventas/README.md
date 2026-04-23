# Microservicio Ventas

Base recomendada para el microservicio `ventas` en el proyecto botica.

## Objetivo

Registrar ventas y detalle de venta, y coordinar con inventario para descontar stock.

## Integración esperada

- Config Server: `ventas-dev.yml`, `ventas-prod.yml`
- Registry (Eureka): registro por nombre `ventas`
- Gateway: rutas `/api/v1/ventas/**`

## Siguiente paso sugerido

1. Crear proyecto Spring Boot con `spring.application.name=ventas`.
2. Definir entidades mínimas:
   - `Venta` (cabecera)
   - `VentaDetalle`
3. Exponer endpoint de prueba:
   - `GET /api/v1/ventas/instancia`
4. Exponer endpoint principal:
   - `POST /api/v1/ventas`
5. Integrar con `inventario` (Feign) para validar y descontar stock.
