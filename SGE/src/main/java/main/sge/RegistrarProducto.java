package main.sge;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrarProducto {

    @FXML
    private TextField registrarProdNombre;

    @FXML
    private TextField registrarProdPrecio;

    @FXML
    private TextField registrarProdDescripcion;

    @FXML
    private TextField registrarProdStock;

    @FXML
    private Button registrarProducto;

    @FXML
    void registrarProducto(ActionEvent event) {
        // Obtener los valores de los campos
        String nombre = registrarProdNombre.getText();
        String precioStr = registrarProdPrecio.getText();
        String descripcion = registrarProdDescripcion.getText();
        String stockStr = registrarProdStock.getText();

        try {
            // Convertir los valores a los tipos adecuados
            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);

            // Agregar el producto a la base de datos
            agregarProductoABaseDeDatos(nombre, descripcion, precio, stock);

            // Limpiar los campos después de registrar el producto
            limpiarCampos();

            System.out.println("Producto registrado y agregado a la base de datos");
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir los valores. Asegúrate de ingresar números válidos.");
        }
    }

    private void agregarProductoABaseDeDatos(String nombre, String descripcion, double precio, int stock) {
        try (Connection connection = ConexionPool.obtenerConexion()) {
            // Preparar la consulta SQL
            String sql = "INSERT INTO productos (nombre, descripcion, precio, cantidadEnStock) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setDouble(3, precio);
                statement.setInt(4, stock);

                // Ejecutar la consulta
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar el producto a la base de datos: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        registrarProdNombre.clear();
        registrarProdPrecio.clear();
        registrarProdDescripcion.clear();
        registrarProdStock.clear();
    }
}
