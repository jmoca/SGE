package main.sge;

import java.time.LocalDate;

public class Venta {
    private int id;
    private LocalDate fecha;
    private int cantidad;
    private Cliente cliente;

    private Producto producto;


    public Venta(int id, LocalDate fecha, int cantidad, Cliente cliente, Producto producto) {
        this.id = id;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.cliente = cliente;

        this.producto = producto;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }


}

    // Puedes agregar más atributos y métodos según tus necesidades

