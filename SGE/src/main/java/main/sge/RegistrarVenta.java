package main.sge;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class RegistrarVenta {

    @FXML
    private DatePicker datePickerFecha;

    @FXML
    private TextField textFieldCantidad;

    @FXML
    private TextField textFieldClienteID;

    @FXML
    private TextField textFieldProveedorID;

    @FXML
    private TextField textFieldProductosID;

    @FXML
    private TextField textFieldPedidoID;

    @FXML
    private Button buttonGuardar;

    @FXML
    private Button buttonCancelar;

    @FXML
    private void initialize() {
        // Puedes agregar inicializaciones aquí si es necesario
    }

    @FXML
    private void guardarVenta() {
        try (Connection conn = ConexionPool.obtenerConexion()) {
            String fecha = obtenerFechaSeleccionada();
            int cantidad = Integer.parseInt(textFieldCantidad.getText());
            int clienteID = Integer.parseInt(textFieldClienteID.getText());
            int proveedorID = Integer.parseInt(textFieldProveedorID.getText());
            int productosID = Integer.parseInt(textFieldProductosID.getText());
            int pedidoID = Integer.parseInt(textFieldPedidoID.getText());

            String sql = "INSERT INTO ventas (fecha, cantidad, clienteID, proveedorID, productosID, pedidoID) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, fecha);
                stmt.setInt(2, cantidad);
                stmt.setInt(3, clienteID);
                stmt.setInt(4, proveedorID);
                stmt.setInt(5, productosID);
                stmt.setInt(6, pedidoID);

                stmt.executeUpdate();
            }

            // Cierra la ventana después de guardar
            buttonCancelar.getScene().getWindow().hide();

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            // Maneja cualquier error que pueda ocurrir durante la operación de base de datos
            // Puedes mostrar un mensaje de error o registrar el error según tus necesidades
        }
    }

    @FXML
    private void cancelar() {
        // Cierra la ventana sin guardar cambios
        buttonCancelar.getScene().getWindow().hide();
    }

    private String obtenerFechaSeleccionada() {
        LocalDate fechaSeleccionada = datePickerFecha.getValue();
        return fechaSeleccionada != null ? fechaSeleccionada.toString() : null;
    }
}
