package com.mycompany.proyectofinal;

import controller.HistorialManager;
import model.*;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;



class HistorialManagerTest {

    private HistorialManager historial;

    @BeforeEach
    void setUp() {
        historial = new HistorialManager();
    }

    @Test
    @DisplayName("Debe iniciar con historial vacío")
    void testHistorialInicialVacio() {
        assertTrue(historial.obtenerHistorial().isEmpty(),
                "El historial debe iniciar vacío");
    }

    @Test
    @DisplayName("Debe registrar una entrada correctamente")
    void testRegistrarEntrada() {
        historial.registrar("ABC", new ArrayList<>());
        assertEquals(1, historial.contarEntradas(),
                "Debe haber una entrada");
    }

    @Test
    @DisplayName("Debe registrar con sobrecarga sin lista")
    void testRegistrarSinLista() {
        historial.registrar("HOLA");
        assertEquals(1, historial.contarEntradas(),
                "Debe registrar sin lista de señas");
    }

    @Test
    @DisplayName("Debe retornar historial limitado correctamente")
    void testObtenerHistorialLimitado() {
        historial.registrar("A");
        historial.registrar("B");
        historial.registrar("C");
        List<HistorialManager.EntradaHistorial> resultado =
                historial.obtenerHistorial(2);
        assertEquals(2, resultado.size(),
                "Debe retornar solo 2 entradas");
    }

    @Test
    @DisplayName("Debe limpiar el historial correctamente")
    void testLimpiarHistorial() {
        historial.registrar("ABC");
        historial.limpiar();
        assertTrue(historial.obtenerHistorial().isEmpty(),
                "El historial debe estar vacío");
        assertEquals(0, historial.contarEntradas());
    }

    @Test
    @DisplayName("No debe registrar texto vacío")
    void testNoRegistrarTextoVacio() {
        historial.registrar("");
        assertEquals(0, historial.contarEntradas(),
                "No debe registrar texto vacío");
    }

    @Test
    @DisplayName("Debe mostrar más reciente primero")
    void testOrdenMasRecientePrimero() {
        historial.registrar("PRIMERO");
        historial.registrar("SEGUNDO");
        List<HistorialManager.EntradaHistorial> lista =
                historial.obtenerHistorial();
        assertEquals("SEGUNDO", lista.get(0).getTextoOriginal(),
                "El más reciente debe ir primero");
    }
}