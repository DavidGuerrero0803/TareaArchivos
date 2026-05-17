package uabc.david.tareaarchivos.Vista;
import uabc.david.tareaarchivos.VisualizadorCSV;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Clase que crea la ventana para la visualización de archivos CSV.
 * Modela las columnas de forma dinámica adaptándose al contenido del archivo.
 */
public class VistaCSV {
    private VisualizadorCSV logicaCSV;
    private TableView<String[]> tabla;
    private ObservableList<String[]> datosTabla;

    public VistaCSV() {
        this.logicaCSV = new VisualizadorCSV();
    }

    /**
     * Crea la ventana principal, configura la acción del selector de archivos
     * y despliega la interfaz de usuario gráfica.
     */
    public void visualizarCSV() {
        Stage stage = new Stage();
        stage.setTitle("Visualizador CSV");

        // Inicializa los componentes de la tabla
        tabla = new TableView<>();
        datosTabla = FXCollections.observableArrayList();
        tabla.setItems(datosTabla);

        // Se ajustan las columnas para que ocupen espacio que les sobre en la cebecera.
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Creado el botón que servirá par abrir el explorador de archivos.
        Button abrirArchivo = new Button("Carga el archivo CSV");

        // Acción que realizará el botón "abrirArchivo" al darle clic.
        abrirArchivo.setOnAction(e -> {
            File file = new FileChooser().showOpenDialog(stage);
            if (file != null) {

                // Limpia por completo columnas y datos previos.
                tabla.getColumns().clear();
                datosTabla.clear();

                try {
                    // Trata de llamar al procesador del archivo.
                    VisualizadorCSV.ElementoCSV resultado = logicaCSV.procesarArchivo(file);

                    String[] cabeceras = resultado.getCabeceras();
                    // Itera sobre el arreglo de cabeceras detectado en la primera línea del CSV
                    for (int i = 0; i < cabeceras.length; i++) {
                        final int indice = i;

                        // Instancia una nueva columna con el texto de la cabecera.
                        TableColumn<String[], String> columna = new TableColumn<>(cabeceras[i]);

                        // Se construyen las celdas necesarias para completar el CSV.
                        columna.setCellValueFactory(f -> {
                            String[] fila = f.getValue();
                            // Si la fila existe y el índice está dentro de los límites,
                            // envuelve el String en una propiedad observable de JavaFX, si no, no pone nada.
                            return new SimpleStringProperty(fila != null && indice < fila.length ? fila[indice] : "");
                        });

                        // Agrega las columnas que se necesitaron crear a la tabla.
                        tabla.getColumns().add(columna);
                    }

                    // Coloca los registros dentro de la tabla.
                    datosTabla.addAll(resultado.getFilas());

                } catch (Exception ex) {
                    // Atrapa el error generado (en caso de haberlo) y lo muestra en una ventana.
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Error al procesar el CSV: " + ex.getMessage());
                    alerta.showAndWait();
                }
            }
        });

        // Contenedor vertical superior para el botón.
        VBox contenedor = new VBox(abrirArchivo);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.setPadding(new Insets(15));

        // Panel principal de la ventana que contiene la tabla con los registros.
        BorderPane panel = new BorderPane();
        panel.setTop(contenedor);
        panel.setCenter(tabla);
        panel.setPadding(new Insets(10));

        // Todos los elementos del panel se guardan dentro de la escena.
        Scene scene = new Scene(panel, 500, 400);
        stage.setScene(scene);
        stage.show();
    }
}
