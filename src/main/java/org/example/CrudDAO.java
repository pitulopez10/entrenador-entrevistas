package org.example;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T, ID> {

    /*
     * CREATE
     * Ejemplo SQL:
     * INSERT INTO empresa (rut, nombre, mail, password, fechaRegistro, telefono,
     *                      descripcion, logo, sitioWeb)
     * VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
     */
    void agregar(T objeto) throws SQLException;

    /*
     * READ POR ID
     * Ejemplo SQL:
     * SELECT * FROM empresa
     * WHERE rut = ?;
     */
    T buscarPorId(ID id) throws SQLException;

    /*
     * READ TODOS
     * Ejemplo SQL:
     * SELECT * FROM empresa;
     */
    List<T> listar() throws SQLException;

    /*
     * UPDATE
     * Ejemplo SQL:
     * UPDATE empresa
     * SET nombre = ?, mail = ?, password = ?, fechaRegistro = ?,
     *     telefono = ?, descripcion = ?, logo = ?, sitioWeb = ?
     * WHERE rut = ?;
     */
    void modificar(T objeto) throws SQLException;

    /*
     * DELETE
     * Ejemplo SQL:
     * DELETE FROM empresa
     * WHERE rut = ?;
     */
    void eliminar(ID id) throws SQLException;
}