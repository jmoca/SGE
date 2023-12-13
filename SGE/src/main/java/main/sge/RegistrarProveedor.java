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
    private TextField registrarProveedorPrecio;

    @FXML
    private TextField registrarProveedorDescripcion;

    @FXML
    private TextField registrarProveedorStock;

    @FXML
    private Button registrarProveedor;

    @FXML
    void registrarProveedor(ActionEvent event) {
        // Obtener los valores de los campos
        String nombre = registrarProveedorNombre.getText();
        String precioStr = registrarProveedorPrecio.getText();
        String descripcion = registrarProveedorDescripcion.getText();
        String stockStr = registrarProveedorStock.getText();

        try {
            // Convertir los valores a los tipos adecuados
            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);

            // Agregar el proveedor a la base de datos
            agregarProveedorABaseDeDatos(nombre, descripcion, precio, stock);

            // Limpiar los campos después de registrar el proveedor
            limpiarCampos();

            System.out.println("Proveedor registrado y agregado a la base de datos");
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir los valores. Asegúrate de ingresar números válidos.");
        }
    }

    private void agregarProveedorABaseDeDatos(String nombre, String descripcion, double precio, int stock) {
        try (Connection connection = ConexionPool.obtenerConexion()) {
            // Preparar la consulta SQL
            String sql = "INSERT INTO proveedores (nombre, descripcion, precio, cantidadEnStock) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setDouble(3, precio);
                statement.setInt(4, stock);

                // Ejecutar la consulta
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar el proveedor a la base de datos: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        registrarProveedorNombre.clear();
        registrarProveedorPrecio.clear();
        registrarProveedorDescripcion.clear();
        registrarProveedorStock.clear();
    }
}
