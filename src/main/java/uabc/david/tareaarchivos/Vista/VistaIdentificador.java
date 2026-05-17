package uabc.david.tareaarchivos.Vista;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import uabc.david.tareaarchivos.IdentificadorArchivos;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * VistaIdentificador crea la interfaz gráfica para el identificador de firmas.
 * Controla la actualización visual de los resultados tras procesar los bytes.
 */
public class VistaIdentificador {
    private IdentificadorArchivos identificador;

    public VistaIdentificador() {
        this.identificador = new IdentificadorArchivos();
    }

    /**
     * Construye los nodos visuales, formatea los estilos del resultado con colores,
     * inicializa el manejador de eventos del FileChooser y proyecta la ventana en la escena.
     */
    public void mostrarIdentificador() {
        Stage stage = new Stage();
        stage.setTitle("Identificador de archivos");

        Label instrucciones = new Label("Selecciona un archivo para descubrir formato");
        instrucciones.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        Button identificar = new Button("Seleccionar archivo");
        identificar.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px;");

        Label nombreArchivo = new Label("Archivo: Sin seleccionar");
        Label resultado = new Label("Formato del archivo: ...");
        resultado.setStyle("-fx-font-size: 16px; -fx-text-fill: #1b5e20; -fx-font-weight: bold;");

        // Acción que realizará el botón "indentificar" al darle clic.
        identificar.setOnAction(e -> {
            // Abre el explorador de archivos.
            File archivo = new FileChooser().showOpenDialog(stage);
            if (archivo != null) {
                // Actualiza el nombre del archivo seleccionado en la GUI.
                nombreArchivo.setText("Archivo: " + archivo.getName());
                try {
                    // Realiza la identificación del archivo a través de sus bytes.
                    ArrayList<Integer> bytesLeidos = identificador.leer8Bytes(archivo);

                    String formatoDetectado = identificador.verificarFormato(bytesLeidos);

                    resultado.setText("Formato del archivo: " + formatoDetectado);

                } catch (IOException ex) {
                    // En caso de haber un error, se arroja un mensaje emergente.
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error generado");
                    alerta.setHeaderText(null);
                    alerta.setContentText("No se pudieron leer los bytes: " + ex.getMessage());
                    alerta.showAndWait();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Contenedor vertical que guarda los elementos base (labels y botón).
        VBox contenedor = new VBox(15, instrucciones, identificar, nombreArchivo, resultado);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.setPadding(new Insets(20));

        // Creación del panel principal de la ventana.
        BorderPane panel = new BorderPane();
        panel.setCenter(contenedor);

        // Todos los elementos del panel se guardan dentro de la escena.
        Scene scene = new Scene(panel, 500, 220);
        stage.setScene(scene);
        // Se desactiva la opción de modificar el tamaño de la ventana.
        stage.setResizable(false);
        stage.show();
    }
}
