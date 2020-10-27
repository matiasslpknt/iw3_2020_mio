package ar.edu.iua.model.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import ar.edu.iua.model.Producto;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByDescripcion(String descripcionProducto);

    Optional<Producto> findByNombre(String nombre);

    Optional<Producto> findByDescripcionContains(String descripcionProducto);

    Optional<List<Producto>> findByPrecioListaAfter(double precio);

    Optional<List<Producto>> findByPrecioLista(double precio);

    Optional<Producto> findById(long id);

    List<Producto> findByIngredienteListDescripcionIngredienteContains(String descripcionIngrediente);

    Page<Producto> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update productos prod set prod.en_stock=:enStock where prod.id=:id", nativeQuery =  true)
    void updateStockById(@Param("id")Long id, @Param("enStock")boolean enStock);

    @Modifying
    @Transactional
    @Query(value = "update productos prod set prod.precio_lista=:precioLista where prod.nombre=:nombre", nativeQuery =  true)
    void updatePrecioListaByNombre(@Param("precioLista")double precio, @Param("nombre")String nombre);

    @Modifying
    @Transactional
    @Query(value = "UPDATE productos p SET p.en_stock = ?1 WHERE p.id = ?2 AND p.descripcion = ?3", nativeQuery = true)
    void actualizarStockPorIdANDDescripcion(boolean enStock, long id, String descripcion);

    @Modifying
    @Transactional
    @Query(value = "UPDATE productos p SET p.en_stock = ?1 WHERE p.id = ?2", nativeQuery = true)
    void actualizarStockPorId(boolean enStock, long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE productos p SET p.en_stock = ?1 WHERE p.descripcion = ?2", nativeQuery = true)
    void actualizarStockPorDescripcion(boolean enStock, String descripcion);

    Producto findByEnStockAndIdAndDescripcion(boolean enStock, long id, String descripcion);

    Producto findByEnStockAndId(boolean enStock, long id);

    Producto findByEnStockAndDescripcion(boolean enStock, String descripcion);

    Optional<Producto> findByNombreAndDescripcion(String nombre, String Descripcion);
}
