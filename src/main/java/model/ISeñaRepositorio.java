package model;

import java.util.List;
import java.util.Optional;

public interface ISeñaRepositorio {

    // Obtener por id exacto
    Optional<Seña> buscarPorId(String id);

    // Obtener todas las señas de una categoría
    List<Seña> buscarPorCategoria(CategoriaSeña categoria);

    // Búsqueda por nombre (parcial, case-insensitive)
    List<Seña> buscarPorNombre(String texto);

    // Todas las señas disponibles
    List<Seña> obtenerTodas();

    // Total de señas cargadas
    int contarTotal();
}