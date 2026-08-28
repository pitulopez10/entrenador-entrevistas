package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostulanteDAO implements CrudDAO<Postulante, Integer> {

    @Override
    public void agregar(Postulante postulante) throws SQLException {

        String sql = """
                INSERT INTO postulante
                (ci, nombre, mail, password, fechaRegistro, telefono,
                 descripcion, fotoPerfil, cv, fechaNacimiento,
                 genero, localidad, datosEstudio, datosExperiencia, bloqueado)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, postulante.getCi());
            stmt.setString(2, postulante.getNombre());
            stmt.setString(3, postulante.getMail());
            stmt.setString(4, postulante.getPassword());

            if (postulante.getFechaRegistro() != null) {
                stmt.setDate(5,
                        java.sql.Date.valueOf(postulante.getFechaRegistro()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }

            stmt.setInt(6, postulante.getTelefono());
            stmt.setString(7, postulante.getDescripcion());
            stmt.setString(8, postulante.getFotoPerfil());
            stmt.setString(9, postulante.getCv());

            if (postulante.getFechaNacimiento() != null) {
                stmt.setDate(10,
                        java.sql.Date.valueOf(postulante.getFechaNacimiento()));
            } else {
                stmt.setNull(10, java.sql.Types.DATE);
            }

            stmt.setString(11, postulante.getGenero().name());
            stmt.setString(12, postulante.getLocalidad());
            stmt.setString(13, postulante.getDatosEstudio());
            stmt.setString(14, postulante.getDatosExperiencia());
            stmt.setBoolean(15, postulante.isBloqueado());

            stmt.executeUpdate();
        }
    }

    @Override
    public Postulante buscarPorId(Integer ci) throws SQLException {

        String sql = """
                SELECT *
                FROM postulante
                WHERE ci = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, ci);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Postulante postulante = new Postulante();

                    postulante.setCi(rs.getInt("ci"));
                    postulante.setNombre(rs.getString("nombre"));
                    postulante.setMail(rs.getString("mail"));
                    postulante.setPassword(rs.getString("password"));

                    if (rs.getDate("fechaRegistro") != null) {
                        postulante.setFechaRegistro(
                                rs.getDate("fechaRegistro").toLocalDate()
                        );
                    }

                    postulante.setTelefono(rs.getInt("telefono"));
                    postulante.setDescripcion(rs.getString("descripcion"));
                    postulante.setFotoPerfil(rs.getString("fotoPerfil"));
                    postulante.setCv(rs.getString("cv"));

                    if (rs.getDate("fechaNacimiento") != null) {
                        postulante.setFechaNacimiento(
                                rs.getDate("fechaNacimiento").toLocalDate()
                        );
                    }

                    postulante.setGenero(
                            DTGenero.valueOf(rs.getString("genero"))
                    );

                    postulante.setLocalidad(rs.getString("localidad"));
                    postulante.setDatosEstudio(rs.getString("datosEstudio"));
                    postulante.setDatosExperiencia(rs.getString("datosExperiencia"));
                    postulante.setBloqueado(rs.getBoolean("bloqueado"));

                    return postulante;
                }
            }
        }

        return null;
    }

    @Override
    public List<Postulante> listar() throws SQLException {

        List<Postulante> postulantes = new ArrayList<>();

        String sql = """
                SELECT *
                FROM postulante
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Postulante postulante = new Postulante();

                postulante.setCi(rs.getInt("ci"));
                postulante.setNombre(rs.getString("nombre"));
                postulante.setMail(rs.getString("mail"));
                postulante.setPassword(rs.getString("password"));

                if (rs.getDate("fechaRegistro") != null) {
                    postulante.setFechaRegistro(
                            rs.getDate("fechaRegistro").toLocalDate()
                    );
                }

                postulante.setTelefono(rs.getInt("telefono"));
                postulante.setDescripcion(rs.getString("descripcion"));
                postulante.setFotoPerfil(rs.getString("fotoPerfil"));
                postulante.setCv(rs.getString("cv"));

                if (rs.getDate("fechaNacimiento") != null) {
                    postulante.setFechaNacimiento(
                            rs.getDate("fechaNacimiento").toLocalDate()
                    );
                }

                postulante.setGenero(
                        DTGenero.valueOf(rs.getString("genero"))
                );

                postulante.setLocalidad(rs.getString("localidad"));
                postulante.setDatosEstudio(rs.getString("datosEstudio"));
                postulante.setDatosExperiencia(rs.getString("datosExperiencia"));
                postulante.setBloqueado(rs.getBoolean("bloqueado"));

                postulantes.add(postulante);
            }
        }

        return postulantes;
    }

    @Override
    public void modificar(Postulante postulante) throws SQLException {

        String sql = """
                UPDATE postulante
                SET nombre = ?,
                    mail = ?,
                    password = ?,
                    fechaRegistro = ?,
                    telefono = ?,
                    descripcion = ?,
                    fotoPerfil = ?,
                    cv = ?,
                    fechaNacimiento = ?,
                    genero = ?,
                    localidad = ?,
                    datosEstudio = ?,
                    datosExperiencia = ?,
                    bloqueado = ?
                WHERE ci = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, postulante.getNombre());
            stmt.setString(2, postulante.getMail());
            stmt.setString(3, postulante.getPassword());

            if (postulante.getFechaRegistro() != null) {
                stmt.setDate(4,
                        java.sql.Date.valueOf(postulante.getFechaRegistro()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }

            stmt.setInt(5, postulante.getTelefono());
            stmt.setString(6, postulante.getDescripcion());
            stmt.setString(7, postulante.getFotoPerfil());
            stmt.setString(8, postulante.getCv());

            if (postulante.getFechaNacimiento() != null) {
                stmt.setDate(9,
                        java.sql.Date.valueOf(postulante.getFechaNacimiento()));
            } else {
                stmt.setNull(9, java.sql.Types.DATE);
            }

            stmt.setString(10, postulante.getGenero().name());
            stmt.setString(11, postulante.getLocalidad());
            stmt.setString(12, postulante.getDatosEstudio());
            stmt.setString(13, postulante.getDatosExperiencia());
            stmt.setBoolean(14, postulante.isBloqueado());
            stmt.setInt(15, postulante.getCi());

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Integer ci) throws SQLException {

        String sql = """
                DELETE FROM postulante
                WHERE ci = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, ci);

            stmt.executeUpdate();
        }
    }

    public void bloquear(Integer ci) throws SQLException {

        String sql = """
                UPDATE postulante
                SET bloqueado = TRUE
                WHERE ci = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, ci);

            stmt.executeUpdate();
        }
    }

    public void desbloquear(Integer ci) throws SQLException {

        String sql = """
                UPDATE postulante
                SET bloqueado = FALSE
                WHERE ci = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, ci);

            stmt.executeUpdate();
        }
    }
}