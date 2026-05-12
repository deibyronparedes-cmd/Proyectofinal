package model;

public class FraseSeña extends Seña {

    private final String frase;
    private final String contexto;

    public FraseSeña(String frase, String contexto) {
        super(
            "FRASE_" + frase.toUpperCase().replace(" ", "_"),
            frase,
            CategoriaSeña.FRASE
        );
        this.frase = frase;
        this.contexto = contexto;
    }

    @Override
    public String getDescripcion() {
        return "Frase completa: '" + frase + "'. Contexto: " + contexto;
    }

    @Override
    public String getTextoRepresentacion() {
        return frase;
    }

    public String getFrase() { return frase; }
    public String getContexto() { return contexto; }
}