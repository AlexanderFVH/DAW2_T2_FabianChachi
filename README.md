# Hospital Vida Salud - Sistema Distribuido de Microservicios (T2)

## Descripción del Proyecto
Solución tecnológica basada en microservicios desarrollada para modernizar la plataforma administrativa del Hospital Vida Salud, desacoplando el monolito original y garantizando alta disponibilidad, escalabilidad y procesamiento no bloqueante.

## Tecnologías Utilizadas
* Java 21 y Spring Boot.
* Spring WebFlux y Spring Data R2DBC (Programación y persistencia reactiva).
* Spring Cloud Gateway (Punto de entrada / API Gateway).
* Eureka Discovery Server (Registro y descubrimiento dinámico de servicios).
* RabbitMQ (Mensajería asíncrona orientada a eventos).
* MySQL y phpMyAdmin (Base de datos relacional fragmentada por dominio).
* GitHub Actions (Integración Continua automatizada).

---

## Arquitectura y Puertos de los Microservicios

| Componente | Nombre del Servicio / Módulo | Puerto | Responsabilidad Principal |
| :--- | :--- | :--- | :--- |
| **Infraestructura** | `eureka-server` | `8761` | Registro y descubrimiento de microservicios. |
| **Infraestructura** | `api-gateway` | `9095` | Enrutamiento dinámico de peticiones externas. |
| **Dominio** | `microservicio-pacientes` | `8082` | Gestión, consulta y validación de duplicados de pacientes. |
| **Dominio** | `microservicio-citas` | `8081` | Programación de citas y validación síncrona vía WebClient. |
| **Asíncrono** | `microservicio-notificaciones` | `8083` | Consumo de eventos a través de colas RabbitMQ. |

---

## Flujos de Comunicación

1. **Comunicación Síncrona (HTTP / WebClient):** 
   * Al registrar una cita en `/api/citas`, el microservicio de Citas consulta de forma síncrona al microservicio de Pacientes para verificar la existencia del paciente antes de confirmar la persistencia.
2. **Comunicación Asíncrona (RabbitMQ):** 
   * Al persistir exitosamente una cita, se publica un evento (`cita.confirmada`) hacia un `TopicExchange`, el cual es consumido de forma independiente por el módulo de notificaciones y auditoría sin bloquear el hilo principal.

---

## Instrucciones de Ejecución

### 1. Requisitos Previos
* Tener instalado Java 21.
* Tener activo MySQL (XAMPP) y crear la base de datos `bd_hospital` ejecutando el script SQL provisto en el proyecto.
* Servidor RabbitMQ activo (o utilizar el manejo de excepciones preventivo incluido).

### 2. Orden de Inicio de los Servicios
Para que la arquitectura funcione correctamente, levanta los proyectos en el siguiente orden desde tu IDE (Eclipse/IntelliJ):
1. `eureka-server` (Puerto 8761)
2. `api-gateway` (Puerto 9095)
3. `microservicio-pacientes` (Puerto 8082)
4. `microservicio-citas` (Puerto 8081)
5. `microservicio-notificaciones` (Puerto 8083)

---

## Pruebas Funcionales (Postman)

* **Registrar Paciente:** `POST http://localhost:9095/api/pacientes` (`201 Created`)
* **Consultar Paciente:** `GET http://localhost:9095/api/pacientes/{id}` (`200 OK`)
* **Validar Duplicado:** `POST http://localhost:9095/api/pacientes` con un DNI existente (`400 Bad Request`)
* **Registrar Cita:** `POST http://localhost:9095/api/citas` (Valida síncronamente al paciente y dispara el evento en RabbitMQ)

---

## Integración Continua (CI con GitHub Actions)
El repositorio cuenta con un pipeline automatizado configurado en `.github/workflows/ci.yml` que se ejecuta en cada `push` o `pull_request` hacia la rama principal, encargándose de descargar Java 21, compilar los módulos y verificar la correcta ejecución de las pruebas automatizadas.
