package controller;

import model.*;
import repository.SeñaRepositorioImpl;
import java.util.List;


public class SeñaController {

    private final SeñaRepositorioImpl repositorio;
    private final TextoTraductor traductor;
    private final HistorialManager historial;

   
    public SeñaController() {
        this.repositorio = new SeñaRepositorioImpl();
        this.traductor   = new TextoTraductor(repositorio);
        this.historial   = new HistorialManager();
    }

    //Búsqueda 

   
    public List<Seña> obtenerTodas() {
        return repositorio.obtenerTodas();
    }

   
    public List<Seña> filtrarPorCategoria(CategoriaSeña categoria) {
        return repositorio.buscarPorCategoria(categoria);
    }

   
    public List<Seña> buscar(String texto) {
        return repositorio.buscarPorNombre(texto);
    }

  
    public List<Seña> buscar(String texto, CategoriaSeña categoria) {
        return repositorio.buscarPorNombre(texto).stream()
                .filter(s -> s.getCategoria() == categoria)
                .toList();
    }

    //  Traducción

    
    public List<Seña> traducir(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El texto no puede estar vacío");
        }
        List<Seña> resultado = traductor.traducirTexto(texto);
        historial.registrar(texto, resultado);
        return resultado;
    }

    
    public List<Seña> traducir(String texto, int limite) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El texto no puede estar vacío");
        }
        List<Seña> resultado = traductor.traducirTexto(texto, limite);
        historial.registrar(texto, resultado);
        return resultado;
    }

    // Historial─

   
    public List<HistorialManager.EntradaHistorial> obtenerHistorial() {
        return historial.obtenerHistorial();
    }

    
    public void limpiarHistorial() {
        historial.limpiar();
    }

    
    public int totalSeñas() {
        return repositorio.contarTotal();
    }
}