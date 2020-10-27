package ar.edu.iua.business;

import ar.edu.iua.model.ProductoDTO;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.business.exception.NotFoundException;
import ar.edu.iua.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface IProductoBusiness {

	public Producto load(Long id) throws BusinessException, NotFoundException;

	public List<Producto> list() throws BusinessException;

	public Producto save(Producto producto) throws BusinessException;

	public void delete(Long id) throws BusinessException, NotFoundException;

 //   public Producto findByDescripcion(String descripcionProducto) throws BusinessException, NotFoundException;

    public Producto findByDescripcionContains(String descripcionProducto) throws BusinessException, NotFoundException;

    public List<Producto> findByPrecioListaAfter(double precio) throws BusinessException, NotFoundException;

    public List<Producto> findByIngredienteListDescripcionContains(String descripcion) throws BusinessException, NotFoundException;

    public Page<Producto> findAllPage(Pageable pageable);

    void updateStockById(Long id, boolean enStock);

    public Long updatePrecioListaByNombre(ProductoDTO productoDTO);

    public Producto actualizarStockPorIdOrDescripcion(boolean stock, long id, String descripcion) throws BusinessException, NotFoundException;

    public Producto actualizarProductoConDTO(ProductoDTO producto) throws BusinessException, NotFoundException;

    public List<Producto> findByPrecioLista(String precio) throws BusinessException, NotFoundException, NumberFormatException;
}
