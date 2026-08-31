package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfertaLaboralDAO implements CrudDAO<OfertaLaboral, Integer> {

    @Override
    public void agregar(OfertaLaboral oferta) throws SQLException {

    }

    @Override
    public OfertaLaboral buscarPorId(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<OfertaLaboral> listar() throws SQLException {

        List<OfertaLaboral> ofertas = new ArrayList<>();

        String sql = """
                SELECT o.*, e.nombre AS nombreEmpresa
                FROM ofertalaboral o
                JOIN empresa e ON o.empresa_rut = e.rut
                ORDER BY o.id
                """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                OfertaLaboral oferta = new OfertaLaboral();

                oferta.setId(rs.getInt("id"));
                oferta.setTitulo(rs.getString("titulo"));
                oferta.setDescripcion(rs.getString("descripcion"));
                oferta.setRequisitos(rs.getString("requisitos"));

                if (rs.getDate("fechaPublicacion") != null) {
                    oferta.setFechaPublicacion(
                            rs.getDate("fechaPublicacion").toLocalDate()
                    );
                }

                if (rs.getDate("fechaCierre") != null) {
                    oferta.setFechaCierre(
                            rs.getDate("fechaCierre").toLocalDate()
                    );
                }

                oferta.setEstado(
                        EstadoEntrevista.valueOf(
                                rs.getString("estado").toUpperCase()
                        )
                );

                Empresa empresa = new Empresa();
                empresa.setRut(rs.getString("empresa_rut"));
                empresa.setNombre(rs.getString("nombreEmpresa"));

                oferta.setEmpresa(empresa);

                ofertas.add(oferta);
            }
        }

        return ofertas;
    }

    @Override
    public void modificar(OfertaLaboral oferta) throws SQLException {

    }

    @Override
    public void eliminar(Integer id) throws SQLException {

    }
}