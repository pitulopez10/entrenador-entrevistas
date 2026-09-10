# Entrenador de Entrevistas

Sistema administrativo desarrollado en Java para la gestión de una plataforma de entrevistas laborales.

La aplicación funciona mediante consola y permite al administrador gestionar postulantes, empresas, ofertas laborales y postulaciones.

El sistema utiliza Java, JDBC y MariaDB para la persistencia de los datos.

## Requisitos

Para ejecutar el proyecto es necesario contar con:

- Java JDK 26.
- Maven.
- MariaDB.
- XAMPP, MariaDB Server o equivalente.
- IntelliJ IDEA u otro IDE compatible con proyectos Maven.

## Tecnologías utilizadas

- Java.
- JDBC.
- Maven.
- MariaDB.
- MariaDB Java Client 3.5.10.

## Configuración de la base de datos

La aplicación utiliza una base de datos llamada:

```text
entrenador_entrevista
```
La conexión a la base de datos se encuentra configurada en:

ConexionDB.java

Configuración actual:

Host: localhost
Puerto: 3306
Base de datos: entrenador_entrevista
Usuario: root
Contraseña: vacía

La URL JDBC utilizada es:

jdbc:mariadb://localhost:3306/entrenador_entrevista

Antes de ejecutar la aplicación se debe iniciar el servicio de MariaDB.

Luego se debe ejecutar el archivo:

schema.sql

Este archivo permite crear desde cero la base de datos y sus tablas.

Configuración del proyecto
Descargar o clonar el proyecto.
Abrir el proyecto en IntelliJ IDEA.
Verificar que Java JDK 26 esté configurado correctamente.
Esperar a que Maven descargue las dependencias definidas en pom.xml.
Iniciar MariaDB.
Ejecutar el archivo schema.sql.
Verificar que los datos de conexión de ConexionDB.java coincidan con la configuración local.
Ejecución

La clase principal del sistema es:

org.example.Main

Para ejecutar el proyecto:

Abrir:
src/main/java/org/example/Main.java
Ejecutar el método:
public static void main(String[] args)
El sistema mostrará el menú principal en la consola.
Seleccionar una opción ingresando el número correspondiente.
Seguir las instrucciones mostradas en pantalla.
Compilación con Maven

Desde una terminal ubicada en la carpeta raíz del proyecto se puede compilar utilizando:

mvn clean compile

También se puede construir el proyecto utilizando:

mvn clean package

Main y Menu

Se encargan de la interacción con el usuario y del flujo general de la aplicación.

Modelos

Representan las entidades del sistema.

DAO

Las clases DAO se encargan del acceso, consulta y modificación de los datos almacenados en MariaDB mediante JDBC.

Las sentencias SQL se encuentran dentro de las clases DAO.

ConexionDB

Se encarga de establecer la conexión entre la aplicación Java y MariaDB.

CrudDAO

Interfaz genérica utilizada para definir las operaciones CRUD básicas de los DAO.

Persistencia

Toda la información administrada por el sistema se almacena en MariaDB.

Los datos permanecen almacenados aunque el programa sea cerrado y ejecutado nuevamente.

Consideraciones

MariaDB debe estar iniciado antes de ejecutar la aplicación.
La base de datos debe ser creada mediante schema.sql.
Si se utiliza otro usuario, contraseña, puerto o nombre de base de datos, se deben modificar los datos correspondientes en ConexionDB.java.
Maven debe descargar correctamente las dependencias indicadas en pom.xml.