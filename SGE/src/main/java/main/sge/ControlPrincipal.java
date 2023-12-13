package main.sge;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ControlPrincipal {

    public Button anadirProducto;
    @FXML
    private TableView<Proveedor> tablaProveedores;

    @FXML
    private TableColumn<Proveedor, String> ColProveedorNombre;

    @FXML
    private TableColumn<Proveedor, String> ColProveedorDireccion;

    @FXML
    private TableColumn<Proveedor, String> ColProveedorContacto;

    @FXML
    private TableColumn<Proveedor, String> ColProveedorproducSumis;

    // Tabla y columnas para Productos
    @FXML
    private TableView<Producto> tablaInventario;

    @FXML
    private TableColumn<Producto, Integer> colInventarioID;

    @FXML
    private TableColumn<Producto, String> colInventarioNombre;

    @FXML
    private TableColumn<Producto, String> colInventarioDescripcion;

    @FXML
    private TableColumn<Producto, Double> colInventarioPrecio;

    @FXML
    private TableColumn<Producto, Integer> colInventarioStock;

    // Tabla y columnas para Clientes


    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, Integer> colClientesID;

    @FXML
    private TableColumn<Cliente, String> colClientesNombre;

    @FXML
    private TableColumn<Cliente, String> colClientesDireccion;

    @FXML
    private TableColumn<Cliente, String> colClientesContacto;

    @FXML
    private TableColumn<Cliente, String> colClientesHistorialCompras;

    // Botón para crear Producto
    @FXML
    private Button buttonCrearProducto;

    @FXML
    private Label welcomeText;
    @FXML
    private Label horaLabel;

    @FXML
    private TableView<Producto> tableView;

    @FXML
    private void initialize() {
        System.out.println("Inicializando el controlador...");
        configurarColumnas();

        // Inicializar tablaClientes y tablaProveedores
        cargarDatosProducto();
        cargarDatosClientes();
        cargarDatosProveedores();  // Agregado para cargar datos en la tabla de proveedores

        configurarReloj();
    }

    private void configurarReloj() {
        // Actualizar la fecha cada segundo
        Timeline timelineFecha = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    cargarDatosProducto();
                    cargarDatosClientes();
                })
        );
        timelineFecha.setCycleCount(Timeline.INDEFINITE);
        timelineFecha.play();

        // Actualizar la hora cada segundo
        Timeline timelineHora = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    LocalTime hora = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    horaLabel.setText(formatter.format(hora));
                })
        );
        timelineHora.setCycleCount(Timeline.INDEFINITE);
        timelineHora.play();
    }

    private void configurarColumnas() {
        colInventarioID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colInventarioNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colInventarioDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colInventarioPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colInventarioStock.setCellValueFactory(new PropertyValueFactory<>("cantidadEnStock"));

        // Configurar columnas para la tabla de clientes
        colClientesID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colClientesNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colClientesDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colClientesContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        colClientesHistorialCompras.setCellValueFactory(new PropertyValueFactory<>("historialCompras"));

        // Configurar columnas para la tabla de proveedores
        ColProveedorNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColProveedorDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        ColProveedorContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        ColProveedorproducSumis.setCellValueFactory(new PropertyValueFactory<>("productosSuministrados"));
    }
    private void cargarDatosProveedores() {
        try (Connection conn = ConexionPool.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Proveedores");
             ResultSet rs = stmt.executeQuery()) {

            tablaProveedores.getItems().clear();

            while (rs.next()) {
                Proveedor proveedor = new Proveedor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("contacto"),
                        rs.getString("productosSuministrados")
                );
                tablaProveedores.getItems().add(proveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void cargarDatosClientes() {
        try (Connection conn = ConexionPool.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Clientes");
             ResultSet rs = stmt.executeQuery()) {

            tablaClientes.getItems().clear();

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("contacto"),
                        rs.getString("historialCompras")
                );
                tablaClientes.getItems().add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void cargarDatosProducto() {
        try (Connection conn = ConexionPool.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM productos");
             ResultSet rs = stmt.executeQuery()) {

            tablaInventario.getItems().clear();

            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidadEnStock")
                );
                tablaInventario.getItems().add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void crearProducto() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/sge/RegistrarProducto.fxml"));
            Parent registrarRoot = loader.load();
            RegistrarProducto registrarProductoControlador = loader.getController();
            Scene scene = new Scene(registrarRoot);
            Stage stage = new Stage();
            stage.setTitle("Registrar Nuevo Producto");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void crearCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/sge/RegistrarCliente.fxml"));
            Parent registrarRoot = loader.load();
            RegistrarCliente registrarClienteControlador = loader.getController();
            Scene scene = new Scene(registrarRoot);
            Stage stage = new Stage();
            stage.setTitle("Registrar Nuevo Cliente");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void crearProveedor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/sge/RegistrarProveedor.fxml"));
            Parent registrarRoot = loader.load();
            RegistrarProveedor registrarProveedorControlador = loader.getController();
            Scene scene = new Scene(registrarRoot);
            Stage stage = new Stage();
            stage.setTitle("Registrar Nuevo Proveedor");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
            actualizarVistaProveedores();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void actualizarVistaProveedores() {
        cargarDatosProveedores();
    }

}
