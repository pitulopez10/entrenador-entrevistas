package org.example;

import java.sql.*;

public class PasswordResetDAO {
    private static final String CREATE = """
            CREATE TABLE IF NOT EXISTS password_reset_token (
                token VARCHAR(100) PRIMARY KEY,
                email VARCHAR(150) NOT NULL,
                rol VARCHAR(20) NOT NULL,
                expires_at TIMESTAMP NOT NULL,
                usado BOOLEAN NOT NULL DEFAULT FALSE
            )
            """;

    public PasswordResetDAO() throws SQLException {
        try (Connection c = ConexionDB.obtenerConexion(); Statement s = c.createStatement()) {
            s.executeUpdate(CREATE);
        }
    }

    public void guardar(String token, String email, String rol, Timestamp vence) throws SQLException {
        String sql = "INSERT INTO password_reset_token(token,email,rol,expires_at) VALUES (?,?,?,?)";
        try (Connection c = ConexionDB.obtenerConexion(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, token); p.setString(2, email); p.setString(3, rol); p.setTimestamp(4, vence); p.executeUpdate();
        }
    }

    public String obtenerRolValido(String token) throws SQLException {
        String sql = "SELECT rol FROM password_reset_token WHERE token=? AND usado=FALSE AND expires_at > CURRENT_TIMESTAMP";
        try (Connection c = ConexionDB.obtenerConexion(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, token); try (ResultSet r = p.executeQuery()) { return r.next() ? r.getString(1) : null; }
        }
    }

    public String obtenerEmail(String token) throws SQLException {
        String sql = "SELECT email FROM password_reset_token WHERE token=? AND usado=FALSE AND expires_at > CURRENT_TIMESTAMP";
        try (Connection c = ConexionDB.obtenerConexion(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, token); try (ResultSet r = p.executeQuery()) { return r.next() ? r.getString(1) : null; }
        }
    }

    public void marcarUsado(String token) throws SQLException {
        try (Connection c = ConexionDB.obtenerConexion(); PreparedStatement p = c.prepareStatement("UPDATE password_reset_token SET usado=TRUE WHERE token=?")) {
            p.setString(1, token); p.executeUpdate();
        }
    }
}
