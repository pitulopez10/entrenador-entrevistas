package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RespuestaDAO {

    public void agregar(String texto, int preguntaId) throws SQLException {

        String sql = """
                INSERT INTO respuesta (texto, pregunta_id)
                VALUES (?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, texto);
            stmt.setInt(2, preguntaId);

            stmt.executeUpdate();
        }
    }

    public List<Respuesta> listarPorEntrevista(int entrevistaId) throws SQLException {

        List<Respuesta> respuestas = new ArrayList<>();

        String sql = """
            SELECT r.id AS respuesta_id, 
                   r.texto AS respuesta_texto, 
                   p.id AS pregunta_id, 
                   p.texto AS pregunta_texto, 
                   p.tipo, 
                   p.dificultad
            FROM respuesta r
            JOIN pregunta p ON r.pregunta_id = p.id
            WHERE p.entrevista_id = ?
            ORDER BY r.id ASC
            """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, entrevistaId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Respuesta respuesta = new Respuesta();
                    respuesta.setId(rs.getInt("respuesta_id"));
                    respuesta.setTexto(rs.getString("respuesta_texto"));

                    Pregunta pregunta = new Pregunta();
                    pregunta.setId(rs.getInt("pregunta_id"));
                    pregunta.setTexto(rs.getString("pregunta_texto"));
                    pregunta.setTipo(TipoPregunta.valueOf(rs.getString("tipo").toUpperCase()));
                    pregunta.setDificultad(TipoDificultad.valueOf(rs.getString("dificultad").toUpperCase()));

                    respuesta.setPregunta(pregunta);
                    respuestas.add(respuesta);
                }
            }
        }

        return respuestas;
    }
}