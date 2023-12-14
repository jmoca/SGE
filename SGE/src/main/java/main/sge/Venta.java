package main.sge;

import java.time.LocalDate;

public class Venta {
    private int id;
    private LocalDate fecha;
    private int cantidad;
    private int clienteId;
    private int proveedorId;
    private int productoId;
    private int pedidoId;

    public Venta(int id, LocalDate fecha, int cantidad, int clienteId, int proveedorId, int productoId, int pedidoId) {
        this.id = id;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.clienteId = clienteId;
        this.proveedorId = proveedorId;
        this.productoId = productoId;
        this.pedidoId = pedidoId;
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

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }
}
