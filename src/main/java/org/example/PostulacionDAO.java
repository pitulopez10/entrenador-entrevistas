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
            SELECT p.*, o.titulo AS tituloOferta, o.id AS ofertaId,
                   a.nombre AS nombreArea, e.nombre AS nombreEmpresa
            FROM postulacion p
            JOIN ofertalaboral o ON p.ofertalaboral_id = o.id
            JOIN area a ON o.area_id = a.id
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

                    Area area = new Area();
                    area.setNombre(rs.getString("nombreArea"));
                    oferta.setArea(area);

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

    public List<Postulacion> listarPorEmpresa(String rut) throws SQLException {

        List<Postulacion> postulaciones = new ArrayList<>();

        String sql = """
            SELECT p.id,
                   p.fechaPostulacion,
                   p.estado,
                   p.mensaje,

                   po.ci AS postulante_ci,
                   po.nombre AS nombrePostulante,
                   po.mail AS mailPostulante,

                   o.id AS oferta_id,
                   o.titulo AS tituloOferta

            FROM postulacion p

            JOIN postulante po
                ON p.postulante_ci = po.ci

            JOIN ofertalaboral o
                ON p.ofertalaboral_id = o.id

            WHERE o.empresa_rut = ?

            ORDER BY p.fechaPostulacion DESC
            """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)
        ) {

            stmt.setString(1, rut);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Postulacion postulacion = new Postulacion();

                    postulacion.setId(
                            rs.getInt("id")
                    );

                    if (rs.getDate("fechaPostulacion") != null) {

                        postulacion.setFechaPostulacion(
                                rs.getDate("fechaPostulacion")
                                        .toLocalDate()
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

                    postulante.setMail(
                            rs.getString("mailPostulante")
                    );

                    postulacion.setPostulante(
                            postulante
                    );

                    // OFERTA
                    OfertaLaboral oferta =
                            new OfertaLaboral();

                    oferta.setId(
                            rs.getInt("oferta_id")
                    );

                    oferta.setTitulo(
                            rs.getString("tituloOferta")
                    );

                    postulacion.setOfertaLaboral(
                            oferta
                    );

                    postulaciones.add(
                            postulacion
                    );
                }
            }
        }

        return postulaciones;
    }
    public void seleccionarPostulante(Integer id) throws SQLException {

        String sql = """
            UPDATE postulacion
            SET estado = ?
            WHERE id = ?
            """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)
        ) {

            stmt.setString(1, EstadoPostulacion.seleccionado.name());
            stmt.setInt(2, id);

            stmt.executeUpdate();
        }
    }
    // Cambia el estado SOLO si la postulación es de una oferta de esa empresa
    public boolean cambiarEstadoDePostulacion(int postulacionId,
                                              String rutEmpresa,
                                              EstadoPostulacion nuevoEstado)
            throws SQLException {

        String sql = """
                UPDATE postulacion p
                JOIN ofertalaboral o ON p.ofertalaboral_id = o.id
                SET p.estado = ?
                WHERE p.id = ? AND o.empresa_rut = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado.name());
            stmt.setInt(2, postulacionId);
            stmt.setString(3, rutEmpresa);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean existePostulacion(
            int ciPostulante,
            int idOferta
    ) throws SQLException {

        String sql = """
            SELECT COUNT(*) AS cantidad
            FROM postulacion
            WHERE postulante_ci = ?
              AND ofertalaboral_id = ?
            """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)
        ) {

            stmt.setInt(1, ciPostulante);
            stmt.setInt(2, idOferta);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("cantidad") > 0;
                }
            }
        }

        return false;
    }
    public List<Postulacion> listarPorOferta(int ofertaId) throws SQLException {
        List<Postulacion> postulaciones = new ArrayList<>();

        String sql = """
                SELECT p.id,
                       p.fechaPostulacion,
                       p.estado,
                       p.mensaje,
                       po.ci AS postulante_ci,
                       po.nombre AS nombrePostulante,
                       po.mail AS mailPostulante,
                       po.telefono AS telefonoPostulante
                FROM postulacion p
                JOIN postulante po ON p.postulante_ci = po.ci
                WHERE p.ofertalaboral_id = ?
                ORDER BY p.fechaPostulacion DESC
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, ofertaId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Postulacion postulacion = new Postulacion();
                    postulacion.setId(rs.getInt("id"));

                    if (rs.getDate("fechaPostulacion") != null) {
                        postulacion.setFechaPostulacion(
                                rs.getDate("fechaPostulacion").toLocalDate()
                        );
                    }

                    postulacion.setEstado(
                            EstadoPostulacion.valueOf(rs.getString("estado"))
                    );
                    postulacion.setMensaje(rs.getString("mensaje"));

                    Postulante postulante = new Postulante();
                    postulante.setCi(rs.getInt("postulante_ci"));
                    postulante.setNombre(rs.getString("nombrePostulante"));
                    postulante.setMail(rs.getString("mailPostulante"));
                    postulante.setTelefono(rs.getInt("telefonoPostulante"));

                    postulacion.setPostulante(postulante);
                    postulaciones.add(postulacion);
                }
            }
        }

        return postulaciones;
    }
    public List<Postulante> listarPostulantesPorOfertaPaginados(int ofertaId, String nombreFiltro, int limit, int offset) throws SQLException {
        List<Postulante> postulantes = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
            SELECT p.* 
            FROM postulante p
            JOIN postulacion pos ON p.ci = pos.postulante_ci
            WHERE pos.ofertalaboral_id = ?
            """);

        if (nombreFiltro != null && !nombreFiltro.isBlank()) {
            sql.append(" AND LOWER(p.nombre) LIKE LOWER(?)");
        }
        sql.append(" ORDER BY p.nombre LIMIT ? OFFSET ?");

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql.toString())) {

            int param = 1;
            stmt.setInt(param++, ofertaId);

            if (nombreFiltro != null && !nombreFiltro.isBlank()) {
                stmt.setString(param++, "%" + nombreFiltro.trim() + "%");
            }
            stmt.setInt(param++, limit);
            stmt.setInt(param++, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Postulante p = new Postulante();
                    p.setCi(rs.getInt("ci"));
                    p.setNombre(rs.getString("nombre"));
                    p.setMail(rs.getString("mail"));
                    postulantes.add(p);
                }
            }
        }
        return postulantes;
    }

    public int contarPostulantesPorOferta(int ofertaId, String nombreFiltro) throws SQLException {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*) 
            FROM postulante p
            JOIN postulacion pos ON p.ci = pos.postulante_ci
            WHERE pos.ofertalaboral_id = ?
            """);

        if (nombreFiltro != null && !nombreFiltro.isBlank()) {
            sql.append(" AND LOWER(p.nombre) LIKE LOWER(?)");
        }

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql.toString())) {

            int param = 1;
            stmt.setInt(param++, ofertaId);

            if (nombreFiltro != null && !nombreFiltro.isBlank()) {
                stmt.setString(param++, "%" + nombreFiltro.trim() + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    public List<Postulacion> listarPorOfertaFiltrosPaginados(int ofertaId, String nombre, String estado, int limit, int offset)
            throws SQLException {

        List<Postulacion> postulaciones = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
                SELECT p.*, 
                       post.ci AS postulanteCi, post.nombre AS postulanteNombre, 
                       post.mail AS postulanteMail, post.telefono AS postulanteTelefono,
                       post.localidad AS postulanteLocalidad, post.fotoPerfil AS postulanteFoto,
                       post.bloqueado AS postulanteBloqueado
                FROM postulacion p
                JOIN postulante post ON p.postulante_ci = post.ci
                WHERE p.ofertalaboral_id = ?
                """);

        if (nombre != null && !nombre.isBlank()) {
            sql.append(" AND LOWER(post.nombre) LIKE LOWER(?)");
        }
        if (estado != null && !estado.isBlank()) {
            sql.append(" AND p.estado = ?");
        }
        sql.append(" ORDER BY p.fechaPostulacion DESC LIMIT ? OFFSET ?");

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql.toString())) {

            int parametro = 1;
            stmt.setInt(parametro++, ofertaId);

            if (nombre != null && !nombre.isBlank()) {
                stmt.setString(parametro++, "%" + nombre.trim() + "%");
            }
            if (estado != null && !estado.isBlank()) {
                stmt.setString(parametro++, estado.toUpperCase().trim());
            }
            stmt.setInt(parametro++, limit);
            stmt.setInt(parametro, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Postulacion p = new Postulacion();
                    // Ajusta según los setters que tenga tu clase Postulacion
                    p.setId(rs.getInt("id"));
                    p.setMensaje(rs.getString("mensaje"));
                    if (rs.getDate("fechaPostulacion") != null) {
                        p.setFechaPostulacion(rs.getDate("fechaPostulacion").toLocalDate());
                    }
                    if (rs.getString("estado") != null) {
                        p.setEstado(EstadoPostulacion.desdeTexto(rs.getString("estado")));
                    }

                    Postulante postulante = new Postulante();
                    postulante.setCi(rs.getInt("postulanteCi"));
                    postulante.setNombre(rs.getString("postulanteNombre"));
                    postulante.setMail(rs.getString("postulanteMail"));
                    postulante.setTelefono(rs.getInt("postulanteTelefono"));
                    postulante.setLocalidad(rs.getString("postulanteLocalidad"));
                    postulante.setFotoPerfil(rs.getString("postulanteFoto"));
                    postulante.setBloqueado(rs.getBoolean("postulanteBloqueado"));

                    p.setPostulante(postulante);
                    postulaciones.add(p);
                }
            }
        }
        return postulaciones;
    }

    public int contarPorOfertaFiltros(int ofertaId, String nombre, String estado) throws SQLException {
        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(*) 
                FROM postulacion p
                JOIN postulante post ON p.postulante_ci = post.ci
                WHERE p.ofertalaboral_id = ?
                """);

        if (nombre != null && !nombre.isBlank()) {
            sql.append(" AND LOWER(post.nombre) LIKE LOWER(?)");
        }
        if (estado != null && !estado.isBlank()) {
            sql.append(" AND p.estado = ?");
        }

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql.toString())) {

            int parametro = 1;
            stmt.setInt(parametro++, ofertaId);

            if (nombre != null && !nombre.isBlank()) {
                stmt.setString(parametro++, "%" + nombre.trim() + "%");
            }
            if (estado != null && !estado.isBlank()) {
                stmt.setString(parametro, estado.toUpperCase().trim());
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    public void actualizar(Postulacion postulacion) throws SQLException {
        String sql = "UPDATE postulacion SET estado = ?, mensaje = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            // Guardamos el nombre del enum o su representación en texto
            stmt.setString(1, postulacion.getEstado() != null ? postulacion.getEstado().name() : "enviado");
            stmt.setString(2, postulacion.getMensaje());
            stmt.setInt(3, postulacion.getId());

            stmt.executeUpdate();
        }
    }

    public Postulacion buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM postulacion WHERE id = ?";
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Postulacion p = new Postulacion();
                    p.setId(rs.getInt("id"));
                    p.setMensaje(rs.getString("mensaje"));
                    if (rs.getDate("fechaPostulacion") != null) {
                        p.setFechaPostulacion(rs.getDate("fechaPostulacion").toLocalDate());
                    }
                    if (rs.getString("estado") != null) {
                        p.setEstado(EstadoPostulacion.desdeTexto(rs.getString("estado")));
                    }
                    return p;
                }
            }
        }
        return null;
    }
}
