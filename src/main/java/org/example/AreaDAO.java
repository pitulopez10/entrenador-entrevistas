package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AreaDAO implements CrudDAO<Area, Integer> {

    @Override
    public void agregar(Area area) throws SQLException {

        String sql = """
                INSERT INTO area
                (nombre, descripcion)
                VALUES (?, ?)
                """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)
        ) {

            stmt.setString(1, area.getNombre());
            stmt.setString(2, area.getDescripcion());

            stmt.executeUpdate();
        }
    }

    @Override
    public Area buscarPorId(Integer id) throws SQLException {

        String sql = """
                SELECT *
                FROM area
                WHERE id = ?
                """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Area area = new Area();

                    area.setId(rs.getInt("id"));
                    area.setNombre(rs.getString("nombre"));
                    area.setDescripcion(rs.getString("descripcion"));

                    return area;
                }

                return null;
            }
        }
    }

    @Override
    public List<Area> listar() throws SQLException {

        List<Area> areas = new ArrayList<>();

        String sql = """
                SELECT id, nombre, descripcion
                FROM area
                ORDER BY id
                """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Area area = new Area();

                area.setId(rs.getInt("id"));
                area.setNombre(rs.getString("nombre"));
                area.setDescripcion(rs.getString("descripcion"));

                areas.add(area);
            }
        }

        return areas;
    }

    @Override
    public void modificar(Area area) throws SQLException {

        String sql = """
                UPDATE area
                SET nombre = ?, descripcion = ?
                WHERE id = ?
                """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)
        ) {

            stmt.setString(1, area.getNombre());
            stmt.setString(2, area.getDescripcion());
            stmt.setInt(3, area.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Integer id) throws SQLException {

        String sql = """
                DELETE FROM area
                WHERE id = ?
                """;

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }
}
