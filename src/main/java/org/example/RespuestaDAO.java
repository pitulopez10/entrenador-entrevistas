package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
