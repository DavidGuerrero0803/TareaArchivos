package uabc.david.tareaarchivos;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Archivos | Flujos de caracter");

        Label caracteres = new Label("Flujos de Caracteres");
        caracteres.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

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

        VBox contenedorIzquierda = new VBox(12, caracteres, botonEditorNotas, botonEstadistica, botonCSV);
        contenedorIzquierda.setAlignment(Pos.CENTER);
        contenedorIzquierda.setPadding(new Insets(10));

        Label bytes = new Label("Flujos de Bytes");
        bytes.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Button ejercicio4 = new Button("Clonador de Imágenes");
        ejercicio4.setPrefWidth(200);
        ejercicio4.setPrefHeight(40);
        Button ejercicio5 = new Button("Encriptador XOR");
        ejercicio5.setPrefWidth(200);
        ejercicio5.setPrefHeight(40);
        Button ejercicio6 = new Button("Indentificador de Tipos");
        ejercicio6.setPrefWidth(200);
        ejercicio6.setPrefHeight(40);

        VBox contenedorDerecha = new VBox(12, bytes, ejercicio4, ejercicio5, ejercicio6);
        contenedorDerecha.setAlignment(Pos.CENTER);
        contenedorDerecha.setPadding(new Insets(10));

        HBox contenedorPrincipal = new HBox(30, contenedorIzquierda, contenedorDerecha);
        contenedorPrincipal.setAlignment(Pos.CENTER);
        contenedorPrincipal.setPadding(new Insets(20));

        Scene scene = new Scene(contenedorPrincipal, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
