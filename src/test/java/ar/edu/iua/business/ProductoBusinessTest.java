package ar.edu.iua.business;

import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.business.exception.NotFoundException;
import ar.edu.iua.model.Producto;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductoBusinessTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductoBusiness productoBusiness;


    @Rule
    public ExpectedException expectedEx = ExpectedException.none();


    @Test
    public void testLoadSuccess() throws BusinessException, NotFoundException {
        long id = 1;
        assertEquals("Fideos Feos", productoBusiness.load(id).getDescripcion());
    }

    @Test
    public void testLoadFailure() throws BusinessException, NotFoundException {
        long id = 1;
        assertNotEquals("Descripcion distinta", productoBusiness.load(id).getDescripcion());
    }


    @Test(expected = NotFoundException.class)
    public void testLoadNotFoundException() throws BusinessException, NotFoundException {
        long id = 128;
        productoBusiness.load(id);
        expectedEx.expect(NotFoundException.class);
        expectedEx.expectMessage("No se encuentra el producto id=" + id);
    }

    /*
        @Test(expected = ar.edu.iua.business.exception.BusinessException.class)
        public void testFailureLoadNotFoundException() throws  BusinessException, NotFoundException  {
            long id = 128;
            productoBusiness.load(id);
            expectedEx.expect(ar.edu.iua.business.exception.BusinessException.class);
            expectedEx.expectMessage("No se encuentra el producto con id="+id);
        }
    */

    @Test
    public void testListSuccess() throws BusinessException {
        assertEquals(5, productoBusiness.list().size());
    }

    @Test
    public void testfindByDescripcionContainsSuccess() throws BusinessException, NotFoundException {
        Producto p = productoBusiness.load((long) 2);
        assertEquals(p.getId(), productoBusiness.findByDescripcionContains("carame").getId());
    }

    @Test
    public void testfindByDescripcionContainsFailure() throws BusinessException, NotFoundException {
        Producto p = productoBusiness.load((long) 2);
        assertNotEquals(p.getId(), productoBusiness.findByDescripcionContains("sugus").getId());
    }

    @Test
    public void testfindByPrecioListaAfterSuccess() throws BusinessException, NotFoundException {
        boolean bandera = true;
        List<Producto> ps = productoBusiness.findByPrecioListaAfter(14.0);
        for(Producto p : ps){
            if(p.getPrecioLista() < 14){
                bandera = false;
                break;
            }
        }
        assertEquals(true, bandera);
    }

    @Test(expected = BusinessException.class)
    public void testfindByPrecioBusinessException() throws BusinessException, NotFoundException {
        String precio = "1000000";
        productoBusiness.findByPrecioLista(precio);
        expectedEx.expect(BusinessException.class);
        expectedEx.expectMessage("No se encuentran productos con precio igual a: " + precio);
    }

    @Test(expected = NumberFormatException.class)
    public void testfindByPrecioNumberFormatException() throws BusinessException, NotFoundException {
        String precio = "abc";
        productoBusiness.findByPrecioLista(precio);
        expectedEx.expect(NumberFormatException .class);
        expectedEx.expectMessage("El precio ingresado es un String.");
    }
}
