package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class PreguntaDAO {


    // ==================================================
    // AGREGAR UNA PREGUNTA
    // ==================================================

    public int agregar(
            Pregunta pregunta,
            int entrevistaId
    ) throws SQLException {

        String sql = """
                INSERT INTO pregunta
                (texto, tipo, dificultad, entrevista_id)
                VALUES (?, ?, ?, ?)
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
                    pregunta.getTexto()
            );

            stmt.setString(
                    2,
                    pregunta.getTipo().name()
            );

            stmt.setString(
                    3,
                    pregunta.getDificultad().name()
            );

            stmt.setInt(
                    4,
                    entrevistaId
            );


            stmt.executeUpdate();


            try (
                    ResultSet rs =
                            stmt.getGeneratedKeys()
            ) {

                if (rs.next()) {

                    int idGenerado =
                            rs.getInt(1);

                    pregunta.setId(
                            idGenerado
                    );

                    return idGenerado;
                }
            }
        }


        throw new SQLException(
                "No se pudo obtener el id de la pregunta."
        );
    }


    // ==================================================
    // AGREGAR TODAS LAS PREGUNTAS
    // ==================================================

    public void agregarTodas(
            List<Pregunta> preguntas,
            int entrevistaId
    ) throws SQLException {

        String sql = """
                INSERT INTO pregunta
                (texto, tipo, dificultad, entrevista_id)
                VALUES (?, ?, ?, ?)
                """;


        Connection conexion = null;

        try {

            conexion =
                    ConexionDB.obtenerConexion();

            conexion.setAutoCommit(false);


            try (
                    PreparedStatement stmt =
                            conexion.prepareStatement(
                                    sql,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                for (Pregunta pregunta : preguntas) {

                    stmt.setString(
                            1,
                            pregunta.getTexto()
                    );

                    stmt.setString(
                            2,
                            pregunta.getTipo().name()
                    );

                    stmt.setString(
                            3,
                            pregunta.getDificultad().name()
                    );

                    stmt.setInt(
                            4,
                            entrevistaId
                    );


                    stmt.executeUpdate();


                    try (
                            ResultSet rs =
                                    stmt.getGeneratedKeys()
                    ) {

                        if (rs.next()) {

                            pregunta.setId(
                                    rs.getInt(1)
                            );

                        } else {

                            throw new SQLException(
                                    "No se pudo obtener el id de una pregunta."
                            );
                        }
                    }
                }
            }


            conexion.commit();


        } catch (SQLException e) {

            if (conexion != null) {

                try {

                    conexion.rollback();

                } catch (SQLException rollbackError) {

                    rollbackError.printStackTrace();
                }
            }

            throw e;


        } finally {

            if (conexion != null) {

                try {

                    conexion.setAutoCommit(true);
                    conexion.close();

                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }
    }


    // ==================================================
    // PREGUNTAS SIN RESPUESTA
    // ==================================================

    public List<Pregunta> listarSinRespuesta(
            int entrevistaId
    ) throws SQLException {

        List<Pregunta> preguntas =
                new ArrayList<>();


        String sql = """
                SELECT
                    p.id,
                    p.texto,
                    p.tipo,
                    p.dificultad
                FROM pregunta p
                LEFT JOIN respuesta r
                    ON r.pregunta_id = p.id
                WHERE p.entrevista_id = ?
                  AND r.id IS NULL
                ORDER BY p.id
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

                    preguntas.add(
                            crearPreguntaDesdeResultSet(
                                    rs
                            )
                    );
                }
            }
        }


        return preguntas;
    }


    // ==================================================
    // LISTAR TODAS LAS PREGUNTAS DE UNA ENTREVISTA
    // ==================================================

    public List<Pregunta> listarPorEntrevista(
            int entrevistaId
    ) throws SQLException {

        List<Pregunta> preguntas =
                new ArrayList<>();


        String sql = """
                SELECT
                    id,
                    texto,
                    tipo,
                    dificultad
                FROM pregunta
                WHERE entrevista_id = ?
                ORDER BY id
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

                    preguntas.add(
                            crearPreguntaDesdeResultSet(
                                    rs
                            )
                    );
                }
            }
        }


        return preguntas;
    }


    // ==================================================
    // CONTAR PREGUNTAS
    // ==================================================

    public int contarPorEntrevista(
            int entrevistaId
    ) throws SQLException {

        String sql = """
                SELECT COUNT(*) AS total
                FROM pregunta
                WHERE entrevista_id = ?
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

    private Pregunta crearPreguntaDesdeResultSet(
            ResultSet rs
    ) throws SQLException {

        Pregunta pregunta =
                new Pregunta();


        pregunta.setId(
                rs.getInt("id")
        );

        pregunta.setTexto(
                rs.getString("texto")
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


        return pregunta;
    }
}