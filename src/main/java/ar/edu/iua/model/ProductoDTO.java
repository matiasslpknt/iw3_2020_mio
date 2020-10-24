package ar.edu.iua.model;

import java.io.Serializable;


public class ProductoDTO implements Serializable {


    private String nombre;
    private String descripcion;
    private double precioLista;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioLista() {
        return precioLista;
    }

    public void setPrecioLista(double precioLista) {
        this.precioLista = precioLista;
    }

    public ProductoDTO(String nombre, String descripcion, double precioLista) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioLista = precioLista;
    }
}
