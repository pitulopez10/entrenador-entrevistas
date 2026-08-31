package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO implements CrudDAO<Empresa, String> {

    @Override
    public void agregar(Empresa empresa) throws SQLException {

        String sql = """
                INSERT INTO empresa
                (rut, nombre, mail, password, fechaRegistro, telefono,
                 descripcion, logo, sitioWeb, bloqueado)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, empresa.getRut());
            stmt.setString(2, empresa.getNombre());
            stmt.setString(3, empresa.getMail());
            stmt.setString(4, empresa.getPassword());
            stmt.setDate(5, java.sql.Date.valueOf(empresa.getFechaRegistro()));
            stmt.setInt(6, empresa.getTelefono());
            stmt.setString(7, empresa.getDescripcion());
            stmt.setString(8, empresa.getLogo());
            stmt.setString(9, empresa.getSitioWeb());
            stmt.setBoolean(10, empresa.isBloqueado());

            stmt.executeUpdate();
        }
    }

    public Empresa buscarPorId(String rut) throws SQLException {

        String sql = """
                SELECT *
                FROM empresa
                WHERE rut = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, rut);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Empresa empresa = new Empresa();

                    empresa.setRut(rs.getString("rut"));
                    empresa.setNombre(rs.getString("nombre"));
                    empresa.setMail(rs.getString("mail"));
                    empresa.setPassword(rs.getString("password"));

                    if (rs.getDate("fechaRegistro") != null) {
                        empresa.setFechaRegistro(
                                rs.getDate("fechaRegistro").toLocalDate()
                        );
                    }

                    empresa.setTelefono(rs.getInt("telefono"));
                    empresa.setDescripcion(rs.getString("descripcion"));
                    empresa.setLogo(rs.getString("logo"));
                    empresa.setSitioWeb(rs.getString("sitioWeb"));
                    empresa.setBloqueado(rs.getBoolean("bloqueado"));

                    return empresa;
                }
            }
        }

        return null;
    }

    @Override
    public List<Empresa> listar() throws SQLException {

        List<Empresa> empresas = new ArrayList<>();

        String sql = """
                SELECT *
                FROM empresa
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Empresa empresa = new Empresa();

                empresa.setRut(rs.getString("rut"));
                empresa.setNombre(rs.getString("nombre"));
                empresa.setMail(rs.getString("mail"));
                empresa.setPassword(rs.getString("password"));

                if (rs.getDate("fechaRegistro") != null) {
                    empresa.setFechaRegistro(
                            rs.getDate("fechaRegistro").toLocalDate()
                    );
                }

                empresa.setTelefono(rs.getInt("telefono"));
                empresa.setDescripcion(rs.getString("descripcion"));
                empresa.setLogo(rs.getString("logo"));
                empresa.setSitioWeb(rs.getString("sitioWeb"));
                empresa.setBloqueado(rs.getBoolean("bloqueado"));

                empresas.add(empresa);
            }
        }

        return empresas;
    }

    @Override
    public void modificar(Empresa empresa) throws SQLException {

        String sql = """
                UPDATE empresa
                SET nombre = ?,
                    mail = ?,
                    password = ?,
                    fechaRegistro = ?,
                    telefono = ?,
                    descripcion = ?,
                    logo = ?,
                    sitioWeb = ?,
                    bloqueado = ?
                WHERE rut = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, empresa.getNombre());
            stmt.setString(2, empresa.getMail());
            stmt.setString(3, empresa.getPassword());
            stmt.setDate(4, java.sql.Date.valueOf(empresa.getFechaRegistro()));
            stmt.setInt(5, empresa.getTelefono());
            stmt.setString(6, empresa.getDescripcion());
            stmt.setString(7, empresa.getLogo());
            stmt.setString(8, empresa.getSitioWeb());
            stmt.setBoolean(9, empresa.isBloqueado());
            stmt.setString(10, empresa.getRut());

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(String rut) throws SQLException {

        String sql = """
                DELETE FROM empresa
                WHERE rut = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, rut);

            stmt.executeUpdate();
        }
    }

    public void bloquear(String rut) throws SQLException {

        String sql = """
                UPDATE empresa
                SET bloqueado = TRUE
                WHERE rut = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, rut);

            stmt.executeUpdate();
        }
    }

    public void desbloquear(String rut) throws SQLException {

        String sql = """
                UPDATE empresa
                SET bloqueado = FALSE
                WHERE rut = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, rut);

            stmt.executeUpdate();
        }
    }
}