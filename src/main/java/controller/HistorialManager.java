package controller;

import model.Seña;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Gestiona el historial de traducciones realizadas durante la sesión.
 */
public class HistorialManager {

    private static final int CAPACIDAD_MAXIMA = 50;
    private static final DateTimeFormatter FORMATO = 
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final LinkedList<EntradaHistorial> historial = new LinkedList<>();

    /**
     * Registra una nueva traducción en el historial.
     * @param textoOriginal texto que se tradujo
     * @param señas lista de señas resultantes
     */
    public void registrar(String textoOriginal, List<Seña> señas) {
        if (textoOriginal == null || textoOriginal.isBlank()) return;

        if (historial.size() >= CAPACIDAD_MAXIMA) {
            historial.removeLast();
        }
        historial.addFirst(new EntradaHistorial(textoOriginal, señas));
    }

    /**
     * Sobrecarga: registra solo el texto sin lista de señas.
     * @param textoOriginal texto que se tradujo
     */
    public void registrar(String textoOriginal) {
        registrar(textoOriginal, new ArrayList<>());
    }

    /**
     * Retorna el historial completo (más reciente primero).
     * @return lista no modificable de entradas
     */
    public List<EntradaHistorial> obtenerHistorial() {
        return Collections.unmodifiableList(historial);
    }

    /**
     * Sobrecarga: retorna solo los últimos N registros.
     * @param cantidad número de registros a retornar
     * @return sublista del historial
     */
    public List<EntradaHistorial> obtenerHistorial(int cantidad) {
        int limite = Math.min(cantidad, historial.size());
        return Collections.unmodifiableList(
                new ArrayList<>(historial.subList(0, limite))
        );
    }

    /**
     * Limpia todo el historial.
     */
    public void limpiar() {
        historial.clear();
    }

    /**
     * Retorna el total de traducciones registradas.
     * @return cantidad de entradas en el historial
     */
    public int contarEntradas() {
        return historial.size();
    }

    // ── Clase interna ───────────────────────────────────────────────────────

    /**
     * Representa una entrada individual del historial.
     */
    public static class EntradaHistorial {

        private final String textoOriginal;
        private final List<Seña> señas;
        private final String fechaHora;

        public EntradaHistorial(String textoOriginal, List<Seña> señas) {
            this.textoOriginal = textoOriginal;
            this.señas = Collections.unmodifiableList(new ArrayList<>(señas));
            this.fechaHora = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        }

        public String getTextoOriginal() { return textoOriginal; }
        public List<Seña> getSeñas() { return señas; }
        public String getFechaHora() { return fechaHora; }

        @Override
        public String toString() {
            return "[" + fechaHora + "] " + textoOriginal 
                   + " → " + señas.size() + " seña(s)";
        }
    }
}