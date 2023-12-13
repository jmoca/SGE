package main.sge;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrarProveedor {

    @FXML
    private TextField registrarProveedorNombre;

    @FXML
    private TextField registrarProveedorDireccion;

    @FXML
    private TextField registrarProveedoresContacto;

    @FXML
    private TextField registrarProveedorProductosSuministrados;

    @FXML
    private Button registrarProveedor;

    @FXML
    void registrarProveedor(ActionEvent event) {
        // Obtener los valores de los campos
        String nombre = registrarProveedorNombre.getText();
        String direccion = registrarProveedorDireccion.getText();
        String contacto = registrarProveedoresContacto.getText();
        String productosSumistrados = registrarProveedorProductosSuministrados.getText();

        try {
            // Convertir los valores a los tipos adecuados
            int productosSumistradosInt = Integer.parseInt(productosSumistrados);

            // Agregar el proveedor a la base de datos
            agregarProveedorABaseDeDatos(nombre, direccion, contacto, Integer.parseInt(productosSumistrados));

            // Limpiar los campos después de registrar el proveedor
            limpiarCampos();


        } catch (NumberFormatException e) {
            System.err.println("Error al convertir los valores. Asegúrate de ingresar números válidos.");
        }
    }

    private void agregarProveedorABaseDeDatos(String nombre, String direccion, String contacto, int productosSumistrados) {
        try (Connection connection = ConexionPool.obtenerConexion()) {
            // Preparar la consulta SQL
            String sql = "INSERT INTO proveedores (nombre, direccion, contacto, productosSuministrados) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nombre);
                statement.setString(2, direccion);
                statement.setString(3, contacto);
                statement.setInt(4, productosSumistrados);

                // Ejecutar la consulta
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar el proveedor a la base de datos: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        registrarProveedorNombre.clear();
        registrarProveedorDireccion.clear();
        registrarProveedoresContacto.clear();
        registrarProveedorProductosSuministrados.clear();
    }
}
