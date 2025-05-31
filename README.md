# Plataforma de Gestión de Inventarios Distribuidos para Retail

## Descripción del Proyecto

Esta plataforma está diseñada para que empresas minoristas puedan gestionar de manera eficiente y en tiempo real su inventario distribuido en múltiples ubicaciones, como tiendas, almacenes y centros de distribución. El objetivo es optimizar el control de stock, mejorar la eficiencia operativa y facilitar la toma de decisiones estratégicas mediante una solución tecnológica moderna y escalable.

## Lógica del Negocio

La plataforma permite administrar inventarios distribuidos, sincronizando información entre diferentes puntos físicos y garantizando que los datos estén actualizados y disponibles para los distintos usuarios con roles específicos. Se enfoca en:

- Control en tiempo real del stock por ubicación.
- Gestión de productos y sus variantes.
- Operaciones de recepción, transferencia y ajuste de inventario.
- Alertas automáticas para reposición y control de niveles mínimos.
- Reportes analíticos para optimizar la rotación y reducir costos.
- Seguridad y control de acceso mediante autenticación robusta.

## Principales Funcionalidades

1. **Gestión de Inventario**

   - Control detallado de stock por tienda o almacén.
   - Seguimiento de productos y variantes (tallas, colores, etc.).
   - Alertas automáticas para niveles mínimos y máximos.

2. **Gestión de Ubicaciones**

   - Administración de múltiples puntos físicos.
   - Transferencias internas entre ubicaciones con control y trazabilidad.

3. **Operaciones de Inventario**

   - Registro de entradas y salidas de mercancía.
   - Ajustes y conteos cíclicos para mantener la precisión.
   - Gestión de devoluciones.

4. **Seguridad y Autenticación**

   - Registro y autenticación de usuarios con contraseñas cifradas usando BCrypt.
   - Uso de JWT para autenticación stateless y segura.
   - Control de acceso basado en roles.

5. **API REST y Frontend React**

   - Servicios RESTful para todas las operaciones CRUD.
   - Frontend en React que consume estos servicios con métodos GET, POST, PUT y DELETE.
   - Persistencia en base de datos MySQL.

6. **Reportes y Análisis**
   - Dashboards en tiempo real con indicadores clave.
   - Reportes de rotación, tendencias y niveles de inventario.

## Stack Tecnológico

- **Backend:**

  - Java 21 (LTS)
  - Spring Boot 3.5.0
  - Spring MVC, Spring Security, Spring Data JPA
  - Lombok para simplificar código
  - JWT para autenticación stateless
  - BCryptPasswordEncoder para cifrado de contraseñas
  - MySQL 8.x como base de datos relacional
  - Maven como gestor de dependencias

- **Frontend:**

  - React 18+
  - Axios para consumo de API REST
  - React Router para navegación
  - Estilos con Material-UI o Tailwind CSS (a definir)

- **Herramientas:**
  - Git y GitHub para control de versiones
  - Postman para pruebas de API
  - IDEs: IntelliJ IDEA para backend, VS Code para frontend

## Estructura Inicial del Proyecto Backend

```
inventory-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/retail/inventory/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── InventoryApplication.java
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
└── README.md
```

## Primeros Pasos: Implementación del Servicio de Autenticación

### 1. Dependencias Maven

Agrega las siguientes dependencias en tu `pom.xml` para Spring Boot 3.5.0, seguridad, JWT y Lombok:

```xml
<dependencies>
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Boot Starter Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Spring Boot Starter Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <!-- MySQL Connector -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>

    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

### 2. Configuración de la Base de Datos

En `src/main/resources/application.properties` configura la conexión a MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventorydb?useSSL=false&serverTimezone=UTC
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 3. Modelo Usuario

```java
package com.retail.inventory.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;
}
```

### 4. Repositorio Usuario

```java
package com.retail.inventory.repository;

import com.retail.inventory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
```

### 5. Servicio de Usuario y Seguridad

Aquí implementaremos la carga de usuario para Spring Security y el uso de BCrypt para cifrar contraseñas.

### 6. Configuración de Seguridad y JWT

Implementaremos la configuración para usar JWT y proteger los endpoints.

---

Esto es el punto de partida. ¿Quieres que continúe con el código detallado para la configuración de seguridad, generación y validación de JWT, y el controlador de autenticación?  
También puedo ayudarte a documentar cada paso para que quede claro y puedas seguir aprendiendo.
