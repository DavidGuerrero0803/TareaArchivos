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

public class VistaCSV {
    private VisualizadorCSV logicaCSV;
    private TableView<String[]> tabla;
    private ObservableList<String[]> datosTabla;

    public VistaCSV() {
        this.logicaCSV = new VisualizadorCSV();
    }

    public void visualizarCSV() {
        Stage stage = new Stage();
        stage.setTitle("Visualizador CSV");

        tabla = new TableView<>();
        datosTabla = FXCollections.observableArrayList();
        tabla.setItems(datosTabla);

        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button abrirArchivo = new Button("Carga el archivo CSV");

        abrirArchivo.setOnAction(e -> {
            File file = new FileChooser().showOpenDialog(stage);
            if (file != null) {

                tabla.getColumns().clear();
                datosTabla.clear();

                try {
                    VisualizadorCSV.ElementoCSV resultado = logicaCSV.procesarArchivo(file);

                    String[] cabeceras = resultado.getCabeceras();

                    for (int i = 0; i < cabeceras.length; i++) {
                        final int indice = i;
                        TableColumn<String[], String> columna = new TableColumn<>(cabeceras[i]);

                        columna.setCellValueFactory(f -> {
                            String[] fila = f.getValue();

                            return new SimpleStringProperty(fila != null && indice < fila.length ? fila[indice] : "");
                        });

                        tabla.getColumns().add(columna);
                    }

                    datosTabla.addAll(resultado.getFilas());

                } catch (Exception ex) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Error al procesar el CSV: " + ex.getMessage());
                    alerta.showAndWait();
                }
            }
        });

        VBox contenedor = new VBox(abrirArchivo);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.setPadding(new Insets(15));

        BorderPane panel = new BorderPane();
        panel.setTop(contenedor);
        panel.setCenter(tabla);
        panel.setPadding(new Insets(10));

        Scene scene = new Scene(panel, 500, 400);
        stage.setScene(scene);
        stage.show();
    }
}
