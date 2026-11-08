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
                (
                    rut,
                    nombre,
                    mail,
                    password,
                    fechaRegistro,
                    telefono,
                    descripcion,
                    logo,
                    logoPublicId,
                    sitioWeb,
                    bloqueado
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, empresa.getRut());
            stmt.setString(2, empresa.getNombre());
            stmt.setString(3, empresa.getMail());
            stmt.setString(4, empresa.getPassword());

            if (empresa.getFechaRegistro() != null) {
                stmt.setDate(
                        5,
                        java.sql.Date.valueOf(empresa.getFechaRegistro())
                );
            } else {
                stmt.setNull(
                        5,
                        java.sql.Types.DATE
                );
            }

            stmt.setInt(
                    6,
                    empresa.getTelefono()
            );

            stmt.setString(
                    7,
                    empresa.getDescripcion()
            );

            // CLOUDINARY - LOGO
            stmt.setString(
                    8,
                    empresa.getLogo()
            );

            stmt.setString(
                    9,
                    empresa.getLogoPublicId()
            );

            stmt.setString(
                    10,
                    empresa.getSitioWeb()
            );

            stmt.setBoolean(
                    11,
                    empresa.isBloqueado()
            );

            stmt.executeUpdate();
        }
    }


    @Override
    public Empresa buscarPorId(
            String rut
    ) throws SQLException {

        String sql = """
                SELECT *
                FROM empresa
                WHERE rut = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    rut
            );

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return crearEmpresaDesdeResultSet(rs);
                }
            }
        }

        return null;
    }


    @Override
    public List<Empresa> listar() throws SQLException {

        List<Empresa> empresas =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM empresa
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                empresas.add(
                        crearEmpresaDesdeResultSet(rs)
                );
            }
        }

        return empresas;
    }


    @Override
    public void modificar(
            Empresa empresa
    ) throws SQLException {

        String sql = """
                UPDATE empresa
                SET nombre = ?,
                    mail = ?,
                    password = ?,
                    fechaRegistro = ?,
                    telefono = ?,
                    descripcion = ?,
                    logo = ?,
                    logoPublicId = ?,
                    sitioWeb = ?,
                    bloqueado = ?
                WHERE rut = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    empresa.getNombre()
            );

            stmt.setString(
                    2,
                    empresa.getMail()
            );

            stmt.setString(
                    3,
                    empresa.getPassword()
            );

            if (empresa.getFechaRegistro() != null) {

                stmt.setDate(
                        4,
                        java.sql.Date.valueOf(
                                empresa.getFechaRegistro()
                        )
                );

            } else {

                stmt.setNull(
                        4,
                        java.sql.Types.DATE
                );
            }

            stmt.setInt(
                    5,
                    empresa.getTelefono()
            );

            stmt.setString(
                    6,
                    empresa.getDescripcion()
            );

            // CLOUDINARY - LOGO
            stmt.setString(
                    7,
                    empresa.getLogo()
            );

            stmt.setString(
                    8,
                    empresa.getLogoPublicId()
            );

            stmt.setString(
                    9,
                    empresa.getSitioWeb()
            );

            stmt.setBoolean(
                    10,
                    empresa.isBloqueado()
            );

            stmt.setString(
                    11,
                    empresa.getRut()
            );

            stmt.executeUpdate();
        }
    }


    @Override
    public void eliminar(
            String rut
    ) throws SQLException {

        String sql = """
                DELETE FROM empresa
                WHERE rut = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    rut
            );

            stmt.executeUpdate();
        }
    }


    public void bloquear(
            String rut
    ) throws SQLException {

        String sql = """
                UPDATE empresa
                SET bloqueado = TRUE
                WHERE rut = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    rut
            );

            stmt.executeUpdate();
        }
    }


    public void desbloquear(
            String rut
    ) throws SQLException {

        String sql = """
                UPDATE empresa
                SET bloqueado = FALSE
                WHERE rut = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    rut
            );

            stmt.executeUpdate();
        }
    }


    public Empresa iniciarSesion(
            String mail,
            String password
    ) throws SQLException {

        String sql = """
                SELECT rut
                FROM empresa
                WHERE mail = ?
                  AND password = ?
                """;

        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();

                PreparedStatement stmt =
                        conexion.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    mail
            );

            stmt.setString(
                    2,
                    password
            );

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    String rut =
                            rs.getString("rut");

                    return buscarPorId(rut);
                }
            }
        }

        return null;
    }


    public Empresa buscarPorMail(
            String mail
    ) throws SQLException {

        String sql = """
                SELECT *
                FROM empresa
                WHERE mail = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    mail
            );

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return crearEmpresaDesdeResultSet(rs);
                }
            }
        }

        return null;
    }


    /*
     * ==================================================
     * MÉTODO AUXILIAR
     * ==================================================
     */

    private Empresa crearEmpresaDesdeResultSet(
            ResultSet rs
    ) throws SQLException {

        Empresa empresa =
                new Empresa();

        empresa.setRut(
                rs.getString("rut")
        );

        empresa.setNombre(
                rs.getString("nombre")
        );

        empresa.setMail(
                rs.getString("mail")
        );

        empresa.setPassword(
                rs.getString("password")
        );

        if (rs.getDate("fechaRegistro") != null) {

            empresa.setFechaRegistro(
                    rs.getDate("fechaRegistro")
                            .toLocalDate()
            );
        }

        empresa.setTelefono(
                rs.getInt("telefono")
        );

        empresa.setDescripcion(
                rs.getString("descripcion")
        );


        // ==============================
        // CLOUDINARY - LOGO
        // ==============================

        empresa.setLogo(
                rs.getString("logo")
        );

        empresa.setLogoPublicId(
                rs.getString("logoPublicId")
        );


        empresa.setSitioWeb(
                rs.getString("sitioWeb")
        );

        empresa.setBloqueado(
                rs.getBoolean("bloqueado")
        );

        return empresa;
    }
    public List<Empresa> buscarPorNombre(
            String nombre
    ) throws SQLException {

        List<Empresa> empresas = new ArrayList<>();

        String sql = """
                SELECT *
                FROM empresa
                WHERE LOWER(nombre) LIKE ?
                ORDER BY nombre ASC
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    "%" + nombre.toLowerCase().trim() + "%"
            );

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    empresas.add(
                            crearEmpresaDesdeResultSet(rs)
                    );
                }
            }
        }

        return empresas;
    }
    public List<Empresa> listarPorNombrePaginadas(String nombreFiltro, int limit, int offset) throws SQLException {
        List<Empresa> empresas = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
            SELECT rut, nombre, mail, password, fechaRegistro, telefono, descripcion, logo, sitioWeb, bloqueado, logoPublicId
            FROM empresa
            WHERE 1 = 1
            """);

        if (nombreFiltro != null && !nombreFiltro.isBlank()) {
            sql.append(" AND LOWER(nombre) LIKE LOWER(?)");
        }
        sql.append(" ORDER BY nombre LIMIT ? OFFSET ?");

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql.toString())) {

            int param = 1;
            if (nombreFiltro != null && !nombreFiltro.isBlank()) {
                stmt.setString(param++, "%" + nombreFiltro.trim() + "%");
            }
            stmt.setInt(param++, limit);
            stmt.setInt(param++, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Empresa e = new Empresa();
                    e.setRut(rs.getString("rut"));
                    e.setNombre(rs.getString("nombre"));
                    e.setMail(rs.getString("mail"));
                    e.setPassword(rs.getString("password"));

                    if (rs.getDate("fechaRegistro") != null) {
                        e.setFechaRegistro(rs.getDate("fechaRegistro").toLocalDate());
                    }

                    e.setTelefono(rs.getInt("telefono"));
                    e.setDescripcion(rs.getString("descripcion"));
                    e.setLogo(rs.getString("logo"));
                    e.setSitioWeb(rs.getString("sitioWeb"));
                    e.setBloqueado(rs.getBoolean("bloqueado"));
                    e.setLogoPublicId(rs.getString("logoPublicId"));

                    empresas.add(e);
                }
            }
        }
        return empresas;
    }

    public int contarPorNombre(String nombreFiltro) throws SQLException {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*) 
            FROM empresa
            WHERE 1 = 1
            """);

        if (nombreFiltro != null && !nombreFiltro.isBlank()) {
            sql.append(" AND LOWER(nombre) LIKE LOWER(?)");
        }

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql.toString())) {

            if (nombreFiltro != null && !nombreFiltro.isBlank()) {
                stmt.setString(1, "%" + nombreFiltro.trim() + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    public List<Empresa> listarPorFiltrosPaginadas(String nombre, String estado, int limit, int offset)
            throws SQLException {

        List<Empresa> empresas = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
                SELECT * FROM empresa
                WHERE 1 = 1
                """);

        if (nombre != null && !nombre.isBlank()) {
            sql.append(" AND LOWER(nombre) LIKE LOWER(?)");
        }
        if (estado != null && !estado.isBlank()) {
            if (estado.equalsIgnoreCase("activo")) {
                sql.append(" AND (bloqueado = false OR bloqueado IS NULL)");
            } else if (estado.equalsIgnoreCase("bloqueado")) {
                sql.append(" AND bloqueado = true");
            }
        }
        sql.append(" ORDER BY rut LIMIT ? OFFSET ?");

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql.toString())) {

            int parametro = 1;
            if (nombre != null && !nombre.isBlank()) {
                stmt.setString(parametro++, "%" + nombre.trim() + "%");
            }
            stmt.setInt(parametro++, limit);
            stmt.setInt(parametro, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Empresa e = new Empresa();
                    e.setRut(rs.getString("rut"));
                    e.setNombre(rs.getString("nombre"));
                    e.setMail(rs.getString("mail"));
                    e.setTelefono(rs.getInt("telefono"));
                    e.setSitioWeb(rs.getString("sitioWeb"));
                    e.setLogo(rs.getString("logo"));
                    e.setBloqueado(rs.getBoolean("bloqueado"));
                    empresas.add(e);
                }
            }
        }
        return empresas;
    }

    public int contarPorFiltros(String nombre, String estado) throws SQLException {
        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(*) FROM empresa
                WHERE 1 = 1
                """);

        if (nombre != null && !nombre.isBlank()) {
            sql.append(" AND LOWER(nombre) LIKE LOWER(?)");
        }
        if (estado != null && !estado.isBlank()) {
            if (estado.equalsIgnoreCase("activo")) {
                sql.append(" AND (bloqueado = false OR bloqueado IS NULL)");
            } else if (estado.equalsIgnoreCase("bloqueado")) {
                sql.append(" AND bloqueado = true");
            }
        }

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql.toString())) {

            int parametro = 1;
            if (nombre != null && !nombre.isBlank()) {
                stmt.setString(parametro, "%" + nombre.trim() + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}