package controller;

import model.Seña;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class HistorialManager {

    private static final int CAPACIDAD_MAXIMA = 50;
    private static final DateTimeFormatter FORMATO = 
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final LinkedList<EntradaHistorial> historial = new LinkedList<>();

 
    public void registrar(String textoOriginal, List<Seña> señas) {
        if (textoOriginal == null || textoOriginal.isBlank()) return;

        if (historial.size() >= CAPACIDAD_MAXIMA) {
            historial.removeLast();
        }
        historial.addFirst(new EntradaHistorial(textoOriginal, señas));
    }

    
    public void registrar(String textoOriginal) {
        registrar(textoOriginal, new ArrayList<>());
    }

   
    public List<EntradaHistorial> obtenerHistorial() {
        return Collections.unmodifiableList(historial);
    }

    
    public List<EntradaHistorial> obtenerHistorial(int cantidad) {
        int limite = Math.min(cantidad, historial.size());
        return Collections.unmodifiableList(
                new ArrayList<>(historial.subList(0, limite))
        );
    }

   
    public void limpiar() {
        historial.clear();
    }

    
    public int contarEntradas() {
        return historial.size();
    }

    
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