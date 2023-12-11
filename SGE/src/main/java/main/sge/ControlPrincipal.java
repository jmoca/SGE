package main.sge;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

        // Inicializar tablaClientes


        cargarDatosProducto();
        cargarDatosClientes();  // Mover la llamada después de la inicialización de la tabla
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
}