package model;

import java.util.List;
import java.util.Optional;

public interface ISeñaRepositorio {

    
    Optional<Seña> buscarPorId(String id);

  
    List<Seña> buscarPorCategoria(CategoriaSeña categoria);

 
    List<Seña> buscarPorNombre(String texto);

   
    List<Seña> obtenerTodas();

    
    int contarTotal();
}