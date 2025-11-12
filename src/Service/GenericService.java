package service;

import java.sql.SQLException;
import java.util.List;

public interface GenericService<T> {
    void insertar(T entidad) throws SQLException;
    T obtenerPorId(Long id) throws SQLException;
    List<T> obtenerTodos() throws SQLException;
    void actualizar(T entidad) throws SQLException;
    void eliminar(Long id) throws SQLException;
}