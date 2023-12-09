package com.main.sge;

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
    public TableColumn produc_sumisProveedores;
    public TableColumn nombreCliente;
    public TableColumn direccionCliente;
    public TableColumn contactoCliente;
    public TableColumn historialClientes;
    public TableColumn vendidosVentas;
    public TableColumn cantidadVentas;
    public TableColumn fechaVentas;
    public TableColumn clienteVentas;
    public TableColumn stockProducto;
    @FXML
    private TableColumn<Producto, String> nombreProducto;
    @FXML
    private TableColumn<Producto, String> descripcionProducto;
    @FXML
    private TableColumn<Producto, Double> precioProducto;
    @FXML
    private TableColumn<String, String> nombreProveedores;
    @FXML
    private TableColumn<String, String> direccionProveedores;
    @FXML
    private TableColumn<String, String> contactoProveedores;
    @FXML
    private TableColumn<String, Integer> produc_sumis;

    @FXML
    private Label welcomeText;

    @FXML
    private TableView<Producto> tableView;

    @FXML
    protected void onHelloButtonClick() {
        try {
            // Establecer la conexión con la base de datos
            try (Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sge", "root", "root")) {

                // Consulta SQL para obtener los datos de productos
                String queryProductos = "SELECT nombre, descripcion, precio FROM productos";
                cargarDatosEnTableView(connection, queryProductos, nombreProducto, descripcionProducto, precioProducto);

                // Consulta SQL para obtener los datos de proveedores
                String queryProveedores = "SELECT nombre, direccion, contacto, productos_suministrados FROM proveedores";
                cargarDatosEnTableView(connection, queryProveedores, nombreProveedores, direccionProveedores, contactoProveedores, produc_sumis);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción de manera apropiada en tu aplicación
        }
    }


    // Método para cargar datos en una TableView
    private void cargarDatosEnTableView(Connection connection, String query, TableColumn... columnas) throws SQLException {
        // Crear la sentencia preparada
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Ejecutar la consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            // Limpiar la tabla antes de agregar nuevos datos
            tableView.getItems().clear();

            // Configurar el cellValueFactory para cada columna
            for (TableColumn columna : columnas) {
                if (columna.getCellData(0) instanceof String) {
                    columna.setCellValueFactory(new PropertyValueFactory<>(columna.getText()));
                } else if (columna.getCellData(0) instanceof Integer) {
                    columna.setCellValueFactory(new PropertyValueFactory<>(columna.getText()));
                } else if (columna.getCellData(0) instanceof Double) {
                    columna.setCellValueFactory(new PropertyValueFactory<>(columna.getText()));
                }
            }

            // Procesar los resultados
            while (resultSet.next()) {
                for (TableColumn columna : columnas) {
                    if (columna.getCellData(0) instanceof String) {
                        String dato = resultSet.getString(columna.getText());
                        // Agregar el dato a la tabla
                        columna.getTableView().getItems().add(new Producto(dato));
                    } else if (columna.getCellData(0) instanceof Integer) {
                        int dato = resultSet.getInt(columna.getText());
                        // Agregar el dato a la tabla
                        columna.getTableView().getItems().add(new Producto(Integer.toString(dato)));
                    } else if (columna.getCellData(0) instanceof Double) {
                        double dato = resultSet.getDouble(columna.getText());
                        // Agregar el dato a la tabla
                        columna.getTableView().getItems().add(new Producto(Double.toString(dato)));
                    }
                }
            }
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
