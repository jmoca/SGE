package main.sge;

public class Producto {
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidadEnStock;

    private int id;

    public Producto(int id, String nombre, String descripcion, double precio, int cantidadEnStock) {
        this.id=id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadEnStock = cantidadEnStock;
    }
    public int getId (){
        return id;
    }
    public void setId (int id){
        this.id = id;
    }
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return cantidadEnStock;
    }
    public int getCantidadEnStock(){
        return cantidadEnStock;
    }
    public void setCantidadEnStock(int cantidadEnStock) {
        this.cantidadEnStock = cantidadEnStock;
    }

}
