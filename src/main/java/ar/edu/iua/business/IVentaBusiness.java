package ar.edu.iua.business;

import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.business.exception.NotFoundException;
import ar.edu.iua.model.Producto;
import ar.edu.iua.model.Venta;
import ar.edu.iua.model.VentaFechaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface IVentaBusiness {

	public Venta load(Long id) throws BusinessException, NotFoundException;

	public List<Venta> list() throws BusinessException;

	public Venta save(Venta venta) throws BusinessException;

	public void delete(Long id) throws BusinessException, NotFoundException;

 //   public Venta findByDescripcion(String descripcionVenta) throws BusinessException, NotFoundException;

    public Page<Venta> findAllPage(Pageable pageable);

	public List<Venta>  findByProductoListPrecioLista(Double precio) throws BusinessException, NotFoundException;

	public List<Venta>  findByFecha(String fecha) throws BusinessException, NotFoundException;

	public List<Venta> loadByFechaMayorOMenor(String fecha, String modo) throws BusinessException, NotFoundException;

	public List<VentaFechaDTO> getFechaVentasConProductoNombre(String nombre) throws BusinessException, NotFoundException;
}
