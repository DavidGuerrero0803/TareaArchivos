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



        selector.setOnAction(e -> {

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
}
