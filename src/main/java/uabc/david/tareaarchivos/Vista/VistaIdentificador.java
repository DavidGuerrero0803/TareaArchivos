package uabc.david.tareaarchivos.Vista;
import uabc.david.tareaarchivos.IdentificadorArchivos;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VistaIdentificador {
    private IdentificadorArchivos identificador;

    public VistaIdentificador() {
        this.identificador = new IdentificadorArchivos();
    }

    public void mostrarIdentificador() {
        Stage stage = new Stage();
        stage.setTitle("Identificador de archivos");

        Label instrucciones = new Label("Selecciona un archivo para descubrir formato");
        instrucciones.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        Button identificar = new Button("Seleccionar archivo");
        identificar.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px;");

        Label nombreArchivo = new Label("Archivo: Sin seleccionar");
        Label resultado = new Label("Resultado: ...");
        resultado.setStyle("-fx-font-size: 16px; -fx-text-fill: #1b5e20; -fx-font-weight: bold;");

        VBox contenedor = new VBox(15, instrucciones, identificar, nombreArchivo, resultado);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.setPadding(new Insets(20));

        BorderPane panel = new BorderPane();
        panel.setCenter(contenedor);

        Scene scene = new Scene(panel, 500, 220);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
