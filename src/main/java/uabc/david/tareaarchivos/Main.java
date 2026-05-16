package uabc.david.tareaarchivos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Archivos | Flujos de caracter");

        Button botonEditorNotas = new Button("Editor de notas");

        botonEditorNotas.setMaxWidth(200);

        VBox contenedorPrincipal = new VBox(15, botonEditorNotas);

        Scene scene = new Scene(contenedorPrincipal, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
