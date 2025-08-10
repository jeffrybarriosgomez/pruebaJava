# Prueba Java III

## Descripción
Prueba Java III es una aplicación desarrollada con **Spring Boot** que implementa una solución backend para para gestionar albumes con cancipones. Utiliza una base de datos en memoria H2 para pruebas y desarrollo, y cuenta con funcionalidades de seguridad, validación de datos y persistencia mediante JPA.

## Tecnologías utilizadas
- **Java**: Versión 17
- **Spring Boot**: Versión 3.5.4
- **Dependencias principales**:
    - `spring-boot-starter-web`: Para construir APIs RESTful.
    - `spring-boot-starter-data-jpa`: Para la persistencia de datos con Hibernate.
    - `spring-boot-starter-security`: Para la autenticación y autorización.
    - `spring-boot-starter-validation`: Para la validación de datos.
    - `h2`: Base de datos en memoria para desarrollo y pruebas.
    - `lombok`: Para reducir el código repetitivo mediante anotaciones.
    - `spring-boot-starter-test` y `spring-security-test`: Para pruebas unitarias e integración.

## Requisitos previos
- **Java 17** o superior instalado.
- **Maven** instalado para gestionar dependencias y construir el proyecto.
- Opcional: Un IDE como IntelliJ IDEA, para facilitar el desarrollo.

## Instalación
1. Clona el repositorio:
   ```bash
   git clone https://github.com/jeffrybarriosgomez/pruebaJava.git
   ```
2. Navega al directorio del proyecto:
   ```bash
   cd pruebaJava
   ```
3. Instala las dependencias usando Maven:
   ```bash
   mvn clean install
   ```

## Ejecución
Para ejecutar la aplicación en modo desarrollo:
```bash
  mvn spring-boot:run
```
La aplicación estará disponible en `http://localhost:8080`.

## Configuración
- **Base de datos**: La aplicación utiliza una base de datos H2 en memoria por defecto. Puedes acceder a la consola de H2 en `http://localhost:8080/h2-console` (asegúrate de configurar las credenciales en `application.properties` si es necesario).
- **application.properties**: Configura las propiedades de la aplicación (por ejemplo, puerto, credenciales de la base de datos, etc.) en el archivo `src/main/resources/application.properties`.

Ejemplo de configuración básica:
```properties
spring.application.name=pruebaJava

spring.datasource.url=jdbc:h2:mem:playlists;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE 
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## Seguridad
La aplicación utiliza autenticación básica HTTP con Spring Security. Los usuarios disponibles son:
- **Usuario**: `admin` con contraseña `admin` (rol: `ADMIN`)
- **Usuario**: `user` con contraseña `user` (rol: `USER`)

Los endpoints bajo `/lists/**` requieren autenticación y están accesibles para usuarios con roles `USER` o `ADMIN`. Los endpoints bajo `/admin/**` son exclusivos para el rol `ADMIN`.

Para autenticarte, incluye un encabezado `Authorization` con las credenciales en formato `Basic <Base64(username:password)>`. Ejemplo con cURL:
```bash
   curl -u user:user http://localhost:8080/lists
```

## Endpoints de la API
La API permite gestionar listas de reproducción mediante los siguientes endpoints. Todos los endpoints están bajo la ruta base `/lists` y requieren autenticación.

| Método | Endpoint                | Descripción                              | Roles Permitidos | Entrada                              | Respuesta                          |
|--------|-------------------------|------------------------------------------|------------------|--------------------------------------|------------------------------------|
| POST   | `/lists`               | Crea una nueva lista de reproducción.    | USER, ADMIN      | JSON con los datos de la playlist    | 201 Created con la playlist creada |
| GET    | `/lists`               | Lista todas las listas de reproducción. | USER, ADMIN      | Ninguna                              | 200 OK con lista de playlists      |
| GET    | `/lists/{listName}`    | Obtiene una playlist por su nombre.      | USER, ADMIN      | Nombre de la playlist (path)         | 200 OK con la playlist o 404       |
| DELETE | `/lists/{listName}`    | Elimina una playlist por su nombre.      | USER, ADMIN      | Nombre de la playlist (path)         | 204 No Content o 404               |

### Ejemplos de uso
1. **Crear una playlist**:
   ```bash
   curl -u user:user -X POST http://localhost:8080/lists \
   -H "Content-Type: application/json" \
   -d '{"nombre": "Mi Playlist", "descripcion": "Lista de canciones favoritas"}'
   ```
   Respuesta:
   ```json
   {
       "nombre": "Mi Playlist",
       "descripcion": "Lista de canciones favoritas"
   }
   ```

2. **Listar todas las playlists**:
   ```bash
   curl -u user:user http://localhost:8080/lists
   ```
   Respuesta:
   ```json
   [
       {
           "nombre": "Mi Playlist",
           "descripcion": "Lista de canciones favoritas"
       }
   ]
   ```

3. **Obtener una playlist por nombre**:
   ```bash
   curl -u user:user http://localhost:8080/lists/Mi%20Playlist
   ```
   Respuesta:
   ```json
   {
       "nombre": "Mi Playlist",
       "descripcion": "Lista de canciones favoritas"
   }
   ```

4. **Eliminar una playlist**:
   ```bash
   curl -u user:user -X DELETE http://localhost:8080/lists/Mi%20Playlist
   ```

## Estructura del proyecto
```
pruebaJava/
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── quipux
│   │   │           └── pruebaJava
│   │   │               ├── config
│   │   │               │   └── SecurityConfig.java
│   │   │               ├── controller
│   │   │               │   └── PlaylistController.java
│   │   │               ├── entity
│   │   │               │   ├── PlayList.java
│   │   │               │   └── Song.java
│   │   │               ├── PruebaJavaApplication.java
│   │   │               ├── repository
│   │   │               │   └── PlaylistRepository.java
│   │   │               └── service
│   │   │                   ├── PlaylistServiceImpl.java
│   │   │                   └── PlaylistService.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── com
│               └── quipux
│                   └── pruebaJava
│                       ├── controller
│                       │   └── PlaylistControllerTest.java
│                       └── PruebaJavaApplicationTests.java

├── client-app //cliente rest
├── pom.xml
└── README.md
```

## Pruebas
Ejecuta las pruebas unitarias e integrales con:

```bash
    mvn test
```
