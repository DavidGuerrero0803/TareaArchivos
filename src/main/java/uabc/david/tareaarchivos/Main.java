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

        botonEditorNotas.setPrefWidth(200);
        botonEditorNotas.setPrefHeight(40);

        botonEditorNotas.setOnAction(e -> {
            VistaEditor vistaEditor = new VistaEditor();
            vistaEditor.mostrarEditor();
        });

        VBox contenedorPrincipal = new VBox(15, botonEditorNotas);
        contenedorPrincipal.setAlignment(Pos.CENTER);

        Scene scene = new Scene(contenedorPrincipal, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
