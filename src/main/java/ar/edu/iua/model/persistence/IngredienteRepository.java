package ar.edu.iua.model.persistence;

import ar.edu.iua.model.Ingrediente;
import ar.edu.iua.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredienteRepository  extends JpaRepository<Ingrediente, Long> {

    List<Ingrediente> findByProductoListPrecioLista(double precioLista);

    @Query(value = "SELECT ingredientes.*\n" +
                   "FROM ingredientes\n" +
                   "   INNER JOIN producto_ingrediente_detalle ON ingredientes.id = producto_ingrediente_detalle.ingrediente_id\n" +
                   "   INNER JOIN productos ON producto_ingrediente_detalle.producto_id = productos.id where productos.precio_lista >= ?1\n" +
                   "GROUP BY ingredientes.id", nativeQuery = true)
    public List<Ingrediente> getIngredientesConPrecioProductoMayorA(Double precio);

}
