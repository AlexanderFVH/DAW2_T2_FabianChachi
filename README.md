# Hospital Vida Salud - Sistema Distribuido de Microservicios (T2)

## Descripción del Proyecto
Solución tecnológica basada en microservicios desarrollada para modernizar la plataforma administrativa del Hospital Vida Salud, desacoplando el monolito original y garantizando alta disponibilidad, escalabilidad y procesamiento no bloqueante[cite: 48, 49, 52].

## Tecnologías Utilizadas
* Java 21 y Spring Boot[cite: 48].
* Spring WebFlux y Spring Data R2DBC (Programación y persistencia reactiva)[cite: 48, 54].
* Spring Cloud Gateway (Punto de entrada / API Gateway)[cite: 49, 53].
* Eureka Discovery Server (Registro y descubrimiento dinámico de servicios)[cite: 49, 53].
* RabbitMQ (Mensajería asíncrona orientada a eventos)[cite: 49, 53].
* MySQL y phpMyAdmin (Base de datos relacional fragmentada por dominio)[cite: 49, 54].
* GitHub Actions (Integración Continua automatizada)[cite: 48, 55].

---

## Arquitectura y Puertos de los Microservicios

| Componente | Nombre del Servicio / Módulo | Puerto | Responsabilidad Principal |
| :--- | :--- | :--- | :--- |
| **Infraestructura** | `eureka-server` | `8761` | Registro y descubrimiento de microservicios[cite: 49, 53]. |
| **Infraestructura** | `api-gateway` | `9095` | Enrutamiento dinámico de peticiones externas[cite: 49, 53]. |
| **Dominio** | `microservicio-pacientes` | `8082` | Gestión, consulta y validación de duplicados de pacientes[cite: 49, 50]. |
| **Dominio** | `microservicio-citas` | `8081` | Programación de citas y validación síncrona vía WebClient[cite: 49, 50, 53]. |
| **Asíncrono** | `microservicio-notificaciones` | `8083` | Consumo de eventos a través de colas RabbitMQ[cite: 52, 53]. |

---

## Flujos de Comunicación

1. **Comunicación Síncrona (HTTP / WebClient):** 
   * Al registrar una cita en `/api/citas`, el microservicio de Citas consulta de forma síncrona al microservicio de Pacientes para verificar la existencia del paciente antes de confirmar la persistencia[cite: 53].
2. **Comunicación Asíncrona (RabbitMQ):** 
   * Al persistir exitosamente una cita, se publica un evento (`cita.confirmada`) hacia un `TopicExchange`, el cual es consumido de forma independiente por el módulo de notificaciones y auditoría sin bloquear el hilo principal[cite: 52, 53, 54].

---

## Instrucciones de Ejecución

### 1. Requisitos Previos
* Tener instalado Java 21[cite: 48].
* Tener activo MySQL (XAMPP) y crear la base de datos `bd_hospital` ejecutando el script SQL provisto en el proyecto[cite: 48, 54].
* Servidor RabbitMQ activo (o utilizar el manejo de excepciones preventivo incluido)[cite: 48, 53].

### 2. Orden de Inicio de los Servicios
Para que la arquitectura funcione correctamente, levanta los proyectos en el siguiente orden desde tu IDE (Eclipse/IntelliJ):
1. `eureka-server` (Puerto 8761)[cite: 49, 53]
2. `api-gateway` (Puerto 9095)[cite: 49, 53]
3. `microservicio-pacientes` (Puerto 8082)[cite: 49, 50]
4. `microservicio-citas` (Puerto 8081)[cite: 49, 50]
5. `microservicio-notificaciones` (Puerto 8083)[cite: 52]

---

## Pruebas Funcionales (Postman)

* **Registrar Paciente:** `POST http://localhost:9095/api/pacientes` (`201 Created`)[cite: 50, 59]
* **Consultar Paciente:** `GET http://localhost:9095/api/pacientes/{id}` (`200 OK`)[cite: 50, 59]
* **Validar Duplicado:** `POST http://localhost:9095/api/pacientes` con un DNI existente (`400 Bad Request`)[cite: 50, 59]
* **Registrar Cita:** `POST http://localhost:9095/api/citas` (Valida síncronamente al paciente y dispara el evento en RabbitMQ)[cite: 50, 53, 59]

---

## Integración Continua (CI con GitHub Actions)
El repositorio cuenta con un pipeline automatizado configurado en `.github/workflows/ci.yml` que se ejecuta en cada `push` o `pull_request` hacia la rama principal, encargándose de descargar Java 21, compilar los módulos y verificar la correcta ejecución de las pruebas automatizadas[cite: 55, 56, 57].
