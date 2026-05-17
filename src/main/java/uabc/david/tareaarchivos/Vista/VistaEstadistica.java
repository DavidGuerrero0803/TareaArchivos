package uabc.david.tareaarchivos.Vista;
import uabc.david.tareaarchivos.EstadisticaTexto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;

/**
 * VistaEstadistica maneja la interfaz de usuario gráfica para la clase EstadisticaTexto.
 * Crea una ventana que muestra los datos del archivo dentro de un TableView.
 */
public class VistaEstadistica {
    private EstadisticaTexto analizador;

    public VistaEstadistica() {
        this.analizador = new EstadisticaTexto();
    }

    /**
     * Inicializa los componentes, define la estructura de las columnas de la tabla,
     * configura el botón y despliega la interfaz gráfica.
     */
    public void mostrarEstadisticas() {
        Stage stage = new Stage();
        stage.setTitle("Estadísticas de texto");

        // Contenedor de la tabla y su lista observable de datos reactivos.
        TableView tabla = new TableView<>();
        ObservableList datosTabla = FXCollections.observableArrayList();

        tabla.setItems(datosTabla);
        tabla.setMinHeight(150);
        tabla.setMaxHeight(150);
        tabla.setStyle("-fx-font-size: 16px;");

        // Botón encargado de mostrar el explorador de archivos.
        Button selector = new Button("Abre un archivo para analizar");

        // Configuración de la primera columna: información de lo que se va a mostrar.
        TableColumn<TablaElemento, String> infoColumna = new TableColumn<>("Información");
        infoColumna.setCellValueFactory(new PropertyValueFactory<>("informacion"));
        infoColumna.setMinWidth(180);

        // Configuración de la segunda columna: El valor total numérico calculado.
        TableColumn<TablaElemento, String> valorColumna = new TableColumn<>("Total");
        valorColumna.setCellValueFactory(new PropertyValueFactory<>("total"));
        valorColumna.setMinWidth(180);

        // Esto fuerza a las columnas a ocupar el espacio que pueda sobrarles.
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Ambas columnas se añaden al contenedor de la tabla.
        tabla.getColumns().addAll(infoColumna, valorColumna);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de Texto (*.txt)", "*.txt")
        );

        // Acción que realizará el botón "selector" al darle clic.
        selector.setOnAction(e -> {
            File archivo = fileChooser.showOpenDialog(stage);
            if (archivo != null) {
                try {
                    // Invoca la clase junto a realizarEstadistica.
                    HashMap<String, Integer> estadisticas = analizador.realizarEstadistica(archivo);

                    // Se vacían registros previos de la tabla.
                    datosTabla.clear();
                    // Se añaden los objetos que indicarán la información de cada cuestión del archivo en la tabla.
                    datosTabla.add(new TablaElemento("Líneas", String.valueOf(estadisticas.get("Lineas"))));
                    datosTabla.add(new TablaElemento("Palabras", String.valueOf(estadisticas.get("Palabras"))));
                    datosTabla.add(new TablaElemento("Caracteres", String.valueOf(estadisticas.get("Caracteres"))));
                } catch (Exception ex) {
                    // Mensaje (alert) que avisará en caso de presentarse un error.
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error generado");
                    alerta.setHeaderText(null);
                    alerta.setContentText("No pudo cargarse el archivo: " + ex.getMessage());
                    alerta.showAndWait();
                }
            }
        });

        // Contenedor vertical que guarda el botón que abre el explorador de archivos.
        VBox contenedorBoton = new VBox(selector);
        contenedorBoton.setAlignment(Pos.CENTER);
        contenedorBoton.setPadding(new Insets(5));

        // Panel principal de la ventana que contiene la tabla con la información.
        BorderPane panel = new BorderPane();
        panel.setTop(contenedorBoton);
        panel.setCenter(tabla);
        panel.setPadding(new Insets(10));

        // Todos los elementos del panel se guardan dentro de la escena.
        Scene scene = new Scene(panel, 500, 250);
        // Se desactiva la opción de modificar el tamaño de la ventana.
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Clase estática necesaria para manejar los datos requeridos por TableView.
     */
    public static class TablaElemento {
        private String informacion;
        private String total;

        /**
         * Constructor que inicializa las propiedades del registro.
         * @param informacion Texto descriptivo de la fila.
         * @param total Valor en formato string de la métrica analizada.
         */
        public TablaElemento(String informacion, String total) {
            this.informacion = informacion;
            this.total = total;
        }

        // Getters necesarios para el funcionamiento de PropertyValueFactory.
        public String getInformacion() {
            return informacion;
        }

        public String getTotal() {
            return total;
        }
    }
}
