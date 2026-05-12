package model;

public class LetraSeña extends Seña {

    private final char letra;

    public LetraSeña(char letra) {
        super(
            "LETRA_" + Character.toUpperCase(letra),
            String.valueOf(Character.toUpperCase(letra)),
            CategoriaSeña.LETRA
        );
        this.letra = Character.toUpperCase(letra);
    }

    @Override
    public String getDescripcion() {
        return "Seña para la letra '" + letra + "' en lengua de señas colombiana";
    }

    @Override
    public String getTextoRepresentacion() {
        return String.valueOf(letra);
    }

    public char getLetra() { return letra; }
}