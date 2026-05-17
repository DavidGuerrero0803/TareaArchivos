package uabc.david.tareaarchivos.Vista;
import uabc.david.tareaarchivos.EncriptadorXOR;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

import java.io.File;
import javafx.stage.FileChooser;
import java.io.IOException;

public class VistaEncriptador {
    private EncriptadorXOR encriptador;

    public VistaEncriptador() {
        this.encriptador = new EncriptadorXOR();
    }

    public void mostrarEncriptador() {
        Stage stage = new Stage();
        stage.setTitle("Encriptador XOR");

        Label titulo = new Label("Encripta/Desencripta el archivo que desees");
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        TextField campoTexto = new TextField("123");
        campoTexto.setPromptText("(0-255)");
        campoTexto.setMaxWidth(110);
        campoTexto.setStyle("-fx-font-size: 13px;");

        Button encriptar = new Button("Seleccionar archivo");
        encriptar.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px;");

        encriptar.setOnAction(e -> {
            File archivo = new FileChooser().showOpenDialog(stage);

            if (archivo != null) {
                File destino = new File(archivo.getParent(), "xor_" + archivo.getName());

                try {
                    encriptador.procesarXOR(archivo, destino, campoTexto.getText());

                    mostrarMensaje(Alert.AlertType.INFORMATION, "Proceso finalizado",
                            "Se ha generado el archivo: " + destino.getName() +
                                    "\nUbicado en: " + destino.getParent() +
                                    "\nVuelve a procesar archivo con la misma clave para recuperarlo");

                } catch (NumberFormatException nfe) {
                    mostrarMensaje(Alert.AlertType.WARNING, "Error generado",
                            "La máscara debe ser un número entero entre 0 y 255");
                } catch (IOException ex) {
                    mostrarMensaje(Alert.AlertType.ERROR, "Error generado",
                            "Error al leer/escribir el flujo de bytes: " + ex.getMessage());
                }
            }
        });

        HBox contenedorMascara = new HBox(10, new Label("Clave XOR (0-255):"), campoTexto);
        contenedorMascara.setAlignment(Pos.CENTER);

        VBox contenedorPrincipal = new VBox(15, titulo, contenedorMascara, encriptar);
        contenedorPrincipal.setAlignment(Pos.CENTER);
        contenedorPrincipal.setPadding(new Insets(20));

        BorderPane panel = new BorderPane();
        panel.setCenter(contenedorPrincipal);

        Scene escena = new Scene(panel, 500, 220);
        stage.setScene(escena);
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
