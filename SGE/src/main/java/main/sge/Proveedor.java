package main.sge;

public class Proveedor {
    private String nombre;
    private String direccion;
    private String contacto;
    private int productosSuministrados;

    public Proveedor(String nombre, String direccion, String contacto, int productosSuministrados) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.contacto = contacto;
        this.productosSuministrados = productosSuministrados;
    }

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

    public int getProductosSuministrados() {
        return productosSuministrados;
    }

    public void setProductosSuministrados(int productosSuministrados) {
        this.productosSuministrados = productosSuministrados;
    }
}
