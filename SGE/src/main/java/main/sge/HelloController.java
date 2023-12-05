package main.sge;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {
    public TableColumn descripcionProducto;
    public TableColumn precioProducto;
    public TableColumn nombreProveedores;
    public TableColumn direccionProveedores;
    public TableColumn contactoProveedores;
    public TableColumn produc_sumis;
    @FXML
    private TableColumn<Producto, String> nombreProducto;
    @FXML
    private Label welcomeText;

    @FXML
    private TableView<Producto> tableView;

    @FXML
    protected void onHelloButtonClick() {
        try {
            // Establecer la conexión con la base de datos
            Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sge", "root", "root");

            // Consulta SQL para obtener los nombres de productos
            String query = "SELECT nombre FROM productos";

            // Crear la sentencia preparada
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                // Ejecutar la consulta
                ResultSet resultSet = preparedStatement.executeQuery();

                // Limpiar la tabla antes de agregar nuevos datos
                tableView.getItems().clear();

                // Configurar el cellValueFactory para la columna nombreProducto
                nombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));

                // Procesar los resultados
                while (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");

                    // Agregar el nombre a la tabla
                    tableView.getItems().add(new Producto(nombre));
                }
            }

            // Cerrar la conexión
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción de manera apropiada en tu aplicación
        }
    }

    // Clase auxiliar para representar un producto
    public static class Producto {
        private final String nombre;

        public Producto(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }
    }
}
