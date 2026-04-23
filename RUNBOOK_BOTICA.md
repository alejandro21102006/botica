# Runbook de Integracion Botica

## 1) Levantar infraestructura

Desde `infra`:

```bash
docker compose up -d
```

Para desarrollo local (alternativa):

1. `infra/config-server` -> `mvn spring-boot:run`
2. `infra/registry-server` -> `mvn spring-boot:run`
3. `infra/gateway` -> `mvn spring-boot:run`

## 2) Levantar bases de datos dev por microservicio

Ejecutar en cada carpeta de servicio:

- `docker compose -f docker-compose-dev.yml up -d`

Servicios: `catalogo`, `producto`, `inventario`, `ventas`, `clientes`, `carrito`, `pagos`, `proveedores`, `compras`, `auth`.

## 3) Levantar microservicios

En cada servicio:

```bash
mvn clean spring-boot:run
```

## 4) Smoke tests

- `GET http://localhost:7071/catalogo/dev`
- `GET http://localhost:7081`
- `GET http://localhost:7091/actuator/health`

Instancia por gateway (enviar header `X-ROLE: ADMIN` para rutas protegidas):

- `GET /api/v1/catalogo/instancia`
- `GET /api/v1/producto/instancia`
- `GET /api/v1/inventario/instancia`
- `GET /api/v1/ventas/instancia`
- `GET /api/v1/clientes/instancia`
- `GET /api/v1/carrito/instancia`
- `GET /api/v1/pagos/instancia`
- `GET /api/v1/proveedores/instancia`
- `GET /api/v1/compras/instancia`
- `GET /api/v1/auth/instancia`

## 5) Flujo E2E MVP

1. Crear cliente: `POST /api/v1/clientes`
2. Crear carrito/item: `POST /api/v1/carrito`
3. Checkout: `POST /api/v1/carrito/{id}/checkout`
4. Confirmar pago: `POST /api/v1/pagos/{ventaId}/confirmar`
5. Registrar compra: `POST /api/v1/compras/{id}/ingresar-stock`

## 6) Autenticacion base (MVP)

- `POST /api/v1/auth/login`
- `POST /api/v1/auth/refresh`
- `GET /api/v1/auth/usuarios/me`

## 7) Swagger DEV (directo)

- `catalogo`: `http://localhost:8081/swagger-ui/index.html`
- `producto`: `http://localhost:9091/swagger-ui/index.html`
- `inventario`: `http://localhost:9101/swagger-ui/index.html`
- `ventas`: `http://localhost:9111/swagger-ui/index.html`
- `clientes`: `http://localhost:9121/swagger-ui/index.html`
- `carrito`: `http://localhost:9131/swagger-ui/index.html`
- `pagos`: `http://localhost:9141/swagger-ui/index.html`
- `proveedores`: `http://localhost:9151/swagger-ui/index.html`
- `compras`: `http://localhost:9161/swagger-ui/index.html`
- `auth`: `http://localhost:9171/swagger-ui/index.html`
