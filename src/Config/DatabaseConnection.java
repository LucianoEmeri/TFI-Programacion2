package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // Datos de conexion - CAMBIAR SI ES NECESARIO
    private static final String URL = "jdbc:mysql://localhost:3306/empresas_db";
    private static final String USER = "root";
    private static final String PASS = ""; // CAMBIALO POR TU CONTRASEÑA DE MYSQL
    
    // Metodo para obtener la conexion
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}