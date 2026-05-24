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

    // ── Carga de datos 

    
    private void cargarLetras() {
        for (char c = 'A'; c <= 'Z'; c++) {
            LetraSeña seña = new LetraSeña(c);
            seña.setImagen(cargarImagenConFallback("letras", String.valueOf(c)));
            catalogo.put(seña.getId(), seña);
        }
    }

    private void cargarNumeros() {
        for (int i = 0; i <= 9; i++) {
            NumeroSeña seña = new NumeroSeña(i);
            seña.setImagen(cargarImagenConFallback("numeros", String.valueOf(i)));
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
            String archivo = d[0].toLowerCase().replace(" ", "_");
            seña.setImagen(cargarImagenConFallback("palabras", archivo));
            catalogo.put(seña.getId(), seña);
        }
    }

    private void cargarFrases() {
    String[][] datos = {
        {"Buenos días",        "Saludo matutino",                "buenos_dias"},
        {"Buenas tardes",      "Saludo vespertino",              "buenas_tardes"},
        {"Buenas noches",      "Saludo nocturno",                "buenas_noches"},
        {"¿Cómo estás?",       "Pregunta de bienestar",          "como_estas"},
        {"Mucho gusto",        "Presentación formal",            "mucho_gusto"},
        {"No entiendo",        "Expresar confusión",             "no_entiendo"},
        {"Habla más despacio", "Pedir que reduzcan la velocidad","habla_mas_despacio"},
        {"Necesito ayuda",     "Solicitar asistencia urgente",   "necesito_ayuda"},
        {"¿Cuánto cuesta?",    "Preguntar el precio de algo",    "cuanto_cuesta"},
        {"Me llamo",           "Presentarse con el nombre",      "me_llamo"}
    };

    for (String[] d : datos) {
        FraseSeña seña = new FraseSeña(d[0], d[1]);
        seña.setImagen(cargarImagenConFallback("frases", d[2]));
        catalogo.put(seña.getId(), seña);
    }
}

    // ── Carga de imagen

    
   private Image cargarImagenConFallback(String carpeta, String nombre) {
    String[] extensiones = {".png", ".jpg", ".jpeg"};
    for (String ext : extensiones) {
        String ruta = "/imagenes/" + carpeta + "/" + nombre + ext;
        System.out.println("Buscando imagen: " + ruta);
        Image img = cargarImagen(ruta);
        if (img != null) {
            System.out.println("Imagen encontrada: " + ruta);
            return img;
        }
    }
    System.out.println("No se encontró imagen para: " + carpeta + "/" + nombre);
    return null;
}
   
    private Image cargarImagen(String ruta) {
        try {
            java.net.URL url = getClass().getResource(ruta);
            if (url != null) {
                return new ImageIcon(url).getImage();
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar imagen: " + ruta);
        }
        return null;
    }

    // ── Implementación de ISeñaRepositorio 

    
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