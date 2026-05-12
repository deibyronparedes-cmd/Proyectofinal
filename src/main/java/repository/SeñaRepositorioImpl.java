package repository;

import model.*;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;

public class SeñaRepositorioImpl implements ISeñaRepositorio {

    private final Map<String, Seña> catalogo = new LinkedHashMap<>();

    public SeñaRepositorioImpl() {
        cargarLetras();
        cargarNumeros();
        cargarPalabras();
        cargarFrases();
    }

    // ── Carga de datos ─────────────────────────────────────────────────────────

    private void cargarLetras() {
        for (char c = 'A'; c <= 'Z'; c++) {
            LetraSeña seña = new LetraSeña(c);
            seña.setImagen(cargarImagen("/imagenes/letras/" + c + ".png"));
            catalogo.put(seña.getId(), seña);
        }
    }

    private void cargarNumeros() {
        for (int i = 0; i <= 9; i++) {
            NumeroSeña seña = new NumeroSeña(i);
            seña.setImagen(cargarImagen("/imagenes/numeros/" + i + ".png"));
            catalogo.put(seña.getId(), seña);
        }
    }

    private void cargarPalabras() {
        String[][] datos = {
            {"Hola",        "Saludo informal cotidiano"},
            {"Gracias",     "Expresión de agradecimiento"},
            {"Por favor",   "Petición con cortesía"},
            {"Sí",          "Afirmación"},
            {"No",          "Negación"},
            {"Ayuda",       "Solicitar asistencia"},
            {"Agua",        "Líquido vital"},
            {"Comer",       "Acción de alimentarse"},
            {"Casa",        "Lugar de vivienda"},
            {"Familia",     "Grupo de personas unidas"},
            {"Amigo",       "Persona de confianza"},
            {"Trabajo",     "Actividad laboral"},
            {"Escuela",     "Centro de aprendizaje"},
            {"Baño",        "Solicitar ir al servicio"},
            {"Dolor",       "Expresar malestar físico"}
        };

        for (String[] d : datos) {
            PalabraSeña seña = new PalabraSeña(d[0], d[1]);
            seña.setImagen(cargarImagen("/imagenes/palabras/" + d[0].toLowerCase() + ".png"));
            catalogo.put(seña.getId(), seña);
        }
    }

    private void cargarFrases() {
        String[][] datos = {
            {"Buenos días",         "Saludo matutino"},
            {"Buenas tardes",       "Saludo vespertino"},
            {"Buenas noches",       "Saludo nocturno"},
            {"¿Cómo estás?",        "Pregunta de bienestar"},
            {"Mucho gusto",         "Presentación formal"},
            {"No entiendo",         "Expresar confusión"},
            {"Habla más despacio",  "Pedir que reduzcan la velocidad"},
            {"Necesito ayuda",      "Solicitar asistencia urgente"},
            {"¿Cuánto cuesta?",     "Preguntar el precio de algo"},
            {"Me llamo",            "Presentarse con el nombre propio"}
        };

        for (String[] d : datos) {
            FraseSeña seña = new FraseSeña(d[0], d[1]);
            String archivo = d[0].toLowerCase()
                               .replace(" ", "_")
                               .replace("¿", "")
                               .replace("?", "");
            seña.setImagen(cargarImagen("/imagenes/frases/" + archivo + ".png"));
            catalogo.put(seña.getId(), seña);
        }
    }

    // ── Carga de imagen desde recursos ─────────────────────────────────────────

    private Image cargarImagen(String ruta) {
        try {
            java.net.URL url = getClass().getResource(ruta);
            if (url != null) {
                return new ImageIcon(url).getImage();
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar imagen: " + ruta);
        }
        return null; // la vista mostrará un placeholder
    }

    // ── Implementación de ISeñaRepositorio ─────────────────────────────────────

    @Override
    public Optional<Seña> buscarPorId(String id) {
        return Optional.ofNullable(catalogo.get(id));
    }

    @Override
    public List<Seña> buscarPorCategoria(CategoriaSeña categoria) {
        return catalogo.values().stream()
                .filter(s -> s.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    @Override
    public List<Seña> buscarPorNombre(String texto) {
        if (texto == null || texto.isBlank()) {
            return obtenerTodas();
        }
        String filtro = texto.toLowerCase().trim();
        return catalogo.values().stream()
                .filter(s -> s.getNombre().toLowerCase().contains(filtro))
                .collect(Collectors.toList());
    }

    @Override
    public List<Seña> obtenerTodas() {
        return Collections.unmodifiableList(new ArrayList<>(catalogo.values()));
    }

    @Override
    public int contarTotal() {
        return catalogo.size();
    }
}