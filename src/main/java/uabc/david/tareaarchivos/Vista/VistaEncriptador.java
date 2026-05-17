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

/**
 * VistaEncriptador controla la GUI para el Encriptador XOR.
 * Proporciona un campo de texto para la clave y asocia las alertas del sistema.
 */
public class VistaEncriptador {
    private EncriptadorXOR encriptador;

    public VistaEncriptador() {
        this.encriptador = new EncriptadorXOR();
    }

    /**
     * Inicializa el campo de entrada, gestiona las rutas dinámicas de guardado,
     * implementa el manejo de errores y muestra el resultado en una ventana emergente.
     */
    public void mostrarEncriptador() {
        Stage stage = new Stage();
        stage.setTitle("Encriptador XOR");

        Label titulo = new Label("Encripta/Desencripta el archivo que desees");
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        // TextFiel para ingresar un valor (clave) de 0-255 (100 por default).
        TextField campoTexto = new TextField("100");
        campoTexto.setPromptText("(0-255)");
        campoTexto.setMaxWidth(110);
        campoTexto.setStyle("-fx-font-size: 13px;");

        // Se crea el botón encargado de seleccionar el archivo a encriptar/desencriptar.
        Button encriptar = new Button("Seleccionar archivo");
        encriptar.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px;");

        // Acción que realizará el botón "encriptar" al darle clic.
        encriptar.setOnAction(e -> {
            // Despliega el explorador de archivos para seleccionar cualquier archivo.
            File archivo = new FileChooser().showOpenDialog(stage);

            if (archivo != null) {
                // Genera la ruta destino de forma automática en el mismo directorio de origen,
                // colocando el prefijo "xor_" al nombre original del archivo procesado.
                File destino = new File(archivo.getParent(), "xor_" + archivo.getName());

                try {
                    // Trata de realizar el procesamiento, pasándole los archivos y el String de la máscara.
                    encriptador.procesarXOR(archivo, destino, campoTexto.getText());

                    // Se lanzará este mensaje en caso de haber podido encriptar el archivo.
                    mostrarMensaje(Alert.AlertType.INFORMATION, "Proceso finalizado",
                            "Se ha generado el archivo: " + destino.getName() +
                                    "\nUbicado en: " + destino.getParent() +
                                    "\nVuelve a procesar archivo con la misma clave para recuperarlo");

                } catch (NumberFormatException nfe) {
                    // Se lanzará este mensaje en caso de no ingresar una cantidad válida.
                    mostrarMensaje(Alert.AlertType.WARNING, "Error generado",
                            "La entrada debe ser un número entero entre 0 y 255");
                } catch (IOException ex) {
                    // Se lanzará este mensaje en caso de haber un error con el encriptado del archivo.
                    mostrarMensaje(Alert.AlertType.ERROR, "Error generado",
                            "Error al leer/escribir el flujo de bytes: " + ex.getMessage());
                }
            }
        });

        // HBox que contiene el campo de texto donde se ingresará la clave XOR.
        HBox contenedorMascara = new HBox(10, new Label("Clave XOR (0-255):"), campoTexto);
        contenedorMascara.setAlignment(Pos.CENTER);

        // VBox que contiene todos los elementos visuales de la ventana.
        VBox contenedorPrincipal = new VBox(15, titulo, contenedorMascara, encriptar);
        contenedorPrincipal.setAlignment(Pos.CENTER);
        contenedorPrincipal.setPadding(new Insets(20));

        // Creación del panel principal de la ventana.
        BorderPane panel = new BorderPane();
        panel.setCenter(contenedorPrincipal);

        // Todos los elementos del panel se guardan dentro de la escena.
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
