package service;

import config.DatabaseConnection;
import dao.DomicilioFiscalDao;
import entities.DomicilioFiscal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DomicilioFiscalService implements GenericService<DomicilioFiscal> {
    
    private DomicilioFiscalDao dao = new DomicilioFiscalDao();

    @Override
    public void insertar(DomicilioFiscal domicilio) throws SQLException {
        // Valido antes de insertar
        if (domicilio.getCalle() == null || domicilio.getCalle().trim().isEmpty()) {
            throw new SQLException("La calle es obligatoria");
        }
        if (domicilio.getCiudad() == null || domicilio.getCiudad().trim().isEmpty()) {
            throw new SQLException("La ciudad es obligatoria");
        }
        if (domicilio.getProvincia() == null || domicilio.getProvincia().trim().isEmpty()) {
            throw new SQLException("La provincia es obligatoria");
        }
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Inicio la transaccion
            
            dao.crear(domicilio, conn);
            
            conn.commit(); // Si todo salio bien, guardo
            System.out.println("Domicilio creado correctamente");
            
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Si hubo error, deshago todo
                System.out.println("Error al crear domicilio - se hizo rollback");
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public DomicilioFiscal obtenerPorId(Long id) throws SQLException {
        return dao.leer(id);
    }

    @Override
    public List<DomicilioFiscal> obtenerTodos() throws SQLException {
        return dao.leerTodos();
    }

    @Override
    public void actualizar(DomicilioFiscal domicilio) throws SQLException {
        // Valido
        if (domicilio.getCalle() == null || domicilio.getCalle().trim().isEmpty()) {
            throw new SQLException("La calle es obligatoria");
        }
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            dao.actualizar(domicilio, conn);
            
            conn.commit();
            System.out.println("Domicilio actualizado correctamente");
            
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            dao.eliminar(id, conn);
            
            conn.commit();
            System.out.println("Domicilio eliminado (baja logica)");
            
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}