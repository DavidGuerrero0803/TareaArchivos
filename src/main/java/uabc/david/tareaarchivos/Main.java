package uabc.david.tareaarchivos;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Archivos | Flujos de caracter");

        Button botonEditorNotas = new Button("Editor de notas");
        botonEditorNotas.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button botonEstadistica = new Button("Estadísticas de texto");
        botonEstadistica.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button botonCSV = new Button("Visualizador CSV");
        botonCSV.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        botonEditorNotas.setPrefWidth(200);
        botonEditorNotas.setPrefHeight(40);

        botonEstadistica.setPrefWidth(200);
        botonEstadistica.setPrefHeight(40);

        botonCSV.setPrefWidth(200);
        botonCSV.setPrefHeight(40);

        botonEditorNotas.setOnAction(e -> {
            VistaEditor vistaEditor = new VistaEditor();
            vistaEditor.mostrarEditor();
        });

        botonEstadistica.setOnAction(event -> {
            VistaEstadistica vistaEstadistica = new VistaEstadistica();
            vistaEstadistica.mostrarEstadisticas();
        });

        botonCSV.setOnAction(e -> {
            VistaCSV vistaCSV = new VistaCSV();
            vistaCSV.visualizarCSV();
        });

        VBox contenedorPrincipal = new VBox(15, botonEditorNotas, botonEstadistica, botonCSV);
        contenedorPrincipal.setAlignment(Pos.CENTER);

        Scene scene = new Scene(contenedorPrincipal, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
