package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministradorDAO {

    public boolean validarCredenciales(String usuario, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM administrador WHERE usuario = ? AND password = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}