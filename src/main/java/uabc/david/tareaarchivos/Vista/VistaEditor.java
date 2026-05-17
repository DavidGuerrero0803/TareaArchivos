package uabc.david.tareaarchivos.Vista;
import uabc.david.tareaarchivos.EditorNotas;

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

/**
 * VistaEditor maneja la interfaz de usuario gráfica para el Editor de Notas.
 * Administra principalmente el contenedor con el área de texto y los botones
 * de carga y guardado de archivos.
 */
public class VistaEditor {
    private EditorNotas editor;

    public VistaEditor() {
        this.editor = new EditorNotas();
    }

    /**
     * Configura el escenario, inicializa los componentes visuales
     * y muestra la ventana en la pantalla del usuario.
     */
    public void mostrarEditor() {
        Stage stage = new Stage();
        stage.setTitle("Editor de notas");

        // Se crea el TextArea para la edición y modificación de texto.
        TextArea textArea = new TextArea();

        // Botones encargados de cargar y guardar archivos respectivamente.
        Button cargar = new Button("Cargar archivo");
        Button guardar = new Button("Guardar archivo");

        // Contenedor horizontal para mantener los 2 botones en la parte superior.
        HBox contenedorBotones = new HBox(10, cargar, guardar);
        contenedorBotones.setAlignment(Pos.CENTER);
        contenedorBotones.setPadding(new Insets(5));

        // Panel principal encargado de mostrar los botones y el área para escribir.
        BorderPane panelPrincipal = new BorderPane();
        panelPrincipal.setCenter(textArea);
        panelPrincipal.setTop(contenedorBotones);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de Texto (*.txt)", "*.txt")
        );

        // Acción que realizará el botón "cargar" al darle clic.
        cargar.setOnAction(e -> {
            // Muestra la ventana para seleccionar un archivo.
            File archivo = fileChooser.showOpenDialog(stage);
            if (archivo != null) {
                try {
                    // Se trata de llamar a cargarArchivo para dejar el String en el área de texto.
                    String contenido = editor.cargarArchivo(archivo);
                    textArea.setText(contenido);
                    mostrarMensaje(Alert.AlertType.INFORMATION, "Archivo cargado", "El archivo se cargó");
                } catch (Exception ex) {
                    // Mensaje de error en caso de no poder cargar el archivo.
                    mostrarMensaje(Alert.AlertType.ERROR, "Error generado", "No se pudo leer el archivo: " + ex.getMessage());
                }
            }
        });

        // Acción que realizará el botón "guardar" al darle clic.
        guardar.setOnAction(e -> {
            // Muestra la ventana para determinar nombre y ruta de guardado.
            File archivo = fileChooser.showOpenDialog(stage);
            if (archivo != null) {
                // Se valida el uso de la extensión .txt si es que el usuario la omitió.
                if (!archivo.getName().endsWith(".txt")) {
                    archivo = new File(archivo.getAbsolutePath() + ".txt");
                } try {
                    // Se trata de llamar a guardarArchivo para que el texto quede almacenado en el equipo.
                    editor.guardarArchivo(archivo, textArea.getText());
                    mostrarMensaje(Alert.AlertType.INFORMATION, "Archivo guardado", "El archivo se ha guardado");
                } catch (Exception ex) {
                    // Mensaje de error en caso de no poder guardar el archivo.
                    mostrarMensaje(Alert.AlertType.ERROR, "Error generado", "No se pudo guardar el archivo" + ex.getMessage());
                }
            }
        });

        // Todos los elementos del contenedor se guardan dentro de la escena.
        Scene scene = new Scene(panelPrincipal, 500, 600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * mostrarMensaje() se encarga de lanzar ventanas emergentes de ciertos tipos.
     * @param alertaTipo Categoría del mensaje (Información o Error).
     * @param titulo Encabezado de la ventana de diálogo.
     * @param mensaje Texto de la alerta.
     */
    private void mostrarMensaje(Alert.AlertType alertaTipo, String titulo, String mensaje) {
        Alert alerta = new Alert(alertaTipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
