package ar.edu.iua.business;

import ar.edu.iua.model.ProductoDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.business.exception.NotFoundException;
import ar.edu.iua.model.Producto;
import ar.edu.iua.model.persistence.ProductoRepository;

@Service
public class ProductoBusiness implements IProductoBusiness {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductoRepository productoDAO;

    @Override
    public Producto load(Long id) throws BusinessException, NotFoundException {
        Optional<Producto> op;
        try {
            op = productoDAO.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("No se encuentra el producto id=" + id);
        return op.get();
    }

    @Override
    public List<Producto> list() throws BusinessException {
        try {
            return productoDAO.findAll();
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public Producto save(Producto producto) throws BusinessException {
        try {
            return productoDAO.save(producto);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void delete(Long id) throws BusinessException, NotFoundException {
        try {
            productoDAO.deleteById(id);
        } catch (EmptyResultDataAccessException e1) {
            throw new NotFoundException("No se encuentra el producto id=" + id);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }


    @Override
    public Producto findByDescripcionContains(String descripcionProducto) throws BusinessException, NotFoundException {
        Optional<Producto> op = null;
        try {
            log.info("Getting by description");
            op = productoDAO.findByDescripcionContains(descripcionProducto);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("No se encuentra el producto con descripcion=" + descripcionProducto);
        return op.get();
    }

    @Override
    public List<Producto> findByPrecioListaAfter(double precioMayor) throws BusinessException, NotFoundException {
        Optional<List<Producto>> op = null;
        try {
            log.info("Getting by precio");
            op = productoDAO.findByPrecioListaAfter(precioMayor);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("No se encuentra el producto con precio mayor a " + precioMayor);
        return op.get();
    }

    @Override
    public List<Producto> findByIngredienteListDescripcionContains(String descripcion) throws BusinessException, NotFoundException {
        try {
            return productoDAO.findByIngredienteListDescripcionIngredienteContains(descripcion);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }

    }

    public Page<Producto> findAllPage(Pageable pageable) {
        return productoDAO.findAll(pageable);

    }

    @Override
    public void updateStockById(Long id, boolean enStock) {
        productoDAO.updateStockById(id, enStock);
    }

    @Override
    public Long updatePrecioListaByNombre(ProductoDTO productoDTO) {


        productoDAO.updatePrecioListaByNombre(productoDTO.getPrecioLista(), productoDTO.getNombre());
        Optional<Producto> p = productoDAO.findByNombre(productoDTO.getNombre());


        return p.get().getId();
    }

    @Override
    public Producto actualizarStockPorIdOrDescripcion(boolean stock, long id, String descripcion) throws BusinessException, NotFoundException {
        Optional<Producto> p = null;
        try {
            if (id == -1 && descripcion.equals("-1")) {
                throw new BusinessException();
            } else if (id != -1 && !descripcion.equals("-1")) {
                productoDAO.actualizarStockPorIdANDDescripcion(stock, id, descripcion);
                p = productoDAO.findById(id);
            } else if (id != -1 && descripcion.equals("-1")) {
                productoDAO.actualizarStockPorId(stock, id);
                p = productoDAO.findById(id);
            } else if (id == -1 && !descripcion.equals("-1")) {
                productoDAO.actualizarStockPorDescripcion(stock, descripcion);
                p = productoDAO.findByDescripcion(descripcion);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (!p.isPresent()) {
            throw new NotFoundException("No se encontro ningun producto cn el filtro especificado.");
        }
        return p.get();
    }

    @Override
    public Producto actualizarProductoConDTO(ProductoDTO productoDTO) throws BusinessException, NotFoundException {
        Optional<Producto> producto = null;
        System.out.println(productoDTO.getNombre() + " " + productoDTO.getDescripcion() + " " + productoDTO.getPrecioLista());
        try {
            producto = productoDAO.findByNombreAndDescripcion(productoDTO.getNombre(), productoDTO.getDescripcion());
            if(producto.get().getPrecioLista() != productoDTO.getPrecioLista()){
                producto.get().setPrecioLista(productoDTO.getPrecioLista());
                productoDAO.save(producto.get());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (!producto.isPresent()) {
            throw new NotFoundException("No se encontro ningun producto cn el filtro especificado.");
        }
        return producto.get();
    }

    @Override
    public List<Producto> findByPrecioLista(String precio) throws BusinessException, NotFoundException, NumberFormatException{
        Optional<List<Producto>> op = null;
        double numero = -1;
        try{
            numero = Double.parseDouble(precio);
        }catch (Exception e){
            throw new NumberFormatException("El precio ingresado es un String.");
        }
        try {
            if(numero >= 1000000){
                throw new BusinessException();
            }
            log.info("Getting by precio");
            op = productoDAO.findByPrecioLista(numero);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("No se encuentran productos con precio igual a: " + precio);
        return op.get();
    }
}
