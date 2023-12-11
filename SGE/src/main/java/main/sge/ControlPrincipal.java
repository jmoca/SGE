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
import java.time.LocalDate;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
        //cargarDatosClientes();
        cargarDatosProducto ();
        //cargarDatosProveedores();
        //cargarDatosVentas();
        configurarReloj();
    }
    private void configurarReloj() {
        // Actualizar la fecha cada segundo
        Timeline timelineFecha = new Timeline(
                new KeyFrame ( Duration.seconds(1), event -> {
                    //cargarDatosClientes();
                    cargarDatosProducto ();
                    //cargarDatosProveedores();
                    //cargarDatosVentas();
                })

        );
        timelineFecha.setCycleCount(Timeline.INDEFINITE);
        timelineFecha.play();

        // Actualizar la hora cada segundo
        Timeline timelineHora = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    LocalTime hora      = LocalTime.now();
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
                tablaInventario.getItems ().add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }}
