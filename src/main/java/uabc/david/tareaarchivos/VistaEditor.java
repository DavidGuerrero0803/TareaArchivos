package uabc.david.tareaarchivos;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javafx.stage.FileChooser;
import java.io.File;

public class VistaEditor {
    private  EditorNotas editor;

    public VistaEditor() {
        this.editor = new EditorNotas();
    }

    public void mostrarEditor() {
        Stage stage = new Stage();
        stage.setTitle("Editor de notas");

        TextArea textArea = new TextArea();
        Button cargar = new Button("Cargar archivo");
        Button guardar = new Button("Guardar archivo");

        HBox contenedorBotones = new HBox(10, cargar, guardar);
        contenedorBotones.setAlignment(Pos.CENTER);
        contenedorBotones.setPadding(new Insets(5));

        BorderPane panelPrincipal = new BorderPane();
        panelPrincipal.setCenter(textArea);
        panelPrincipal.setTop(contenedorBotones);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de Texto (*.txt)", "*.txt")
        );

        cargar.setOnAction(e -> {
            File archivo = fileChooser.showOpenDialog(stage);
            if (archivo != null) {
                try {
                    String contenido = editor.cargarArchivo(archivo);
                    textArea.setText(contenido);
                    mostrarMensaje(Alert.AlertType.INFORMATION, "Archivo cargado", "El archivo se cargó");
                } catch (Exception ex) {
                    mostrarMensaje(Alert.AlertType.ERROR, "Error generado", "No se pudo leer el archivo: " + ex.getMessage());
                }
            }
        });

        guardar.setOnAction(e -> {
            File archivo = fileChooser.showOpenDialog(stage);
            if (archivo != null) {
                if (!archivo.getName().endsWith(".txt")) {
                    archivo = new File(archivo.getAbsolutePath() + ".txt");
                } try {
                    editor.guardarArchivo(archivo, textArea.getText());
                    mostrarMensaje(Alert.AlertType.INFORMATION, "Archivo guardado", "El archivo se ha guardado");
                } catch (Exception ex) {
                    mostrarMensaje(Alert.AlertType.ERROR, "Error generado", "No se pudo guardar el archivo" + ex.getMessage());
                }
            }
        });

        Scene scene = new Scene(panelPrincipal, 500, 600);
        stage.setScene(scene);
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
