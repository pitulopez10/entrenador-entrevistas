package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostulacionDAO implements CrudDAO<Postulacion, Integer> {

    @Override
    public void agregar(Postulacion postulacion) throws SQLException {

        String sql = """
                INSERT INTO postulacion
                (fechaPostulacion, estado, mensaje, postulante_ci, ofertalaboral_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            if (postulacion.getFechaPostulacion() != null) {
                stmt.setDate(
                        1,
                        java.sql.Date.valueOf(postulacion.getFechaPostulacion())
                );
            } else {
                stmt.setNull(1, java.sql.Types.DATE);
            }

            stmt.setString(
                    2,
                    postulacion.getEstado().name()
            );

            stmt.setString(
                    3,
                    postulacion.getMensaje()
            );

            stmt.setInt(
                    4,
                    postulacion.getPostulante().getCi()
            );

            stmt.setInt(
                    5,
                    postulacion.getOfertaLaboral().getId()
            );

            stmt.executeUpdate();
        }
    }

    @Override
    public Postulacion buscarPorId(Integer id) throws SQLException {

        String sql = """
                SELECT p.id,
                       p.fechaPostulacion,
                       p.estado,
                       p.mensaje,
                       po.ci AS postulante_ci,
                       po.nombre AS nombrePostulante,
                       o.id AS oferta_id,
                       o.titulo AS tituloOferta
                FROM postulacion p
                JOIN postulante po
                    ON p.postulante_ci = po.ci
                JOIN ofertalaboral o
                    ON p.ofertalaboral_id = o.id
                WHERE p.id = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Postulacion postulacion = new Postulacion();

                    postulacion.setId(
                            rs.getInt("id")
                    );

                    if (rs.getDate("fechaPostulacion") != null) {
                        postulacion.setFechaPostulacion(
                                rs.getDate("fechaPostulacion").toLocalDate()
                        );
                    }

                    postulacion.setEstado(
                            EstadoPostulacion.valueOf(
                                    rs.getString("estado")
                            )
                    );

                    postulacion.setMensaje(
                            rs.getString("mensaje")
                    );

                    // POSTULANTE
                    Postulante postulante = new Postulante();

                    postulante.setCi(
                            rs.getInt("postulante_ci")
                    );

                    postulante.setNombre(
                            rs.getString("nombrePostulante")
                    );

                    postulacion.setPostulante(postulante);

                    // OFERTA
                    OfertaLaboral oferta = new OfertaLaboral();

                    oferta.setId(
                            rs.getInt("oferta_id")
                    );

                    oferta.setTitulo(
                            rs.getString("tituloOferta")
                    );

                    postulacion.setOfertaLaboral(oferta);

                    return postulacion;
                }
            }
        }

        return null;
    }

    @Override
    public List<Postulacion> listar() throws SQLException {

        List<Postulacion> postulaciones = new ArrayList<>();

        String sql = """
                SELECT p.id,
                       p.fechaPostulacion,
                       p.estado,
                       p.mensaje,
                       po.ci AS postulante_ci,
                       po.nombre AS nombrePostulante,
                       o.id AS oferta_id,
                       o.titulo AS tituloOferta
                FROM postulacion p
                JOIN postulante po
                    ON p.postulante_ci = po.ci
                JOIN ofertalaboral o
                    ON p.ofertalaboral_id = o.id
                ORDER BY p.id
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Postulacion postulacion = new Postulacion();

                postulacion.setId(
                        rs.getInt("id")
                );

                if (rs.getDate("fechaPostulacion") != null) {
                    postulacion.setFechaPostulacion(
                            rs.getDate("fechaPostulacion").toLocalDate()
                    );
                }

                postulacion.setEstado(
                        EstadoPostulacion.valueOf(
                                rs.getString("estado")
                        )
                );

                postulacion.setMensaje(
                        rs.getString("mensaje")
                );

                // POSTULANTE
                Postulante postulante = new Postulante();

                postulante.setCi(
                        rs.getInt("postulante_ci")
                );

                postulante.setNombre(
                        rs.getString("nombrePostulante")
                );

                postulacion.setPostulante(postulante);

                // OFERTA LABORAL
                OfertaLaboral oferta = new OfertaLaboral();

                oferta.setId(
                        rs.getInt("oferta_id")
                );

                oferta.setTitulo(
                        rs.getString("tituloOferta")
                );

                postulacion.setOfertaLaboral(oferta);

                postulaciones.add(postulacion);
            }
        }

        return postulaciones;
    }

    @Override
    public void modificar(Postulacion postulacion) throws SQLException {

        String sql = """
                UPDATE postulacion
                SET fechaPostulacion = ?,
                    estado = ?,
                    mensaje = ?,
                    postulante_ci = ?,
                    ofertalaboral_id = ?
                WHERE id = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            if (postulacion.getFechaPostulacion() != null) {
                stmt.setDate(
                        1,
                        java.sql.Date.valueOf(postulacion.getFechaPostulacion())
                );
            } else {
                stmt.setNull(1, java.sql.Types.DATE);
            }

            stmt.setString(
                    2,
                    postulacion.getEstado().name()
            );

            stmt.setString(
                    3,
                    postulacion.getMensaje()
            );

            stmt.setInt(
                    4,
                    postulacion.getPostulante().getCi()
            );

            stmt.setInt(
                    5,
                    postulacion.getOfertaLaboral().getId()
            );

            stmt.setInt(
                    6,
                    postulacion.getId()
            );

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Integer id) throws SQLException {

        String sql = """
                DELETE FROM postulacion
                WHERE id = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }
    public List<Postulacion> listarPorPostulante(int ci) throws SQLException {

        List<Postulacion> postulaciones = new ArrayList<>();

        String sql = """
            SELECT p.*, o.titulo AS tituloOferta, o.id AS ofertaId, e.nombre AS nombreEmpresa
            FROM postulacion p
            JOIN ofertalaboral o ON p.ofertalaboral_id = o.id
            JOIN empresa e ON o.empresa_rut = e.rut
            WHERE p.postulante_ci = ?
            ORDER BY p.fechaPostulacion
            """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, ci);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Postulacion postulacion = new Postulacion();
                    postulacion.setId(rs.getInt("id"));
                    postulacion.setFechaPostulacion(rs.getDate("fechaPostulacion").toLocalDate());
                    postulacion.setMensaje(rs.getString("mensaje"));

                    postulacion.setEstado(EstadoPostulacion.valueOf(rs.getString("estado")));

                    // una oferta solo con lo que necesitamos mostrar
                    OfertaLaboral oferta = new OfertaLaboral();
                    oferta.setId(rs.getInt("ofertaId"));
                    oferta.setTitulo(rs.getString("tituloOferta"));
                    postulacion.setOfertaLaboral(oferta);

                    // Idem con una empresa
                    Empresa empresa = new Empresa();
                    empresa.setNombre(rs.getString("nombreEmpresa"));
                    oferta.setEmpresa(empresa);

                    postulaciones.add(postulacion);
                }
            }
        }

        return postulaciones;
    }




}