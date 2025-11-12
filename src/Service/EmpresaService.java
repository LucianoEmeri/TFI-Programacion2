package service;

import config.DatabaseConnection;
import dao.DomicilioFiscalDao;
import dao.EmpresaDao;
import entities.DomicilioFiscal;
import entities.Empresa;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EmpresaService implements GenericService<Empresa> {
    
    private EmpresaDao empresaDao = new EmpresaDao();
    private DomicilioFiscalDao domicilioDao = new DomicilioFiscalDao();

    @Override
    public void insertar(Empresa empresa) throws SQLException {
        // Primero valido todo ANTES de abrir la conexion
        validarEmpresa(empresa);
        
        // Me fijo que no exista ya ese CUIT
        Empresa empresaExistente = empresaDao.buscarPorCuit(empresa.getCuit());
        if (empresaExistente != null) {
            throw new SQLException("Ya existe una empresa con ese CUIT: " + empresa.getCuit());
        }
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Aca empieza la transaccion
            
            // PASO 1: Crear el domicilio primero (necesito el ID)
            if (empresa.getDomicilioFiscal() != null) {
                domicilioDao.crear(empresa.getDomicilioFiscal(), conn);
                System.out.println("Domicilio fiscal creado con ID: " + empresa.getDomicilioFiscal().getId());
            }
            
            // PASO 2: Crear la empresa que apunta al domicilio
            empresaDao.crear(empresa, conn);
            System.out.println("Empresa creada con ID: " + empresa.getId());
            
            // Si llegue hasta aca sin errores, guardo todo
            conn.commit();
            System.out.println("Transaccion completada exitosamente!");
            
        } catch (SQLException e) {
            // Si hubo algun error, deshago TODO (rollback)
            if (conn != null) {
                conn.rollback();
                System.out.println("ERROR - Se hizo rollback: " + e.getMessage());
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true); // Vuelvo al modo normal
                conn.close();
            }
        }
    }

    @Override
    public Empresa obtenerPorId(Long id) throws SQLException {
        return empresaDao.leer(id);
    }
    
    // Metodo extra para buscar por CUIT
    public Empresa buscarPorCuit(String cuit) throws SQLException {
        return empresaDao.buscarPorCuit(cuit);
    }

    @Override
    public List<Empresa> obtenerTodos() throws SQLException {
        return empresaDao.leerTodos();
    }

    @Override
    public void actualizar(Empresa empresa) throws SQLException {
        validarEmpresa(empresa);
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Actualizo el domicilio si existe
            if (empresa.getDomicilioFiscal() != null && empresa.getDomicilioFiscal().getId() != null) {
                domicilioDao.actualizar(empresa.getDomicilioFiscal(), conn);
            }
            
            // Actualizo la empresa
            empresaDao.actualizar(empresa, conn);
            
            conn.commit();
            System.out.println("Empresa actualizada correctamente");
            
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
            
            // Baja logica de la empresa
            empresaDao.eliminar(id, conn);
            
            conn.commit();
            System.out.println("Empresa eliminada (baja logica)");
            
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
    
    // Metodo para validar los datos de la empresa
    private void validarEmpresa(Empresa empresa) throws SQLException {
        // Razon Social
        if (empresa.getRazonSocial() == null || empresa.getRazonSocial().trim().isEmpty()) {
            throw new SQLException("La razon social es obligatoria");
        }
        
        // CUIT
        if (empresa.getCuit() == null || empresa.getCuit().trim().isEmpty()) {
            throw new SQLException("El CUIT es obligatorio");
        }
        
        // Valido el formato del CUIT (XX-XXXXXXXX-X)
        if (!empresa.getCuit().matches("\\d{2}-\\d{8}-\\d{1}")) {
            throw new SQLException("Formato de CUIT invalido. Debe ser XX-XXXXXXXX-X");
        }
        
        // Email (solo si existe)
        if (empresa.getEmail() != null && !empresa.getEmail().isEmpty()) {
            if (!empresa.getEmail().contains("@")) {
                throw new SQLException("Email invalido");
            }
        }
        
        // Si tiene domicilio, lo valido tambien
        if (empresa.getDomicilioFiscal() != null) {
            DomicilioFiscal dom = empresa.getDomicilioFiscal();
            if (dom.getCalle() == null || dom.getCalle().trim().isEmpty()) {
                throw new SQLException("La calle del domicilio es obligatoria");
            }
            if (dom.getCiudad() == null || dom.getCiudad().trim().isEmpty()) {
                throw new SQLException("La ciudad del domicilio es obligatoria");
            }
        }
    }
}