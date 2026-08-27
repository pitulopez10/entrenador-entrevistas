package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        try (Connection conexion = ConexionDB.obtenerConexion()) {

            System.out.println("Conexión exitosa a la base de datos.");

        } catch (SQLException e) {

            System.out.println("Error al conectar con la base de datos.");
            System.out.println(e.getMessage());
        }
    }
}