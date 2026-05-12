package repository;

import controller.HistorialManager.EntradaHistorial;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para persistir el historial de traducciones en MySQL.
 * Maneja todas las operaciones CRUD sobre historial_traducciones.
 */
public class HistorialDAO {

    private final ConexionMySQL conexionMySQL;

    /**
     * Constructor — obtiene la instancia de conexión.
     */
    public HistorialDAO() {
        this.conexionMySQL = ConexionMySQL.getInstance();
    }

    /**
     * Guarda una entrada del historial en la base de datos.
     * @param entrada entrada a persistir
     */
    public void guardar(EntradaHistorial entrada) {
        String sql = "INSERT INTO historial_traducciones "
                   + "(texto_original, cantidad_senas) VALUES (?, ?)";
        try (PreparedStatement ps = 
                conexionMySQL.getConexion().prepareStatement(sql)) {
            ps.setString(1, entrada.getTextoOriginal());
            ps.setInt(2, entrada.getSeñas().size());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar historial: " + e.getMessage());
        }
    }

    /**
     * Retorna todos los registros del historial ordenados por fecha.
     * @return lista de arreglos con [id, texto, cantidad, fecha]
     */
    public List<String[]> obtenerTodos() {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT id, texto_original, cantidad_senas, fecha_hora "
                   + "FROM historial_traducciones ORDER BY fecha_hora DESC";
        try (Statement st = conexionMySQL.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("id"),
                    rs.getString("texto_original"),
                    rs.getString("cantidad_senas"),
                    rs.getString("fecha_hora")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener historial: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Elimina todos los registros del historial.
     */
    public void limpiar() {
        String sql = "DELETE FROM historial_traducciones";
        try (Statement st = conexionMySQL.getConexion().createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error al limpiar historial: " + e.getMessage());
        }
    }

    /**
     * Retorna el total de traducciones guardadas.
     * @return cantidad de registros
     */
    public int contarRegistros() {
        String sql = "SELECT COUNT(*) FROM historial_traducciones";
        try (Statement st = conexionMySQL.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Error al contar registros: " + e.getMessage());
        }
        return 0;
    }
}