package controller;

import model.*;
import repository.SeñaRepositorioImpl;
import java.util.ArrayList;
import java.util.List;

public class TextoTraductor {

    private final SeñaRepositorioImpl repositorio;

   
    public TextoTraductor(SeñaRepositorioImpl repositorio) {
        this.repositorio = repositorio;
    }

    
    public TextoTraductor() {
        this.repositorio = new SeñaRepositorioImpl();
    }

  
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

   
    public List<Seña> traducirTexto(String texto, int limite) {
        List<Seña> todas = traducirTexto(texto);
        return todas.size() <= limite ? todas : todas.subList(0, limite);
    }

    
    public List<Seña> traducirTexto(List<String> palabras) {
        List<Seña> resultado = new ArrayList<>();
        for (String palabra : palabras) {
            String id = "PALABRA_" + palabra.toUpperCase();
            repositorio.buscarPorId(id).ifPresent(resultado::add);
        }
        return resultado;
    }
}