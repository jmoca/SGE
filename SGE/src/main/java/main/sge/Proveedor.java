package main.sge;

public class Proveedor {
    private int id;
    private String nombre;
    private String direccion;
    private String contacto;
    private String productosSuministrados;
    private int productoId;

    public Proveedor(int id, String nombre, String direccion, String contacto, String productosSuministrados, int productoId) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.contacto = contacto;
        this.productosSuministrados = productosSuministrados;
        this.productoId = productoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getProductosSuministrados() {
        return productosSuministrados;
    }

    public void setProductosSuministrados(String productosSuministrados) {
        this.productosSuministrados = productosSuministrados;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    // Puedes agregar más atributos y métodos según tus necesidades
}
