package ar.edu.iua.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class ProveedorDTO implements Serializable {

    private static final long serialVersionUID = 1306850831764023629L;

    public ProveedorDTO(String nombre){
        this.nombre = "proveedor_" + nombre;
    }

    private String nombre;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
