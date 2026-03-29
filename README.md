# 🎮 Video Game Store API
 
Backend REST API desarrollada como proyecto de portfolio personal, simulando el backend de una tienda online de videojuegos. El objetivo es mostrar cómo construyo APIs **claras, mantenibles y seguras** siguiendo buenas prácticas.
 
---
 
## 📌 Descripción
 
El proyecto está construido con Spring Boot y PostgreSQL, aplicando principios de arquitectura en capas y seguridad con JWT. Está pensado para ser **realista** y profesional, no un tutorial ni un ejercicio académico.
 
---

## 🚀 Quick Start

1. Clona el repositorio
2. Copia `.env.example` a `.env` y rellena tus valores
3. Ejecuta `docker-compose up --build`
4. Accede a Swagger en `http://localhost:8080/swagger-ui/index.html`

---
 
## 🛠️ Stack tecnológico
 
- **Java 21 (LTS)**
- **Spring Boot 3.3.4**
- **Spring Security + JWT**
- **PostgreSQL**
- **Flyway** — migraciones de base de datos
- **Maven**
- **JUnit 5 + Mockito** — testing unitario
- **Swagger / OpenAPI** — documentación de endpoints
 
---
 
## 🏗️ Arquitectura y diseño
 
Arquitectura en capas para separar responsabilidades:
 
- **Controllers** → Endpoints REST
- **Services** → Lógica de negocio
- **Repositories** → Persistencia de datos
- **Mappers** → Conversión entre entidades y DTOs
- **Security** → Autenticación y autorización con JWT
 
Se aplican principios **SOLID**, separation of concerns y uso de **DTOs** para no exponer entidades directamente.
 
---
 
## 🔐 Seguridad
 
- Registro y login de usuarios
- Tokens JWT con roles (`USER`, `ADMIN`) incluidos en el payload
- Filtro JWT personalizado con `OncePerRequestFilter`
- Protección de endpoints por autenticación
- Manejo global de excepciones con `@RestControllerAdvice`
 
---
 
## 📦 Modelo de dominio
 
- **User** → Usuarios registrados con roles
- **Product** → Videojuegos con categoría, condición y tags
- **Order** → Pedido realizado por un usuario
- **OrderItem** → Productos dentro de un pedido con precio y cantidad
- **Stock** → Inventario de productos por almacén
- **Warehouse** → Almacenes físicos
 
Relaciones modeladas de forma realista para e-commerce.
 
---
 
## 🗄️ Base de datos y migraciones
 
- Gestionada con **Flyway**, garantizando control de versiones de esquema
- Evita modificaciones manuales y facilita trabajo en equipo
- Compatible con PostgreSQL
 
---
 
## 📡 Endpoints principales
 
```http
POST   /auth/login
POST   /api/users
 
GET    /api/products
GET    /api/products/{id}
POST   /api/products
PUT    /api/products
DELETE /api/products/{id}
 
POST   /api/orders
GET    /api/orders?userId={id}
PUT    /api/orders
PUT    /api/orders/update-status?orderId={id}&newOrderStatus={status}
 
GET    /api/stocks/search?warehouseId={id}&productId={id}
POST   /api/stocks
PUT    /api/stocks/updateQuantity
 
GET    /api/warehouses
GET    /api/warehouses/{id}
POST   /api/warehouses
PUT    /api/warehouses/{id}
DELETE /api/warehouses/{id}
```
 
La documentación completa está disponible en Swagger UI una vez arrancado el proyecto:
```
http://localhost:8080/swagger-ui/index.html
```
 
---
 
## 🧪 Testing
 
- **32 tests unitarios** con JUnit 5 y Mockito
- Cobertura de servicios: `OrderService`, `ProductService`
- Cobertura de seguridad: `JwtUtil`
- Casos positivos y negativos cubiertos en todos los métodos
- Patrón AAA (Arrange, Act, Assert) aplicado consistentemente
 
---
 
## 🚀 Roadmap

- [x] Descuento de stock al crear pedidos
- [x] Máquina de estados para transiciones de pedidos
- [x] Autorización por roles con `@PreAuthorize`
- [ ] Tests de integración con MockMvc
- [ ] Integración con **Kafka** para eventos asíncronos (emails, notificaciones)
- [ ] Refresh tokens y rate limiting
- [x] Docker & Docker Compose para levantar el entorno fácilmente
- [ ] Despliegue en servidor Linux
 
---
 
## 👨‍💻 Autor
 
Desarrollado por **Ernestas Urbonas**
Backend Developer | Java & Spring Boot
 
- LinkedIn: [linkedin.com/in/ernestas-urbonas-020702220](https://www.linkedin.com/in/ernestas-urbonas-020702220)
