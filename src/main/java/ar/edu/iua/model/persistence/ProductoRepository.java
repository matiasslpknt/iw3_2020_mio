package ar.edu.iua.model.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.iua.model.Producto;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByDescripcion(String descripcionProducto);

    Optional<Producto> findByDescripcionContains(String descripcionProducto);

    Optional<Producto> findByPrecioListaAfter(double precio);

    List<Producto> findByIngredienteListDescripcionIngredienteContains(String descripcionIngrediente);

    Page<Producto> findAll(Pageable pageable);

    @Query(value = "UPDATE productos SET en_stock = ?1 WHERE productos.id = ?2 AND productos.descripcion = ?3" , nativeQuery = true)
    public void actualizarStockPorIdANDDescripcion(boolean enStock, long id, String descripcion);

    @Query(value = "UPDATE productos SET en_stock = ?1 WHERE productos.id = ?2" , nativeQuery = true)
    public void actualizarStockPorId(boolean enStock, long id);

    @Query(value = "UPDATE productos SET en_stock = ?1 WHERE productos.descripcion = ?2" , nativeQuery = true)
    public void actualizarStockPorDescripcion(boolean enStock, String descripcion);

    public Producto findByEnStockAndIdAndDescripcion(boolean enStock, long id, String descripcion);

    public Producto findByEnStockAndId(boolean enStock, long id);

    public Producto findByEnStockAndDescripcion(boolean enStock, String descripcion);
}
