package ar.edu.iua.model.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.iua.model.Producto;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByDescripcion(String descripcionProducto);

    Optional<Producto> findByDescripcionContains(String descripcionProducto);

    Optional<Producto> findByPrecioListaAfter(double precio);

    Optional<Producto> findById(long id);

    List<Producto> findByIngredienteListDescripcionIngredienteContains(String descripcionIngrediente);

    Page<Producto> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE productos p SET p.en_stock = ?1 WHERE p.id = ?2 AND p.descripcion = ?3" , nativeQuery = true)
    void actualizarStockPorIdANDDescripcion(boolean enStock, long id, String descripcion);
    @Modifying
    @Transactional
    @Query(value = "UPDATE productos p SET p.en_stock = ?1 WHERE p.id = ?2" , nativeQuery = true)
    void actualizarStockPorId(boolean enStock, long id);
    @Modifying
    @Transactional
    @Query(value = "UPDATE productos p SET p.en_stock = ?1 WHERE p.descripcion = ?2" , nativeQuery = true)
    void actualizarStockPorDescripcion(boolean enStock, String descripcion);

    public Producto findByEnStockAndIdAndDescripcion(boolean enStock, long id, String descripcion);

    public Producto findByEnStockAndId(boolean enStock, long id);

    public Producto findByEnStockAndDescripcion(boolean enStock, String descripcion);
}
