package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión a la base de datos MySQL.
 * Implementa el patrón Singleton para reutilizar la conexión.
 */
public class ConexionMySQL {

    private static final String URL      = "jdbc:mysql://localhost:3306/nueva";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "";

    private static ConexionMySQL instancia;
    private Connection conexion;

    /**
     * Constructor privado — patrón Singleton.
     */
    private ConexionMySQL() {
        conectar();
    }

    /**
     * Retorna la única instancia de la conexión.
     * @return instancia Singleton
     */
    public static ConexionMySQL getInstance() {
        if (instancia == null) {
            instancia = new ConexionMySQL();
        }
        return instancia;
    }

    /**
     * Establece la conexión con MySQL.
     */
    private void conectar() {
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a MySQL");
        } catch (SQLException e) {
            System.err.println("Error al conectar a MySQL: " + e.getMessage());
        }
    }

    /**
     * Retorna la conexión activa, reconectando si es necesario.
     * @return objeto Connection
     * @throws SQLException si no se puede establecer conexión
     */
    public Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conectar();
        }
        return conexion;
    }

    /**
     * Cierra la conexión con la base de datos.
     */
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