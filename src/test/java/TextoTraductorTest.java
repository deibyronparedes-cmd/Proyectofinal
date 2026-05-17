package com.mycompany.Sistema;

import controller.TextoTraductor;
import model.Seña;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del TextoTraductor.
 */
class TextoTraductorTest {

    private TextoTraductor traductor;

    @BeforeEach
    void setUp() {
        traductor = new TextoTraductor();
    }

    @Test
    @DisplayName("Debe traducir texto simple letra por letra")
    void testTraducirTextoSimple() {
        List<Seña> resultado = traductor.traducirTexto("ABC");
        assertNotNull(resultado);
        assertEquals(3, resultado.size(), "Debe traducir 3 letras");
    }

    @Test
    @DisplayName("Debe ignorar espacios en la traducción")
    void testTraducirTextoConEspacios() {
        List<Seña> resultado = traductor.traducirTexto("A B C");
        assertNotNull(resultado);
        assertEquals(3, resultado.size(), "Debe ignorar espacios");
    }

    @Test
    @DisplayName("Debe retornar lista vacía con texto null")
    void testTraducirTextoNull() {
        List<Seña> resultado = traductor.traducirTexto((String) null);
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty(), "Debe retornar lista vacía");
    }

    @Test
    @DisplayName("Debe retornar lista vacía con texto en blanco")
    void testTraducirTextoBlanco() {
        List<Seña> resultado = traductor.traducirTexto("   ");
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty(), "Debe retornar lista vacía");
    }

    @Test
    @DisplayName("Debe traducir números correctamente")
    void testTraducirNumeros() {
        List<Seña> resultado = traductor.traducirTexto("123");
        assertNotNull(resultado);
        assertEquals(3, resultado.size(), "Debe traducir 3 números");
    }

    @Test
    @DisplayName("Debe respetar el límite en sobrecarga con límite")
    void testTraducirTextoConLimite() {
        List<Seña> resultado = traductor.traducirTexto("ABCDE", 3);
        assertNotNull(resultado);
        assertTrue(resultado.size() <= 3, "No debe superar el límite de 3");
    }

    @Test
    @DisplayName("Debe traducir texto en minúsculas correctamente")
    void testTraducirTextoMinusculas() {
        List<Seña> resultado = traductor.traducirTexto("abc");
        assertNotNull(resultado);
        assertEquals(3, resultado.size(), "Debe traducir minúsculas como mayúsculas");
    }

    @Test
    @DisplayName("Debe manejar texto mixto letras y números")
    void testTraducirTextoMixto() {
        List<Seña> resultado = traductor.traducirTexto("A1B2");
        assertNotNull(resultado);
        assertEquals(4, resultado.size(), "Debe traducir letras y números mezclados");
    }
}