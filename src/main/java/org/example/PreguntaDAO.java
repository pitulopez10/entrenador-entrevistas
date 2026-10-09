package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PreguntaDAO {

    public void agregar(Pregunta pregunta, int entrevistaId) throws SQLException {

        String sql = """
                INSERT INTO pregunta
                (texto, tipo, dificultad, entrevista_id)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, pregunta.getTexto());
            stmt.setString(2, pregunta.getTipo().name());
            stmt.setString(3, pregunta.getDificultad().name());
            stmt.setInt(4, entrevistaId);

            stmt.executeUpdate();
        }
    }
    // Preguntas de la entrevista que todavía no tienen respuesta
    public List<Pregunta> listarSinRespuesta(int entrevistaId) throws SQLException {

        List<Pregunta> preguntas = new ArrayList<>();

        String sql = """
            SELECT p.id, p.texto, p.tipo, p.dificultad
            FROM pregunta p
            LEFT JOIN respuesta r ON r.pregunta_id = p.id
            WHERE p.entrevista_id = ? AND r.id IS NULL
            ORDER BY p.id
            """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, entrevistaId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pregunta p = new Pregunta();
                    p.setId(rs.getInt("id"));
                    p.setTexto(rs.getString("texto"));
                    p.setTipo(TipoPregunta.valueOf(rs.getString("tipo").toUpperCase()));
                    p.setDificultad(TipoDificultad.valueOf(rs.getString("dificultad").toUpperCase()));
                    preguntas.add(p);
                }
            }
        }

        return preguntas;
    }

}