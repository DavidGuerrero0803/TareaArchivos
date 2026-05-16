package uabc.david.tareaarchivos;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class VistaEditor {
    private  EditorNotas editor;

    public VistaEditor() {
        this.editor = new EditorNotas();
    }

    public void mostrarEditor() {
        Stage stage = new Stage();
        stage.setTitle("Editor de notas");

        TextArea textArea = new TextArea();
        Button cargar = new Button("Cargar archivo");
        Button guardar = new Button("Guardar archivo");

        HBox contenedorBotones = new HBox(10, cargar, guardar);
        contenedorBotones.setAlignment(Pos.CENTER);

        BorderPane panelPrincipal = new BorderPane();
        panelPrincipal.setCenter(textArea);
        panelPrincipal.setBottom(contenedorBotones);

        Scene scene = new Scene(panelPrincipal, 500, 500);
        stage.setScene(scene);
        stage.show();
    }
}
