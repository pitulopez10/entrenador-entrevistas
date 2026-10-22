package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class RetroAlimentacionDAO
        implements CrudDAO<RetroAlimentacion, Integer> {


    // ==================================================
    // AGREGAR RETROALIMENTACIÓN
    // ==================================================

    @Override
    public void agregar(
            RetroAlimentacion retro
    ) throws SQLException {

        String sql = """
                INSERT INTO retroalimentacion
                (texto, entrevista_id)
                VALUES (?, ?)
                """;

        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();

                PreparedStatement stmt =
                        conexion.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    retro.getTexto()
            );

            stmt.setInt(
                    2,
                    retro.getEntrevista().getId()
            );

            stmt.executeUpdate();
        }
    }


    // ==================================================
    // BUSCAR POR ENTREVISTA
    // ==================================================
    //
    // RetroAlimentacion no tiene ID propio.
    //
    // Por eso el Integer recibido se interpreta
    // como entrevista_id.
    // ==================================================

    @Override
    public RetroAlimentacion buscarPorId(
            Integer entrevistaId
    ) throws SQLException {

        return buscarPorEntrevista(
                entrevistaId
        );
    }


    // ==================================================
    // LISTAR TODAS
    // ==================================================

    @Override
    public List<RetroAlimentacion> listar()
            throws SQLException {

        List<RetroAlimentacion> lista =
                new ArrayList<>();


        String sql = """
                SELECT texto, entrevista_id
                FROM retroalimentacion
                ORDER BY entrevista_id ASC
                """;


        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();

                PreparedStatement stmt =
                        conexion.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                lista.add(
                        crearRetroDesdeResultSet(
                                rs
                        )
                );
            }
        }


        return lista;
    }


    // ==================================================
    // MODIFICAR
    // ==================================================
    //
    // Se modifica usando entrevista_id.
    // ==================================================

    @Override
    public void modificar(
            RetroAlimentacion retro
    ) throws SQLException {

        String sql = """
                UPDATE retroalimentacion
                SET texto = ?
                WHERE entrevista_id = ?
                """;


        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();

                PreparedStatement stmt =
                        conexion.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    retro.getTexto()
            );


            stmt.setInt(
                    2,
                    retro.getEntrevista().getId()
            );


            stmt.executeUpdate();
        }
    }


    // ==================================================
    // ELIMINAR
    // ==================================================
    //
    // Como no existe ID propio,
    // eliminamos utilizando entrevista_id.
    // ==================================================

    @Override
    public void eliminar(
            Integer entrevistaId
    ) throws SQLException {

        eliminarPorEntrevista(
                entrevistaId
        );
    }


    // ==================================================
    // BUSCAR POR ENTREVISTA
    // ==================================================

    public RetroAlimentacion buscarPorEntrevista(
            int entrevistaId
    ) throws SQLException {

        String sql = """
                SELECT texto, entrevista_id
                FROM retroalimentacion
                WHERE entrevista_id = ?
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
                    entrevistaId
            );


            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                if (rs.next()) {

                    return crearRetroDesdeResultSet(
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
    //
    // Si todavía no existe:
    //      INSERT
    //
    // Si ya existe:
    //      UPDATE
    // ==================================================

    public void guardarOActualizar(
            RetroAlimentacion retro
    ) throws SQLException {

        int entrevistaId =
                retro.getEntrevista().getId();


        RetroAlimentacion existente =
                buscarPorEntrevista(
                        entrevistaId
                );


        if (existente == null) {

            agregar(
                    retro
            );

        } else {

            modificar(
                    retro
            );
        }
    }


    // ==================================================
    // ELIMINAR POR ENTREVISTA
    // ==================================================

    public void eliminarPorEntrevista(
            int entrevistaId
    ) throws SQLException {

        String sql = """
                DELETE FROM retroalimentacion
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


            stmt.executeUpdate();
        }
    }


    // ==================================================
    // MÉTODO AUXILIAR
    // ==================================================

    private RetroAlimentacion crearRetroDesdeResultSet(
            ResultSet rs
    ) throws SQLException {

        RetroAlimentacion retro =
                new RetroAlimentacion();


        retro.setTexto(
                rs.getString(
                        "texto"
                )
        );


        Entrevista entrevista =
                new Entrevista();


        entrevista.setId(
                rs.getInt(
                        "entrevista_id"
                )
        );


        retro.setEntrevista(
                entrevista
        );


        return retro;
    }
}