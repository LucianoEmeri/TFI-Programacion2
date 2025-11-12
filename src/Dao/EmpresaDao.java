package dao;

import config.DatabaseConnection;
import entities.Empresa;
import entities.DomicilioFiscal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDao implements GenericDao<Empresa> {
    
    private DomicilioFiscalDao domicilioDao = new DomicilioFiscalDao();

    @Override
    public void crear(Empresa empresa, Connection conn) throws SQLException {
        String sql = "INSERT INTO empresa (razon_social, cuit, actividad_principal, email, domicilio_fiscal_id) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, empresa.getRazonSocial());
        ps.setString(2, empresa.getCuit());
        ps.setString(3, empresa.getActividadPrincipal());
        ps.setString(4, empresa.getEmail());
        
        if (empresa.getDomicilioFiscal() != null) {
            ps.setLong(5, empresa.getDomicilioFiscal().getId());
        } else {
            ps.setNull(5, Types.BIGINT);
        }
        
        ps.executeUpdate();
        
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            empresa.setId(rs.getLong(1));
        }
        rs.close();
        ps.close();
    }

    @Override
    public Empresa leer(Long id) throws SQLException {
        String sql = "SELECT * FROM empresa WHERE id = ? AND eliminado = 0";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        
        ResultSet rs = ps.executeQuery();
        Empresa empresa = null;
        
        if (rs.next()) {
            empresa = convertirResultSetAEmpresa(rs);
        }
        
        rs.close();
        ps.close();
        conn.close();
        return empresa;
    }
    
    // Metodo adicional para buscar por CUIT
    public Empresa buscarPorCuit(String cuit) throws SQLException {
        String sql = "SELECT * FROM empresa WHERE cuit = ? AND eliminado = 0";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, cuit);
        
        ResultSet rs = ps.executeQuery();
        Empresa empresa = null;
        
        if (rs.next()) {
            empresa = convertirResultSetAEmpresa(rs);
        }
        
        rs.close();
        ps.close();
        conn.close();
        return empresa;
    }

    @Override
    public List<Empresa> leerTodos() throws SQLException {
        List<Empresa> lista = new ArrayList<>();
        String sql = "SELECT * FROM empresa WHERE eliminado = 0";
        
        Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        while (rs.next()) {
            lista.add(convertirResultSetAEmpresa(rs));
        }
        
        rs.close();
        st.close();
        conn.close();
        return lista;
    }

    @Override
    public void actualizar(Empresa empresa, Connection conn) throws SQLException {
        String sql = "UPDATE empresa SET razon_social=?, cuit=?, actividad_principal=?, " +
                     "email=?, domicilio_fiscal_id=? WHERE id=?";
        
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, empresa.getRazonSocial());
        ps.setString(2, empresa.getCuit());
        ps.setString(3, empresa.getActividadPrincipal());
        ps.setString(4, empresa.getEmail());
        
        if (empresa.getDomicilioFiscal() != null) {
            ps.setLong(5, empresa.getDomicilioFiscal().getId());
        } else {
            ps.setNull(5, Types.BIGINT);
        }
        
        ps.setLong(6, empresa.getId());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void eliminar(Long id, Connection conn) throws SQLException {
        String sql = "UPDATE empresa SET eliminado = 1 WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ps.executeUpdate();
        ps.close();
    }
    
    // Metodo auxiliar para convertir ResultSet a Empresa
    private Empresa convertirResultSetAEmpresa(ResultSet rs) throws SQLException {
        Empresa e = new Empresa();
        e.setId(rs.getLong("id"));
        e.setEliminado(rs.getBoolean("eliminado"));
        e.setRazonSocial(rs.getString("razon_social"));
        e.setCuit(rs.getString("cuit"));
        e.setActividadPrincipal(rs.getString("actividad_principal"));
        e.setEmail(rs.getString("email"));
        
        // Cargar el domicilio si existe
        Long domicilioId = rs.getLong("domicilio_fiscal_id");
        if (domicilioId != null && domicilioId > 0) {
            DomicilioFiscal dom = domicilioDao.leer(domicilioId);
            e.setDomicilioFiscal(dom);
        }
        
        return e;
    }
}