package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class RespuestaDAO {


    // ==================================================
    // AGREGAR RESPUESTA
    // ==================================================

    public int agregar(
            String texto,
            int preguntaId
    ) throws SQLException {

        String sql = """
                INSERT INTO respuesta
                (texto, pregunta_id)
                VALUES (?, ?)
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

            stmt.setString(
                    1,
                    texto
            );

            stmt.setInt(
                    2,
                    preguntaId
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
                "No se pudo obtener el id de la respuesta."
        );
    }


    // ==================================================
    // MODIFICAR RESPUESTA
    // ==================================================

    public void modificar(
            int respuestaId,
            String texto
    ) throws SQLException {

        String sql = """
                UPDATE respuesta
                SET texto = ?
                WHERE id = ?
                """;

        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();

                PreparedStatement stmt =
                        conexion.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    texto
            );

            stmt.setInt(
                    2,
                    respuestaId
            );

            stmt.executeUpdate();
        }
    }


    // ==================================================
    // BUSCAR RESPUESTA DE UNA PREGUNTA
    // ==================================================

    public Respuesta buscarPorPregunta(
            int preguntaId
    ) throws SQLException {

        String sql = """
                SELECT
                    r.id AS respuesta_id,
                    r.texto AS respuesta_texto,
                    p.id AS pregunta_id,
                    p.texto AS pregunta_texto,
                    p.tipo,
                    p.dificultad
                FROM respuesta r
                JOIN pregunta p
                    ON r.pregunta_id = p.id
                WHERE p.id = ?
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
                    preguntaId
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                if (rs.next()) {

                    return crearRespuestaDesdeResultSet(
                            rs
                    );
                }
            }
        }

        return null;
    }


    // ==================================================
    // GUARDAR O ACTUALIZAR
    // ==================================================

    public void guardarOActualizar(
            String texto,
            int preguntaId
    ) throws SQLException {

        Respuesta existente =
                buscarPorPregunta(
                        preguntaId
                );

        if (existente == null) {

            agregar(
                    texto,
                    preguntaId
            );

        } else {

            modificar(
                    existente.getId(),
                    texto
            );
        }
    }


    // ==================================================
    // LISTAR RESPUESTAS DE UNA ENTREVISTA
    // ==================================================

    public List<Respuesta> listarPorEntrevista(
            int entrevistaId
    ) throws SQLException {

        List<Respuesta> respuestas =
                new ArrayList<>();

        String sql = """
                SELECT
                    r.id AS respuesta_id,
                    r.texto AS respuesta_texto,
                    p.id AS pregunta_id,
                    p.texto AS pregunta_texto,
                    p.tipo,
                    p.dificultad
                FROM respuesta r
                JOIN pregunta p
                    ON r.pregunta_id = p.id
                WHERE p.entrevista_id = ?
                ORDER BY p.id ASC
                """;

        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();

                PreparedStatement stmt =
                        conexion.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    entrevistaId
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                while (rs.next()) {

                    respuestas.add(
                            crearRespuestaDesdeResultSet(
                                    rs
                            )
                    );
                }
            }
        }

        return respuestas;
    }


    // ==================================================
    // CONTAR RESPUESTAS
    // ==================================================

    public int contarPorEntrevista(
            int entrevistaId
    ) throws SQLException {

        String sql = """
                SELECT COUNT(*) AS total
                FROM respuesta r
                JOIN pregunta p
                    ON r.pregunta_id = p.id
                WHERE p.entrevista_id = ?
                """;

        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();

                PreparedStatement stmt =
                        conexion.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    entrevistaId
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                if (rs.next()) {

                    return rs.getInt(
                            "total"
                    );
                }
            }
        }

        return 0;
    }


    // ==================================================
    // MÉTODO AUXILIAR
    // ==================================================

    private Respuesta crearRespuestaDesdeResultSet(
            ResultSet rs
    ) throws SQLException {

        Respuesta respuesta =
                new Respuesta();

        respuesta.setId(
                rs.getInt(
                        "respuesta_id"
                )
        );

        respuesta.setTexto(
                rs.getString(
                        "respuesta_texto"
                )
        );


        Pregunta pregunta =
                new Pregunta();

        pregunta.setId(
                rs.getInt(
                        "pregunta_id"
                )
        );

        pregunta.setTexto(
                rs.getString(
                        "pregunta_texto"
                )
        );

        pregunta.setTipo(
                TipoPregunta.valueOf(
                        rs.getString("tipo")
                                .toUpperCase()
                )
        );

        pregunta.setDificultad(
                TipoDificultad.valueOf(
                        rs.getString("dificultad")
                                .toUpperCase()
                )
        );


        respuesta.setPregunta(
                pregunta
        );

        return respuesta;
    }
}