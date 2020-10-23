package ar.edu.iua.business;

import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.business.exception.NotFoundException;
import ar.edu.iua.model.Ingrediente;
import ar.edu.iua.model.Producto;
import ar.edu.iua.model.Venta;
import ar.edu.iua.model.VentaFechaDTO;
import ar.edu.iua.model.persistence.ProductoRepository;
import ar.edu.iua.model.persistence.VentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VentaBusiness implements IVentaBusiness {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private VentaRepository ventaDAO;

    @Override
    public Venta load(Long id) throws BusinessException, NotFoundException {
        Optional<Venta> op;
        try {
            op = ventaDAO.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("No se encuentra la venta id = " + id);
        return op.get();
    }

    @Override
    public List<Venta> list() throws BusinessException {
        try {
            return ventaDAO.findAll();
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public Venta save(Venta producto) throws BusinessException {
        try {
            return ventaDAO.save(producto);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void delete(Long id) throws BusinessException, NotFoundException {
        try {
            ventaDAO.deleteById(id);
        } catch (EmptyResultDataAccessException e1) {
            throw new NotFoundException("No se encuentra la venta id = " + id);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    public Page<Venta> findAllPage(Pageable pageable) {
        return ventaDAO.findAll(pageable);

    }

    @Override
    public List<Venta> findByProductoListPrecioLista(Double precio) throws BusinessException, NotFoundException {
        List<Venta> ventas = null;
        try {
            ventas = ventaDAO.findByProductoListPrecioLista(precio);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (ventas.isEmpty()) {
            throw new NotFoundException("No se encontraron productos con los parametros especificados.");
        }
        return ventas;
    }

    @Override
    public List<Venta> findByFecha(String fecha) throws BusinessException, NotFoundException {
        List<Venta> ventas = null;
        try {
            fecha += "T00:00:00.000+00:00";
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(fecha);
            log.info(date.toString());
            ventas = ventaDAO.findByFecha(date);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (ventas.isEmpty()) {
            throw new NotFoundException("No se encontraron productos con los parametros especificados.");
        }
        return ventas;
    }

    @Override
    public List<Venta> loadByFechaMayorOMenor(String fecha, String modo) throws BusinessException, NotFoundException {
        List<Venta> ventas = null;
        try {
            fecha += "T00:00:00.000+00:00";
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(fecha);
            log.info(date.toString());
            if (modo.equals("mayor")) {
                ventas = ventaDAO.findByFechaGreaterThanEqual(date);
            } else if (modo.equals("menor")) {
                ventas = ventaDAO.findByFechaLessThanEqual(date);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (ventas.isEmpty()) {
            throw new NotFoundException("No se encontraron productos con los parametros especificados.");
        }
        return ventas;
    }

    @Override
    public List<VentaFechaDTO> getFechaVentasConProductoNombre(String nombre) throws BusinessException, NotFoundException {
        List<VentaFechaDTO> ventas = null;
        try {
            ventas = ventaDAO.getFechaVentasConProductoNombre(nombre);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (ventas.isEmpty()) {
            throw new NotFoundException("No se encontraron ventas con productos cuyo nombre sea igual a: " + nombre);
        }
        return ventas;
    }
}
