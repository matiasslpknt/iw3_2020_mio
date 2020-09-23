package ar.edu.iua.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="producto_detalle")
public class ProductoDetalle implements Serializable {

    private static final long serialVersionUID = 1811109098957123066L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 150)
    private String fabrica;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFabrica() {
        return fabrica;
    }
    public void setFabrica(String fabrica) {
        this.fabrica = fabrica;
    }

}