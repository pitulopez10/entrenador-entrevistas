package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EntrevistaDAO {

    private final PostulanteDAO postulanteDAO =
            new PostulanteDAO();

    private final OfertaLaboralDAO ofertaLaboralDAO =
            new OfertaLaboralDAO();


    // ==================================================
    // AGREGAR ENTREVISTA
    // ==================================================

    // Devuelve el id generado de la entrevista
    public int agregar(Entrevista entrevista)
            throws SQLException {

        String sql = """
                INSERT INTO entrevista
                (
                    fecha,
                    duracionMin,
                    estado,
                    postulante_ci,
                    ofertalaboral_id
                )
                VALUES (?, ?, ?, ?, ?)
                """;


        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();

                PreparedStatement stmt =
                        conexion.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            stmt.setDate(
                    1,
                    java.sql.Date.valueOf(
                            entrevista.getFecha()
                    )
            );

            stmt.setInt(
                    2,
                    entrevista.getDuracionMin()
            );

            stmt.setString(
                    3,
                    entrevista.getEstado().name()
            );

            stmt.setInt(
                    4,
                    entrevista.getPostulante().getCi()
            );

            stmt.setInt(
                    5,
                    entrevista.getOfertaLaboral().getId()
            );


            stmt.executeUpdate();


            try (
                    ResultSet rs =
                            stmt.getGeneratedKeys()
            ) {

                if (rs.next()) {

                    return rs.getInt(1);
                }
            }
        }


        throw new SQLException(
                "No se pudo obtener el id de la entrevista."
        );
    }


    // ==================================================
    // BUSCAR ENTREVISTA ACTIVA DEL POSTULANTE
    // ==================================================

    public Integer buscarActivaDePostulante(
            int ci
    ) throws SQLException {

        String sql = """
                SELECT id
                FROM entrevista
                WHERE postulante_ci = ?
                  AND estado = 'ACTIVA'
                ORDER BY id DESC
                LIMIT 1
                """;


        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();

                PreparedStatement stmt =
                        conexion.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    ci
            );


            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                if (rs.next()) {

                    return rs.getInt("id");
                }
            }
        }


        return null;
    }


    // ==================================================
    // TERMINAR ENTREVISTA
    // ==================================================

    public void terminar(
            int id
    ) throws SQLException {

        String sql = """
                UPDATE entrevista
                SET estado = 'TERMINADA'
                WHERE id = ?
                """;


        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();

                PreparedStatement stmt =
                        conexion.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    id
            );


            stmt.executeUpdate();
        }
    }


    // ==================================================
    // BUSCAR ENTREVISTA POR ID
    // ==================================================

    public Entrevista buscarPorId(
            int id
    ) throws SQLException {

        String sql = """
                SELECT *
                FROM entrevista
                WHERE id = ?
                """;


        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();

                PreparedStatement stmt =
                        conexion.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    id
            );


            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                if (rs.next()) {

                    Entrevista entrevista =
                            new Entrevista();


                    entrevista.setId(
                            rs.getInt("id")
                    );


                    entrevista.setFecha(
                            rs.getDate("fecha")
                                    .toLocalDate()
                    );


                    entrevista.setDuracionMin(
                            rs.getInt("duracionMin")
                    );


                    entrevista.setEstado(
                            EstadoEntrevista.valueOf(
                                    rs.getString("estado").toUpperCase()
                            )
                    );


                    // =========================
                    // POSTULANTE
                    // =========================

                    int postulanteCi =
                            rs.getInt(
                                    "postulante_ci"
                            );


                    Postulante postulante =
                            postulanteDAO.buscarPorId(
                                    postulanteCi
                            );


                    entrevista.setPostulante(
                            postulante
                    );


                    // =========================
                    // OFERTA LABORAL
                    // =========================

                    int ofertaId =
                            rs.getInt(
                                    "ofertalaboral_id"
                            );


                    OfertaLaboral oferta =
                            ofertaLaboralDAO.buscarPorId(
                                    ofertaId
                            );


                    entrevista.setOfertaLaboral(
                            oferta
                    );


                    return entrevista;
                }
            }
        }


        return null;
    }
}