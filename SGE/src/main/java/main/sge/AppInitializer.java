package main.sge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Cargar la vista desde el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("principal.fxml"));
        Parent root = loader.load();

        // Configurar el controlador si es necesario
        ControlPrincipal controller = loader.getController();
        // Puedes realizar configuraciones adicionales en el controlador si es necesario

        // Configurar la escena
        Scene scene = new Scene(root);

        // Configurar el escenario (ventana)
        stage.setTitle("SGE Application");
        stage.setScene(scene);

        // Mostrar la ventana
        stage.show();
    }

    public static void launchApp(String[] args) {
        launch(args);
    }
}
