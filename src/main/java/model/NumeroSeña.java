package model;

public class NumeroSeña extends Seña {

    private final int numero;

    public NumeroSeña(int numero) {
        super(
            "NUMERO_" + numero,
            String.valueOf(numero),
            CategoriaSeña.NUMERO
        );
        if (numero < 0 || numero > 9) {
            throw new IllegalArgumentException("Solo se aceptan dígitos del 0 al 9");
        }
        this.numero = numero;
    }

    @Override
    public String getDescripcion() {
        return "Seña para el número " + numero + " en lengua de señas colombiana";
    }

    @Override
    public String getTextoRepresentacion() {
        return String.valueOf(numero);
    }

    public int getNumero() { return numero; }
}