package uabc.david.tareaarchivos;

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

public class VistaEstadistica {
    private EstadisticaTexto analizador;

    public VistaEstadistica() {
        this.analizador = new EstadisticaTexto();
    }

    public void mostrarEstadisticas() {
        Stage stage = new Stage();
        stage.setTitle("Estadísticas de texto");

        TableView tabla = new TableView<>();
        ObservableList datosTabla = FXCollections.observableArrayList();

        tabla.setItems(datosTabla);
        tabla.setMinHeight(150);
        tabla.setMaxHeight(150);
        tabla.setStyle("-fx-font-size: 16px;");

        Button selector = new Button("Abre un archivo para analizar");

        TableColumn<TablaElemento, String> infoColumna = new TableColumn<>("Información");
        infoColumna.setCellValueFactory(new PropertyValueFactory<>("informacion"));
        infoColumna.setMinWidth(180);

        TableColumn<TablaElemento, String> valorColumna = new TableColumn<>("Total");
        valorColumna.setCellValueFactory(new PropertyValueFactory<>("total"));
        valorColumna.setMinWidth(180);

        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tabla.getColumns().addAll(infoColumna, valorColumna);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de Texto (*.txt)", "*.txt")
        );

        selector.setOnAction(e -> {
            File archivo = fileChooser.showOpenDialog(stage);
            if (archivo != null) {
                try {
                    HashMap<String, Integer> estadisticas = analizador.realizarEstadistica(archivo);

                    datosTabla.clear();
                    datosTabla.add(new TablaElemento("Líneas", String.valueOf(estadisticas.get("Lineas"))));
                    datosTabla.add(new TablaElemento("Palabras", String.valueOf(estadisticas.get("Palabras"))));
                    datosTabla.add(new TablaElemento("Caracteres", String.valueOf(estadisticas.get("Caracteres"))));
                } catch (Exception ex) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error generado");
                    alerta.setHeaderText(null);
                    alerta.setContentText("No pudo cargarse el archivo: " + ex.getMessage());
                    alerta.showAndWait();
                }
            }
        });

        VBox contenedorBoton = new VBox(selector);
        contenedorBoton.setAlignment(Pos.CENTER);
        contenedorBoton.setPadding(new Insets(5));

        BorderPane panel = new BorderPane();
        panel.setTop(contenedorBoton);
        panel.setCenter(tabla);
        panel.setPadding(new Insets(10));

        Scene scene = new Scene(panel, 500, 250);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static class TablaElemento {
        private String informacion;
        private String total;

        public TablaElemento(String informacion, String total) {
            this.informacion = informacion;
            this.total = total;
        }
        public String getInformacion() {
            return informacion;
        }

        public String getTotal() {
            return total;
        }
    }
}
