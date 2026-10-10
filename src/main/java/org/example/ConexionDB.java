package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL =
            "jdbc:mariadb://localhost:3306/entrenador_entrevista";

    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    private ConexionDB() {
    }

    public static Connection obtenerConexion() throws SQLException {

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se pudo cargar el driver de MariaDB", e);
        }

        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}