package controller;

import model.*;
import repository.SeñaRepositorioImpl;
import java.util.List;

/**
 * Controlador principal. Mediador entre el modelo y la vista.
 * Centraliza toda la lógica de negocio de la aplicación.
 */
public class SeñaController {

    private final SeñaRepositorioImpl repositorio;
    private final TextoTraductor traductor;
    private final HistorialManager historial;

    /**
     * Constructor principal — inicializa todos los componentes.
     */
    public SeñaController() {
        this.repositorio = new SeñaRepositorioImpl();
        this.traductor   = new TextoTraductor(repositorio);
        this.historial   = new HistorialManager();
    }

    // ── Búsqueda ────────────────────────────────────────────────────────────

    /**
     * Retorna todas las señas disponibles.
     * @return lista completa de señas
     */
    public List<Seña> obtenerTodas() {
        return repositorio.obtenerTodas();
    }

    /**
     * Filtra señas por categoría.
     * @param categoria categoría a filtrar
     * @return lista filtrada
     */
    public List<Seña> filtrarPorCategoria(CategoriaSeña categoria) {
        return repositorio.buscarPorCategoria(categoria);
    }

    /**
     * Busca señas por nombre (parcial, case-insensitive).
     * @param texto texto a buscar
     * @return lista de coincidencias
     */
    public List<Seña> buscar(String texto) {
        return repositorio.buscarPorNombre(texto);
    }

    /**
     * Sobrecarga: busca filtrando además por categoría.
     * @param texto texto a buscar
     * @param categoria categoría a filtrar
     * @return lista de coincidencias filtradas
     */
    public List<Seña> buscar(String texto, CategoriaSeña categoria) {
        return repositorio.buscarPorNombre(texto).stream()
                .filter(s -> s.getCategoria() == categoria)
                .toList();
    }

    // ── Traducción ──────────────────────────────────────────────────────────

    /**
     * Traduce un texto en su secuencia de señas y lo registra en historial.
     * @param texto texto a traducir
     * @return lista de señas correspondientes
     * @throws IllegalArgumentException si el texto es nulo o vacío
     */
    public List<Seña> traducir(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El texto no puede estar vacío");
        }
        List<Seña> resultado = traductor.traducirTexto(texto);
        historial.registrar(texto, resultado);
        return resultado;
    }

    /**
     * Sobrecarga: traduce y limita el número de señas retornadas.
     * @param texto texto a traducir
     * @param limite máximo de señas
     * @return lista limitada de señas
     */
    public List<Seña> traducir(String texto, int limite) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El texto no puede estar vacío");
        }
        List<Seña> resultado = traductor.traducirTexto(texto, limite);
        historial.registrar(texto, resultado);
        return resultado;
    }

    // ── Historial ───────────────────────────────────────────────────────────

    /**
     * Retorna el historial completo de traducciones.
     * @return lista de entradas del historial
     */
    public List<HistorialManager.EntradaHistorial> obtenerHistorial() {
        return historial.obtenerHistorial();
    }

    /**
     * Limpia el historial de traducciones.
     */
    public void limpiarHistorial() {
        historial.limpiar();
    }

    /**
     * Retorna el total de señas cargadas en el sistema.
     * @return número de señas disponibles
     */
    public int totalSeñas() {
        return repositorio.contarTotal();
    }
}