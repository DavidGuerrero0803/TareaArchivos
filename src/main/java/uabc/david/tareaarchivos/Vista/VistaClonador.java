package uabc.david.tareaarchivos.Vista;
import uabc.david.tareaarchivos.ClonadorImagen;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

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

        clonar.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Selecciona la ruta de la imagen");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            File origen = fileChooser.showOpenDialog(stage);

            if (origen != null) {
                FileChooser sc = new FileChooser();
                sc.setTitle("Selecciona el destino de la imagen");
                sc.setInitialFileName("copia_" + origen.getName());
                File destino = sc.showSaveDialog(stage);

                if (destino != null) {
                    clonar.setDisable(true);
                    estado.setText("Clonando imagen...");

                    Thread hiloCopia = new Thread(() -> clonador.clonar(
                            origen,
                            destino,
                            progressBar,
                            estado,
                            () -> {
                                estado.setText("La imagen " + destino.getName() + " se ha clonado");
                                clonar.setDisable(false);
                                mostrarMensaje(Alert.AlertType.INFORMATION, "Archivo clonado", "La imagen se ha clonado en su destino");
                            },
                            (errorMensaje) -> {
                                estado.setText("Estado: Error al clonar.");
                                clonar.setDisable(false);
                                mostrarMensaje(Alert.AlertType.ERROR, "Error generado", "No se pudo copiar el archivo: " + errorMensaje);
                            }
                    ));
                    hiloCopia.start();
                }
            }
        });

        VBox contenedorPrincipal = new VBox(15, instrucciones, clonar, progressBar, estado);
        contenedorPrincipal.setAlignment(Pos.CENTER);
        contenedorPrincipal.setPadding(new Insets(20));

        BorderPane panel = new BorderPane();
        panel.setCenter(contenedorPrincipal);

        Scene scene = new Scene(panel, 500, 220);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void mostrarMensaje(Alert.AlertType alertaTipo, String titulo, String mensaje) {
        Alert alerta = new Alert(alertaTipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
