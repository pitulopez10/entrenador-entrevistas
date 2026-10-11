package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetroAlimentacionDAO implements CrudDAO<RetroAlimentacion, Integer> {

    @Override
    public void agregar(RetroAlimentacion retro) throws SQLException {
        String sql = """
                INSERT INTO retroalimentacion (texto, entrevista_id)
                VALUES (?, ?)
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, retro.getTexto());
            stmt.setInt(2, retro.getEntrevista().getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public RetroAlimentacion buscarPorId(Integer id) throws SQLException {
        String sql = """
                SELECT id, texto, entrevista_id
                FROM retroalimentacion
                WHERE id = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    RetroAlimentacion retro = new RetroAlimentacion();
                    retro.setTexto(rs.getString("texto"));

                    Entrevista entrevista = new Entrevista();
                    entrevista.setId(rs.getInt("entrevista_id"));
                    retro.setEntrevista(entrevista);

                    return retro;
                }
            }
        }
        return null;
    }

    @Override
    public List<RetroAlimentacion> listar() throws SQLException {
        List<RetroAlimentacion> lista = new ArrayList<>();
        String sql = """
                SELECT id, texto, entrevista_id
                FROM retroalimentacion
                ORDER BY id ASC
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RetroAlimentacion retro = new RetroAlimentacion();
                retro.setTexto(rs.getString("texto"));

                Entrevista entrevista = new Entrevista();
                entrevista.setId(rs.getInt("entrevista_id"));
                retro.setEntrevista(entrevista);

                lista.add(retro);
            }
        }
        return lista;
    }

    @Override
    public void modificar(RetroAlimentacion retro) throws SQLException {
        String sql = """
                UPDATE retroalimentacion
                SET texto = ?, entrevista_id = ?
                WHERE id = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, retro.getTexto());
            stmt.setInt(2, retro.getEntrevista().getId());
            stmt.setInt(3, retro.getEntrevista().getId()); // O el ID propio de retroalimentación si se cuenta con él

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        String sql = """
                DELETE FROM retroalimentacion
                WHERE id = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    // --- MÉTODOS ESPECÍFICOS ---

    public RetroAlimentacion buscarPorEntrevista(int entrevistaId) throws SQLException {
        String sql = """
                SELECT id, texto, entrevista_id 
                FROM retroalimentacion 
                WHERE entrevista_id = ?
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, entrevistaId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    RetroAlimentacion retro = new RetroAlimentacion();
                    retro.setTexto(rs.getString("texto"));

                    Entrevista e = new Entrevista();
                    e.setId(rs.getInt("entrevista_id"));
                    retro.setEntrevista(e);

                    return retro;
                }
            }
        }
        return null;
    }
}