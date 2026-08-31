package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO implements CrudDAO<Administrador, String> {

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

    @Override
    public void agregar(Administrador admin) throws SQLException {
        String sql = """
            INSERT INTO administrador (usuario, password)
            VALUES (?, ?)
            """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, admin.getUsuario());
            stmt.setString(2, admin.getPassword());

            stmt.executeUpdate();
        }
    }

    @Override
    public Administrador buscarPorId(String usuario) throws SQLException {
        String sql = "SELECT * FROM administrador WHERE usuario = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, usuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Administrador admin = new Administrador();
                    admin.setUsuario(rs.getString("usuario"));
                    admin.setPassword(rs.getString("password"));
                    return admin;
                }
            }
        }
        return null;
    }

    @Override
    public List<Administrador> listar() throws SQLException {
        List<Administrador> administradores = new ArrayList<>();
        String sql = "SELECT * FROM administrador";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Administrador admin = new Administrador();
                admin.setUsuario(rs.getString("usuario"));
                admin.setPassword(rs.getString("password"));
                administradores.add(admin);
            }
        }
        return administradores;
    }

    @Override
    public void modificar(Administrador admin) throws SQLException {
        String sql = """
            UPDATE administrador
            SET password = ?
            WHERE usuario = ?
            """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, admin.getPassword());
            stmt.setString(2, admin.getUsuario());

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(String usuario) throws SQLException {
        String sql = "DELETE FROM administrador WHERE usuario = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.executeUpdate();
        }
    }
}