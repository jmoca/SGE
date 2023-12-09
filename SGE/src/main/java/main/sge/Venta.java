package main.sge;

import java.time.LocalDate;
import java.util.List;

public class Venta {
    private final LocalDate fecha;
    private final List<Producto> productos;
    private double total;

    public Venta(LocalDate fecha, List<Producto> productos) {
        this.fecha = fecha;
        this.productos = productos;
        this.calcularTotal();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public double getTotal() {
        return total;
    }

    private void calcularTotal() {
        this.total = productos.stream().mapToDouble(Producto::getPrecio).sum();
    }
}
