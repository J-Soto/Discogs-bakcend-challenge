
# Discogs Backend Challenge

Este proyecto implementa una API para buscar y comparar artistas musicales utilizando la API de Discogs. El sistema guarda los artistas y sus discografías en una base de datos, permitiendo realizar comparaciones detalladas entre ellos.

## Tabla de Contenidos
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Configuración de la Base de Datos](#configuración-de-la-base-de-datos)
- [Ejecución del Proyecto](#ejecución-del-proyecto)
- [API Endpoints](#api-endpoints)
- [Pruebas Unitarias](#pruebas-unitarias)
- [Análisis de Calidad del Código](#análisis-de-calidad-del-código)
- [Consideraciones Finales](#consideraciones-finales)

## Requisitos
Para ejecutar este proyecto, necesitarás lo siguiente:

- Java 17
- Maven 3.x
- PostgreSQL/MySQL u otro sistema de base de datos relacional compatible
- SonarQube (opcional, para el análisis de calidad del código)
- Una cuenta en Discogs para obtener una API key

## Instalación
### 1. Clonar el repositorio
Clona este repositorio desde GitHub en tu máquina local:

```bash
git clone https://github.com/tuusuario/discogs-backend-challenge.git
cd discogs-backend-challenge
```

### 2. Configuración del archivo `application.properties`
Configura el archivo `src/main/resources/application.properties` para conectar tu base de datos y la API de Discogs. Un ejemplo de la configuración sería:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/YOUR_SCHEMA
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

discogs.api.baseUrl=https://api.discogs.com
discogs.api.token=your_discogs_api_token
```

### 3. Configurar la Base de Datos
#### PostgreSQL
Crea una base de datos nueva:

```bash
psql -U postgres
CREATE DATABASE discogsdb;
```

Configura tu usuario y contraseña en el archivo `application.properties` (como se muestra arriba).

#### MySQL
Si prefieres usar MySQL, la configuración es similar, solo debes ajustar la URL de conexión:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/discogsdb
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
```

### 4. Construcción y Ejecución del Proyecto
Para compilar el proyecto, ejecuta el siguiente comando en la terminal:

```bash
mvn clean install
```

Para ejecutar la aplicación:

```bash
mvn spring-boot:run
```

La aplicación se ejecutará en `http://localhost:8080`.

## API Endpoints

### 1. Buscar Artista
- **URL:** `/artists/search?name={artistName}`
- **Método:** GET
- **Descripción:** Busca un artista por su nombre y guarda su información en la base de datos.

### 2. Comparar Artistas
- **URL:** `/artists/compare`
- **Método:** POST
- **Cuerpo:** Lista de nombres de artistas (JSON)

```json
["artistName1", "artistName2"]
```

- **Descripción:** Compara dos o más artistas por su discografía, géneros comunes y otros detalles.

## Pruebas Unitarias
Para ejecutar las pruebas unitarias:

```bash
mvn test
```

Los resultados de las pruebas unitarias se mostrarán en la terminal y puedes revisar los detalles en `target/surefire-reports`.

### Resultados de las Pruebas:

- **ArtistRepositoryTest:**
  - **Método de Prueba:** `testFindByName`
  - **Descripción:** Verifica que un artista puede ser encontrado por su nombre.
  - **Resultado Esperado:** Debe pasar si el artista se guarda y recupera correctamente.
  - **Resultado Final:** Aprobado.
  

- **MasterServiceTest:**
  - **Método de Prueba:** `testGetMasterByTitle`
  - **Descripción:** Verifica que un master puede ser encontrado por su título.
  - **Resultado Esperado:** Debe pasar si el master se recupera correctamente por título.
  - **Resultado Final:** Aprobado.

- **ReleaseServiceTest:**
  - **Método de Prueba:** `testSaveRelease`
  - **Descripción:** Verifica que un release puede ser guardado.
  - **Resultado Esperado:** Debe pasar si el release se guarda y recupera correctamente.  
  - **Resultado Final:** Aprobado.

## Consideraciones Finales
Este proyecto implementa un sistema robusto para buscar y comparar artistas musicales utilizando la API de Discogs. Se diseñaron pruebas para asegurar el correcto funcionamiento de los servicios, así como un análisis de calidad del código para mejorar la mantenibilidad y evitar problemas futuros.
