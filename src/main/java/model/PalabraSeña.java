package model;

public class PalabraSeña extends Seña {

    private final String palabra;
    private final String definicion;

    public PalabraSeña(String palabra, String definicion) {
        super(
            "PALABRA_" + palabra.toUpperCase(),
            palabra,
            CategoriaSeña.PALABRA
        );
        this.palabra = palabra;
        this.definicion = definicion;
    }

    @Override
    public String getDescripcion() {
        return "Seña para la palabra '" + palabra + "': " + definicion;
    }

    @Override
    public String getTextoRepresentacion() {
        return palabra;
    }

    public String getPalabra() { return palabra; }
    public String getDefinicion() { return definicion; }
}