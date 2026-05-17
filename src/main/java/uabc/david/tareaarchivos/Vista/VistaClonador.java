package uabc.david.tareaarchivos.Vista;
import javafx.scene.Scene;
import uabc.david.tareaarchivos.ClonadorImagen;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VistaClonador {
    private ClonadorImagen clonador;

    public VistaClonador() {
        this.clonador = new ClonadorImagen();
    }

    public void mostrarClonador() {
        Stage stage = new Stage();
        stage.setTitle("Clonador de Imágenes");

        Label instrucciones = new Label("Selecciona una imagen y una carpeta de destino para clonarla.");
        instrucciones.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        Label estado = new Label("Estado: Esperando archivo...");
        estado.setStyle("-fx-font-size: 13px;");

        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(400);
        progressBar.setPrefHeight(18);

        Button clonar = new Button("Seleccionar Imagen");
        clonar.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px;");

        VBox layout = new VBox(15, instrucciones, clonar, progressBar, estado);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        BorderPane panel = new BorderPane();
        panel.setCenter(layout);

        Scene escena = new Scene(panel, 500, 220);
        stage.setScene(escena);
        stage.setResizable(false);
        stage.show();
    }
}
