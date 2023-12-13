package main.sge;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrarCliente {



    @FXML
    private TextField registrarCliNombre;

    @FXML
    private TextField registrarCliContacto;

    @FXML
    private TextField registrarCliDireccion;
    @FXML
    private TextField registrarCliHis;

    @FXML
    private Button registrarCliente;

    @FXML
    void registrarCliente(ActionEvent event) {
        // Obtener los valores de los campos
        String nombre = registrarCliNombre.getText();
        String direccion = registrarCliDireccion.getText();
        String contacto = registrarCliContacto.getText();
        String his = registrarCliHis.getText();

        try {
            // Agregar el cliente a la base de datos
            agregarClienteABaseDeDatos(nombre, direccion, contacto, his);

            // Limpiar los campos después de registrar el cliente
            limpiarCampos();

            System.out.println("Cliente registrado y agregado a la base de datos");
        } catch (Exception e) {
            System.err.println("Error al registrar el cliente: " + e.getMessage());
        }
    }

    private void agregarClienteABaseDeDatos(String nombre, String direccion, String contacto, String his) {
        try (Connection connection = ConexionPool.obtenerConexion()) {
            // Preparar la consulta SQL
            String sql = "INSERT INTO clientes (nombre, direccion, contacto, historialCompras) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nombre);
                statement.setString(2, direccion);
                statement.setString(3, contacto);
                statement.setString(4, his);

                // Ejecutar la consulta
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar el cliente a la base de datos: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        registrarCliNombre.clear();
        registrarCliContacto.clear();
        registrarCliDireccion.clear();
        registrarCliHis.clear();
    }
}
