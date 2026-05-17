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

/**
 * VistaClonador crea la ventana para el Clonador de Imágenes.
 * Modela las columnas de forma dinámica en una tabla, adaptándose al contenido del archivo.
 */
public class VistaClonador {
    private ClonadorImagen clonador;

    public VistaClonador() {
        this.clonador = new ClonadorImagen();
    }

    /**
     * Crea las etiquetas de texto, la barra de progreso, asocia el explorador
     * de archivos y gestiona el hilo asíncrono de la copia.
     */
    public void mostrarClonador() {
        Stage stage = new Stage();
        stage.setTitle("Clonador de Imágenes");

        // Etiquetas informativas.
        Label instrucciones = new Label("Selecciona una imagen y una carpeta de destino para clonarla.");
        instrucciones.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        Label estado = new Label("Estado: Esperando archivo...");
        estado.setStyle("-fx-font-size: 13px;");

        // Componente que crea la barra de progreso.
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(400);
        progressBar.setPrefHeight(18);

        // Botón encargado de ejecutar la búsqueda de imagen.
        Button clonar = new Button("Seleccionar Imagen");
        clonar.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px;");

        // Acción que realizará el botón "clonar" al darle clic.
        clonar.setOnAction(e -> {
            // Selección del archivo de origen.
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Selecciona la ruta de la imagen");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            File origen = fileChooser.showOpenDialog(stage);

            if (origen != null) {
                // Elección del nombre y ruta destino de guardado.
                FileChooser sc = new FileChooser();
                sc.setTitle("Selecciona el destino de la imagen");
                sc.setInitialFileName("copia_" + origen.getName());
                File destino = sc.showSaveDialog(stage);

                if (destino != null) {
                    clonar.setDisable(true);
                    estado.setText("Clonando imagen...");

                    // Crea un hilo independiente para ejecutar la transferencia de bytes.
                    Thread hiloCopia = new Thread(() -> clonador.clonar(
                            origen,
                            destino,
                            progressBar,
                            estado,
                            // Ejecución en caso de haber éxito.
                            () -> {
                                estado.setText("La imagen " + destino.getName() + " se ha clonado");
                                clonar.setDisable(false);
                                mostrarMensaje(Alert.AlertType.INFORMATION, "Archivo clonado", "La imagen se ha clonado en su destino");
                            },
                            // Ejecución en caso de haber un error.
                            (errorMensaje) -> {
                                estado.setText("Estado: Error al clonar.");
                                clonar.setDisable(false);
                                mostrarMensaje(Alert.AlertType.ERROR, "Error generado", "No se pudo copiar el archivo: " + errorMensaje);
                            }
                    ));
                    // Inicia el ciclo de vida del hilo secundario.
                    hiloCopia.start();
                }
            }
        });

        // Contenedor vertical estructurado para centrar todos los elementos.
        VBox contenedorPrincipal = new VBox(15, instrucciones, clonar, progressBar, estado);
        contenedorPrincipal.setAlignment(Pos.CENTER);
        contenedorPrincipal.setPadding(new Insets(20));

        // Creación del panel principal de la ventana
        BorderPane panel = new BorderPane();
        panel.setCenter(contenedorPrincipal);

        // Todos los elementos del panel se guardan dentro de la escena.
        Scene scene = new Scene(panel, 500, 220);
        stage.setScene(scene);
        // Se desactiva la opción de modificar el tamaño de la ventana.
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
