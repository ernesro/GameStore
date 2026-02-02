# Video Game Store API

Backend REST API desarrollada como proyecto de portfolio personal, simulando el backend de una tienda online de videojuegos. El objetivo es mostrar cómo construyo APIs **claras, mantenibles y seguras** siguiendo buenas prácticas.

---

## 📌 Descripción

El proyecto está construido con Spring Boot y PostgreSQL, aplicando principios de arquitectura en capas y seguridad con JWT. Está pensado para ser **realista** y profesional, no un tutorial ni un ejercicio académico.

---

## 🛠️ Stack tecnológico

* **Java 21 (LTS)**
* **Spring Boot 3.3.4**
* **Spring Security + JWT**
* **PostgreSQL**
* **Flyway** (migraciones de base de datos)
* **Docker & Docker Compose**
* **Maven**

> Uso Java 21 LTS, aunque no estoy usando features específicas de la versión todavía. Está preparado para evolucionar.

---

## Arquitectura y diseño

Arquitectura en capas para separar responsabilidades:

* **Controllers** → Endpoints REST
* **Services** → Lógica de negocio
* **Repositories** → Persistencia de datos
* **Security** → Autenticación y autorización con JWT

Se aplican principios **SOLID**, separation of concerns y uso de **DTOs** para no exponer entidades directamente.

---

## 🔐 Seguridad

* Registro y login de usuarios
* Tokens JWT con roles (`USER`, `ADMIN`) incluidos
* Protección de endpoints según rol

---

## Modelo de dominio

* **User** → Usuarios registrados
* **Product** → Videojuegos
* **Order** → Pedido realizado por un usuario
* **OrderItem** → Productos dentro de un pedido

Relaciones modeladas de forma realista para e-commerce.

---

## Base de datos y migraciones

* Gestionada con **Flyway**, lo que garantiza control de versiones y cambios reproducibles
* Evita modificaciones manuales y facilita trabajo en equipo

---

## Endpoints principales (ejemplo)

```http
POST /auth/register
POST /auth/login
GET /products
GET /products/{id}
POST /orders
GET /orders
GET /orders/{id}
```

---

Levanta la API y la base de datos PostgreSQL para desarrollo o pruebas.

---

## Testing

* Preparado para **tests unitarios**
* Tests de integración planificados como siguiente paso

---

## Roadmap

* La integración con Docker está planificada como mejora futura.
* Integración con **Kafka** para eventos (emails, notificaciones)
* Documentación con **Swagger / OpenAPI**
* Tests automatizados
* Refresh tokens y rate limiting
* Despliegue en servidor Linux

---

## 👨‍💻 Autor

Desarrollado por Ernestas Urbonas
Backend Developer | Java & Spring

* LinkedIn: www.linkedin.com/in/ernestas-urbonas-020702220

---

## 📄 Licencia

Proyecto desarrollado con fines educativos y de portfolio profesional.

