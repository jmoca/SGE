package main.sge;

import java.util.ArrayList;

public class Cliente {
    private String nombre;
    private String direccion;
    private String contacto;
    private ArrayList<Venta> historialCompras;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public ArrayList<Venta> getHistorialCompras() {
        return historialCompras;
    }

    public void setHistorialCompras(ArrayList<Venta> historialCompras) {
        this.historialCompras = historialCompras;
    }
}
