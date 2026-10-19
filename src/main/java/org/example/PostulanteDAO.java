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
                (
                    ci,
                    nombre,
                    mail,
                    password,
                    fechaRegistro,
                    telefono,
                    descripcion,
                    fotoPerfil,
                    fotoPerfilPublicId,
                    cv,
                    cvPublicId,
                    fechaNacimiento,
                    genero,
                    localidad,
                    datosEstudio,
                    datosExperiencia,
                    bloqueado
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, postulante.getCi());
            stmt.setString(2, postulante.getNombre());
            stmt.setString(3, postulante.getMail());
            stmt.setString(4, postulante.getPassword());

            if (postulante.getFechaRegistro() != null) {
                stmt.setDate(
                        5,
                        java.sql.Date.valueOf(postulante.getFechaRegistro())
                );
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }

            stmt.setInt(6, postulante.getTelefono());
            stmt.setString(7, postulante.getDescripcion());

            // CLOUDINARY - FOTO
            stmt.setString(
                    8,
                    postulante.getFotoPerfil()
            );

            stmt.setString(
                    9,
                    postulante.getFotoPerfilPublicId()
            );

            // CLOUDINARY - CV
            stmt.setString(
                    10,
                    postulante.getCv()
            );

            stmt.setString(
                    11,
                    postulante.getCvPublicId()
            );

            if (postulante.getFechaNacimiento() != null) {
                stmt.setDate(
                        12,
                        java.sql.Date.valueOf(postulante.getFechaNacimiento())
                );
            } else {
                stmt.setNull(12, java.sql.Types.DATE);
            }

            if (postulante.getGenero() != null) {
                stmt.setString(
                        13,
                        postulante.getGenero().name()
                );
            } else {
                stmt.setNull(
                        13,
                        java.sql.Types.VARCHAR
                );
            }

            stmt.setString(
                    14,
                    postulante.getLocalidad()
            );

            stmt.setString(
                    15,
                    postulante.getDatosEstudio()
            );

            stmt.setString(
                    16,
                    postulante.getDatosExperiencia()
            );

            stmt.setBoolean(
                    17,
                    postulante.isBloqueado()
            );

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

                    return crearPostulanteDesdeResultSet(rs);
                }
            }
        }

        return null;
    }


    @Override
    public List<Postulante> listar() throws SQLException {

        List<Postulante> postulantes =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM postulante
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                postulantes.add(
                        crearPostulanteDesdeResultSet(rs)
                );
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
                    fotoPerfilPublicId = ?,
                    cv = ?,
                    cvPublicId = ?,
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

            stmt.setString(
                    1,
                    postulante.getNombre()
            );

            stmt.setString(
                    2,
                    postulante.getMail()
            );

            stmt.setString(
                    3,
                    postulante.getPassword()
            );

            if (postulante.getFechaRegistro() != null) {

                stmt.setDate(
                        4,
                        java.sql.Date.valueOf(
                                postulante.getFechaRegistro()
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
                    postulante.getTelefono()
            );

            stmt.setString(
                    6,
                    postulante.getDescripcion()
            );

            // CLOUDINARY - FOTO
            stmt.setString(
                    7,
                    postulante.getFotoPerfil()
            );

            stmt.setString(
                    8,
                    postulante.getFotoPerfilPublicId()
            );

            // CLOUDINARY - CV
            stmt.setString(
                    9,
                    postulante.getCv()
            );

            stmt.setString(
                    10,
                    postulante.getCvPublicId()
            );

            if (postulante.getFechaNacimiento() != null) {

                stmt.setDate(
                        11,
                        java.sql.Date.valueOf(
                                postulante.getFechaNacimiento()
                        )
                );

            } else {

                stmt.setNull(
                        11,
                        java.sql.Types.DATE
                );
            }

            if (postulante.getGenero() != null) {

                stmt.setString(
                        12,
                        postulante.getGenero().name()
                );

            } else {

                stmt.setNull(
                        12,
                        java.sql.Types.VARCHAR
                );
            }

            stmt.setString(
                    13,
                    postulante.getLocalidad()
            );

            stmt.setString(
                    14,
                    postulante.getDatosEstudio()
            );

            stmt.setString(
                    15,
                    postulante.getDatosExperiencia()
            );

            stmt.setBoolean(
                    16,
                    postulante.isBloqueado()
            );

            stmt.setInt(
                    17,
                    postulante.getCi()
            );

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

            stmt.setInt(
                    1,
                    ci
            );

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

            stmt.setInt(
                    1,
                    ci
            );

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

            stmt.setInt(
                    1,
                    ci
            );

            stmt.executeUpdate();
        }
    }


    public Postulante buscarPorMail(
            String mail
    ) throws SQLException {

        String sql = """
                SELECT *
                FROM postulante
                WHERE mail = ?
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

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return crearPostulanteDesdeResultSet(rs);
                }
            }
        }

        return null;
    }


    public Postulante iniciarSesion(
            String mail,
            String password
    ) throws SQLException {

        String sql = """
                SELECT ci
                FROM postulante
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

                    int ci =
                            rs.getInt("ci");

                    return buscarPorId(ci);
                }
            }
        }

        return null;
    }


    public void asignarArea(
            int ci,
            int areaId
    ) throws SQLException {

        String sql = """
                INSERT INTO postulante_area
                (postulante_ci, area_id)
                VALUES (?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    ci
            );

            stmt.setInt(
                    2,
                    areaId
            );

            stmt.executeUpdate();
        }
    }


    public List<Integer> listarIdsAreas(
            int ci
    ) throws SQLException {

        List<Integer> ids =
                new ArrayList<>();

        String sql = """
                SELECT area_id
                FROM postulante_area
                WHERE postulante_ci = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    ci
            );

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    ids.add(
                            rs.getInt("area_id")
                    );
                }
            }
        }

        return ids;
    }


    /*
     * ==================================================
     * MÉTODO AUXILIAR
     * ==================================================
     */

    private Postulante crearPostulanteDesdeResultSet(
            ResultSet rs
    ) throws SQLException {

        Postulante postulante =
                new Postulante();

        postulante.setCi(
                rs.getInt("ci")
        );

        postulante.setNombre(
                rs.getString("nombre")
        );

        postulante.setMail(
                rs.getString("mail")
        );

        postulante.setPassword(
                rs.getString("password")
        );

        if (rs.getDate("fechaRegistro") != null) {

            postulante.setFechaRegistro(
                    rs.getDate("fechaRegistro")
                            .toLocalDate()
            );
        }

        postulante.setTelefono(
                rs.getInt("telefono")
        );

        postulante.setDescripcion(
                rs.getString("descripcion")
        );


        // ==============================
        // CLOUDINARY - FOTO DE PERFIL
        // ==============================

        postulante.setFotoPerfil(
                rs.getString("fotoPerfil")
        );

        postulante.setFotoPerfilPublicId(
                rs.getString("fotoPerfilPublicId")
        );


        // ==============================
        // CLOUDINARY - CV
        // ==============================

        postulante.setCv(
                rs.getString("cv")
        );

        postulante.setCvPublicId(
                rs.getString("cvPublicId")
        );


        if (rs.getDate("fechaNacimiento") != null) {

            postulante.setFechaNacimiento(
                    rs.getDate("fechaNacimiento")
                            .toLocalDate()
            );
        }

        String genero =
                rs.getString("genero");

        if (genero != null) {

            postulante.setGenero(
                    DTGenero.valueOf(genero)
            );
        }

        postulante.setLocalidad(
                rs.getString("localidad")
        );

        postulante.setDatosEstudio(
                rs.getString("datosEstudio")
        );

        postulante.setDatosExperiencia(
                rs.getString("datosExperiencia")
        );

        postulante.setBloqueado(
                rs.getBoolean("bloqueado")
        );

        return postulante;
    }
}