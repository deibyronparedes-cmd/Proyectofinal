package model;

import java.awt.Image;

public abstract class Seña {

    private final String id;
    private final String nombre;
    private final CategoriaSeña categoria;
    private Image imagen;

    public Seña(String id, String nombre, CategoriaSeña categoria) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
    }

 
    public abstract String getDescripcion();

   
    public abstract String getTextoRepresentacion();

   
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public CategoriaSeña getCategoria() { return categoria; }
    public Image getImagen() { return imagen; }
    public void setImagen(Image imagen) { this.imagen = imagen; }

    @Override
    public String toString() {
        return nombre + " [" + categoria + "]";
    }
}