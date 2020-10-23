package ar.edu.iua.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ventas")
@NamedNativeQuery(name = "Venta.getFechaVentasConProductoNombre", query = "" +
        "SELECT ventas.fecha " +
        "FROM ventas " +
        "         INNER JOIN producto_venta_detalle ON ventas.id = producto_venta_detalle.venta_id " +
        "         INNER JOIN productos ON producto_venta_detalle.producto_id = productos.id " +
        "WHERE productos.nombre = ?1", resultSetMapping = "ventamap")
@SqlResultSetMapping(
        name="ventamap",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "ventas.fecha", type = Date.class),
                        },
                        targetClass = VentaFechaDTO.class
                )
        }
)
public class Venta implements Serializable {

    private static final long serialVersionUID = 8608301282343428793L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "producto_venta_detalle",
            joinColumns = @JoinColumn(name = "venta_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "id"))
//    @ManyToMany(targetEntity = Producto.class, mappedBy = "ventaList")
//    @JsonBackReference
    private List<Producto> productoList;

    private Date fecha;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }
}
