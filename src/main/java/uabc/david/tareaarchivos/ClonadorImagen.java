package uabc.david.tareaarchivos;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ClonadorImagen {

    public void clonar(File origen, File destino, ProgressBar progressBar, Label estado, Runnable onFinished, java.util.function.Consumer<String> onError) {

        try (FileInputStream in = new FileInputStream(origen);
             FileOutputStream out = new FileOutputStream(destino)) {

            long bytesTotales = origen.length();
            long bytesCopiados = 0;
            byte[] buffer = new byte[8192];
            int leidos;

            while ((leidos = in.read(buffer)) != -1) {
                out.write(buffer, 0, leidos);
                bytesCopiados += leidos;

                final double progreso = (double) bytesCopiados / bytesTotales;

                Platform.runLater(() -> {
                    progressBar.setProgress(progreso);
                    estado.setText("Progreso: " + (int) (progreso * 100) + "%");
                });

                try { Thread.sleep(5);
                } catch (InterruptedException ignored) {

                }
            }
            Platform.runLater(onFinished);

        } catch (IOException ex) {
            Platform.runLater(() -> onError.accept(ex.getMessage()));
        }
    }
}
