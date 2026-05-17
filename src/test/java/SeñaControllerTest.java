package com.mycompany.proyectofinal;

import controller.SeñaController;
import model.*;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del SeñaController.
 */
class SeñaControllerTest {

    private SeñaController controller;

    @BeforeEach
    void setUp() {
        controller = new SeñaController();
    }

    @Test
    @DisplayName("Debe retornar todas las señas disponibles")
    void testObtenerTodas() {
        List<Seña> señas = controller.obtenerTodas();
        assertNotNull(señas, "La lista no debe ser null");
        assertFalse(señas.isEmpty(), "Debe haber señas cargadas");
        assertTrue(señas.size() >= 36, "Debe haber al menos 36 señas (26 letras + 10 números)");
    }

    @Test
    @DisplayName("Debe filtrar correctamente por categoría LETRA")
    void testFiltrarPorCategoriaLetra() {
        List<Seña> letras = controller.filtrarPorCategoria(CategoriaSeña.LETRA);
        assertNotNull(letras);
        assertEquals(26, letras.size(), "Deben ser exactamente 26 letras");
        letras.forEach(s -> assertEquals(CategoriaSeña.LETRA, s.getCategoria()));
    }

    @Test
    @DisplayName("Debe filtrar correctamente por categoría NUMERO")
    void testFiltrarPorCategoriaNumero() {
        List<Seña> numeros = controller.filtrarPorCategoria(CategoriaSeña.NUMERO);
        assertNotNull(numeros);
        assertEquals(10, numeros.size(), "Deben ser exactamente 10 números");
        numeros.forEach(s -> assertEquals(CategoriaSeña.NUMERO, s.getCategoria()));
    }

    @Test
    @DisplayName("Debe buscar señas por nombre correctamente")
    void testBuscarPorNombre() {
        List<Seña> resultado = controller.buscar("A");
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty(), "Debe encontrar al menos una seña con 'A'");
    }

    @Test
    @DisplayName("Debe buscar por nombre y categoría simultáneamente")
    void testBuscarPorNombreYCategoria() {
        List<Seña> resultado = controller.buscar("A", CategoriaSeña.LETRA);
        assertNotNull(resultado);
        resultado.forEach(s -> assertEquals(CategoriaSeña.LETRA, s.getCategoria()));
    }

    @Test
    @DisplayName("Debe traducir texto correctamente")
    void testTraducir() {
        List<Seña> señas = controller.traducir("ABC");
        assertNotNull(señas);
        assertEquals(3, señas.size(), "Debe traducir 3 letras");
    }

    @Test
    @DisplayName("Debe traducir con límite correctamente")
    void testTraducirConLimite() {
        List<Seña> señas = controller.traducir("ABCDE", 3);
        assertNotNull(señas);
        assertTrue(señas.size() <= 3, "No debe superar el límite");
    }

    @Test
    @DisplayName("Debe lanzar excepción con texto vacío")
    void testTraducirTextoVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.traducir("");
        }, "Debe lanzar excepción con texto vacío");
    }

    @Test
    @DisplayName("Debe lanzar excepción con texto null")
    void testTraducirTextoNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.traducir(null);
        }, "Debe lanzar excepción con texto null");
    }

    @Test
    @DisplayName("Debe registrar traducciones en el historial")
    void testHistorial() {
        controller.traducir("ABC");
        assertFalse(controller.obtenerHistorial().isEmpty(),
                "El historial debe tener entradas");
    }

    @Test
    @DisplayName("Debe limpiar el historial correctamente")
    void testLimpiarHistorial() {
        controller.traducir("ABC");
        controller.limpiarHistorial();
        assertTrue(controller.obtenerHistorial().isEmpty(),
                "El historial debe estar vacío después de limpiar");
    }

    @Test
    @DisplayName("Debe retornar el total correcto de señas")
    void testTotalSeñas() {
        int total = controller.totalSeñas();
        assertTrue(total >= 36, "Debe haber al menos 36 señas");
    }
}