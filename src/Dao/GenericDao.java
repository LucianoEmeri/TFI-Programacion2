package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {
    void crear(T entidad, Connection conn) throws SQLException;
    T leer(Long id) throws SQLException;
    List<T> leerTodos() throws SQLException;
    void actualizar(T entidad, Connection conn) throws SQLException;
    void eliminar(Long id, Connection conn) throws SQLException;
}