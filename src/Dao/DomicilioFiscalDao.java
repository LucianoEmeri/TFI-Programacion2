package dao;

import config.DatabaseConnection;
import entities.DomicilioFiscal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DomicilioFiscalDao implements GenericDao<DomicilioFiscal> {

    @Override
    public void crear(DomicilioFiscal domicilio, Connection conn) throws SQLException {
        String sql = "INSERT INTO domicilio_fiscal (calle, numero, ciudad, provincia, codigo_postal, pais) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, domicilio.getCalle());
        ps.setInt(2, domicilio.getNumero());
        ps.setString(3, domicilio.getCiudad());
        ps.setString(4, domicilio.getProvincia());
        ps.setString(5, domicilio.getCodigoPostal());
        ps.setString(6, domicilio.getPais());
        
        ps.executeUpdate();
        
        // Obtener el ID que se genero
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            domicilio.setId(rs.getLong(1));
        }
        rs.close();
        ps.close();
    }

    @Override
    public DomicilioFiscal leer(Long id) throws SQLException {
        String sql = "SELECT * FROM domicilio_fiscal WHERE id = ? AND eliminado = 0";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        
        ResultSet rs = ps.executeQuery();
        DomicilioFiscal domicilio = null;
        
        if (rs.next()) {
            domicilio = new DomicilioFiscal();
            domicilio.setId(rs.getLong("id"));
            domicilio.setEliminado(rs.getBoolean("eliminado"));
            domicilio.setCalle(rs.getString("calle"));
            domicilio.setNumero(rs.getInt("numero"));
            domicilio.setCiudad(rs.getString("ciudad"));
            domicilio.setProvincia(rs.getString("provincia"));
            domicilio.setCodigoPostal(rs.getString("codigo_postal"));
            domicilio.setPais(rs.getString("pais"));
        }
        
        rs.close();
        ps.close();
        conn.close();
        return domicilio;
    }

    @Override
    public List<DomicilioFiscal> leerTodos() throws SQLException {
        List<DomicilioFiscal> lista = new ArrayList<>();
        String sql = "SELECT * FROM domicilio_fiscal WHERE eliminado = 0";
        
        Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        while (rs.next()) {
            DomicilioFiscal d = new DomicilioFiscal();
            d.setId(rs.getLong("id"));
            d.setEliminado(rs.getBoolean("eliminado"));
            d.setCalle(rs.getString("calle"));
            d.setNumero(rs.getInt("numero"));
            d.setCiudad(rs.getString("ciudad"));
            d.setProvincia(rs.getString("provincia"));
            d.setCodigoPostal(rs.getString("codigo_postal"));
            d.setPais(rs.getString("pais"));
            lista.add(d);
        }
        
        rs.close();
        st.close();
        conn.close();
        return lista;
    }

    @Override
    public void actualizar(DomicilioFiscal domicilio, Connection conn) throws SQLException {
        String sql = "UPDATE domicilio_fiscal SET calle=?, numero=?, ciudad=?, " +
                     "provincia=?, codigo_postal=?, pais=? WHERE id=?";
        
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, domicilio.getCalle());
        ps.setInt(2, domicilio.getNumero());
        ps.setString(3, domicilio.getCiudad());
        ps.setString(4, domicilio.getProvincia());
        ps.setString(5, domicilio.getCodigoPostal());
        ps.setString(6, domicilio.getPais());
        ps.setLong(7, domicilio.getId());
        
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void eliminar(Long id, Connection conn) throws SQLException {
        // Baja logica - no borro el registro, solo lo marco como eliminado
        String sql = "UPDATE domicilio_fiscal SET eliminado = 1 WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, id);
        ps.executeUpdate();
        ps.close();
    }
}