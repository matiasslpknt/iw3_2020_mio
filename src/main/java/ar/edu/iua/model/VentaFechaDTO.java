package ar.edu.iua.model;

import java.io.Serializable;
import java.util.Date;

public class VentaFechaDTO implements Serializable {

    private static final long serialVersionUID = 7095972302169727700L;

    private Date fecha;

    public VentaFechaDTO(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
