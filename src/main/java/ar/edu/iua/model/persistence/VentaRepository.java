package ar.edu.iua.model.persistence;

import ar.edu.iua.model.Ingrediente;
import ar.edu.iua.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByProductoListPrecioLista(double precioLista);

    List<Venta> findByFecha(Date fecha);

    List<Venta> findByFechaGreaterThanEqual(Date fecha);

    List<Venta> findByFechaLessThanEqual(Date fecha);
}
