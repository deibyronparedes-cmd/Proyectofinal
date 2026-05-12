package controller;

import model.*;
import repository.SeñaRepositorioImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Traduce una cadena de texto en una lista de señas correspondientes.
 * Busca cada carácter o palabra en el repositorio de señas.
 */
public class TextoTraductor {

    private final SeñaRepositorioImpl repositorio;

    /**
     * Constructor principal con repositorio inyectado.
     * @param repositorio fuente de datos de señas
     */
    public TextoTraductor(SeñaRepositorioImpl repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Constructor por defecto — crea su propio repositorio.
     */
    public TextoTraductor() {
        this.repositorio = new SeñaRepositorioImpl();
    }

    /**
     * Traduce un texto letra por letra.
     * @param texto cadena a traducir
     * @return lista de señas encontradas
     */
    public List<Seña> traducirTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            return new ArrayList<>();
        }

        List<Seña> resultado = new ArrayList<>();
        String textoLimpio = texto.toUpperCase().trim();

        for (char c : textoLimpio.toCharArray()) {
            if (c == ' ') continue;

            if (Character.isLetter(c)) {
                repositorio.buscarPorId("LETRA_" + c)
                        .ifPresent(resultado::add);
            } else if (Character.isDigit(c)) {
                repositorio.buscarPorId("NUMERO_" + c)
                        .ifPresent(resultado::add);
            }
        }
        return resultado;
    }

    /**
     * Sobrecarga: traduce con límite máximo de señas a retornar.
     * @param texto cadena a traducir
     * @param limite máximo de señas en el resultado
     * @return lista de señas limitada
     */
    public List<Seña> traducirTexto(String texto, int limite) {
        List<Seña> todas = traducirTexto(texto);
        return todas.size() <= limite ? todas : todas.subList(0, limite);
    }

    /**
     * Sobrecarga: traduce una lista de palabras buscando cada una completa.
     * @param palabras lista de palabras a buscar
     * @return lista de señas encontradas
     */
    public List<Seña> traducirTexto(List<String> palabras) {
        List<Seña> resultado = new ArrayList<>();
        for (String palabra : palabras) {
            String id = "PALABRA_" + palabra.toUpperCase();
            repositorio.buscarPorId(id).ifPresent(resultado::add);
        }
        return resultado;
    }
}