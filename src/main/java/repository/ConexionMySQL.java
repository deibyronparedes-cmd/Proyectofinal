package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionMySQL {

    private static final String URL      = "jdbc:mysql://localhost:3306/nueva";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "";

    private static ConexionMySQL instancia;
    private Connection conexion;

   
    private ConexionMySQL() {
        conectar();
    }

  
    public static ConexionMySQL getInstance() {
        if (instancia == null) {
            instancia = new ConexionMySQL();
        }
        return instancia;
    }

   
    private void conectar() {
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a MySQL");
        } catch (SQLException e) {
            System.err.println("Error al conectar a MySQL: " + e.getMessage());
        }
    }

    
    public Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conectar();
        }
        return conexion;
    }

    
    public void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}