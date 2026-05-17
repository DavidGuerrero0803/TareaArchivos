package uabc.david.tareaarchivos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

        Button abrirArchivo = new Button("Carga el archivo CSV");

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
